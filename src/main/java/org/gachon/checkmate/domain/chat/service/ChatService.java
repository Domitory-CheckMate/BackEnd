package org.gachon.checkmate.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.dto.*;
import org.gachon.checkmate.domain.chat.dto.request.ChatListRequestDto;
import org.gachon.checkmate.domain.chat.dto.request.ChatRequestDto;
import org.gachon.checkmate.domain.chat.dto.response.*;
import org.gachon.checkmate.domain.chat.entity.Chat;
import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.chat.entity.LiveChatRoom;
import org.gachon.checkmate.domain.chat.mongorepository.ChatRepository;
import org.gachon.checkmate.domain.chat.repository.ChatRoomRepository;
import org.gachon.checkmate.domain.chat.repository.LiveChatRoomRepository;
import org.gachon.checkmate.domain.chat.util.ListComparatorChatSendTime;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserQuerydslRepository;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.error.ErrorCode;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.socket.error.SocketErrorCode;
import org.gachon.checkmate.global.socket.error.SocketNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.gachon.checkmate.domain.chat.entity.LiveChatRoom.createLiveChatRoom;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessageSendingOperations sendingOperations;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final LiveChatRoomRepository liveChatRoomRepository;
    private final UserQuerydslRepository userQuerydslRepository;

    /**
     * 채팅을 전송하는 코드입니다.
     * 1. 채팅상대가 채팅방에 들어와있는지 확인하고 앍맞은 isRead값을 넣은 채팅을 MongoDB에 저장합니다.
     * 2. 만약 다른 유저가 채팅에 들어와있지 않다면 해당 유저의 /queue/user/{다른 유저 번호}로 알림 메세지를 보냅니다.
     * 3. 채팅을 return해주고 controller에서 /queue/chat/{채팅방Id}로 메세지를 보냅니다.
     */
    public ChatResponseDto sendChat(Map<String, Object> simpSessionAttributes,
                                    ChatRequestDto request) {
        String roomId = getRoomIdInAttributes(simpSessionAttributes);
        Long userId = getUserIdInAttributes(simpSessionAttributes);
        ChatRoom chatRoom = getChatRoomById(roomId);
        Long otherUserId = getOtherUserIdInChatRoom(chatRoom, userId);
        // 채팅상대가 이 채팅방에 들어와있는지 확인함
        Boolean isOtherUserInChatRoom = isUserInChatRoom(roomId, otherUserId);
        Chat chat = Chat.createChat(roomId, userId, request.content(), isOtherUserInChatRoom);
        Chat savedChat = chatRepository.save(chat);

        // 만약 채팅방에 다른 유저가 안들어와있을 때 알림가도록 메세지 전송해줌
        sendNotificationToOtherUser(isOtherUserInChatRoom, savedChat, otherUserId);

        return ChatResponseDto.of(savedChat);
    }

    /**
     * 유저의 채팅방들을 불러오는 코드입니다.
     * 1. 유저가 속한 채팅방을 가져옵니다.
     * 2. 해당 채팅방에서 각각 채팅 상대 유저의 정보, 채팅방의 마지막 채팅내용, 읽지않은 채팅 수를 나타냅니다.
     * 3. 채팅방의 마지막 채팅 최신순으로 정렬해줍니다.
     */
    public ChatRoomListResponseDto getChatRoomList(Map<String, Object> simpSessionAttributes) {
        Long userId = getUserIdInAttributes(simpSessionAttributes);
        List<ChatRoom> chatRooms = getUserChatRoomsByUserId(userId);
        List<ChatRoomListDto> response = chatRooms.stream()
                .map(chatRoom -> getChatRoomListResponse(chatRoom, userId))
                .collect(Collectors.toList());

        // 최신 메시지순으로 정렬
        sortChatRoomListDtoByLastSendTime(response);
        return ChatRoomListResponseDto.of(response);
    }

    /**
     * 채팅방에 들어가 이전 채팅목록을 불러오는 코드입니다.
     * 1. 다른 유저의 유저 정보를 받아옵니다.
     * 2. 다른 유저가 작성한 post의 정보를 받아옵니다.
     * 3. 해당 채팅방이 처음 만들어지는 건지 아니면 이미 있는 채팅방인지 확인합니다.
     * 3-1. 만약 채팅방 있다면 mongoDB의 chatting에서 해당 채팅방의 채팅 내역을 20개씩 끊어 받아옵니다.
     * 3-2. 만약 채팅방이 없다면(처음 생성된것이라면) 새로운 채팅방을 만들어 저장해줍니다. (이때 이전 채팅들은 null로 들어갑니다.)
     */
    public ChatListResponseDto getChatList(Map<String, Object> simpSessionAttributes,
                                           ChatListRequestDto request) {
        Long userId = getUserIdInAttributes(simpSessionAttributes);
        String chatRoomId = getChatRoomId(request.otherUserId(), userId);

        ChatUserInfoDto chatUserInfoDto = getUserChatUserInfoByUserId(request);
        ChatListResponseDto response = ChatListResponseDto.of(chatRoomId, chatUserInfoDto);

        PageRequest pageRequest = PageRequest.of(request.pageNumber(), request.pageSize());
        Slice<Chat> chatMessages = chatRepository.findBeforeChatList(chatRoomId, pageRequest);
        chatMessages.getContent().forEach(response::addChatMessage);
        response.updatePageInfo(chatMessages.hasNext(), chatMessages.getNumber());

        return response;
    }

    /**
     * 채팅방을 들어올 때 실행합니다.
     * 1. redis에 해당 채팅방에 유저가 들어왔다는 것을 저장합니다.
     * 2. 이전에 유저가 읽지 않은 채팅을 모두 읽음처리 해줍니다.
     */
    public ChatRoomEnterResponseDto enterChatRoom(Map<String, Object> simpSessionAttributes,
                                                  ChatListRequestDto request) {
        Long userId = getUserIdInAttributes(simpSessionAttributes);
        String chatRoomId = getChatRoomId(request.otherUserId(), userId);

        // 해당 채팅방에 유저 접속상태 확인 위해 redis에 저장
        saveUserInLiveChatRoom(chatRoomId, userId);

        // 채팅방이 있는지 확인하고 없다면 하나 새로 만들어줌 (채팅방 id : 유저 두명의 아이디인데 작은 id가 앞으로감
        // ex) 유저 아이디가 10, 3 인 채팅방이면 3:10이 있는지 확인
        if(validateChatRoomExist(chatRoomId)) {
            // 안읽은 메세지들 읽음 처리 해줌
            chatRepository.updateChatRead(chatRoomId, userId);
        }
        else {
            ChatRoom chatRoom = createChatRoomByIds(chatRoomId, userId, request.otherUserId());
            chatRoomRepository.save(chatRoom);
        }
        return ChatRoomEnterResponseDto.of(userId, chatRoomId);
    }

    /**
     *  유저가 안읽은 메시지의 총 수를 받아옵니다.
     */
    public ChatTotalNotReadResponseDto getTotalNotReadCount(Map<String, Object> simpSessionAttributes) {
        Long userId = getUserIdInAttributes(simpSessionAttributes);
        List<ChatRoom> chatRooms = getUserChatRoomsByUserId(userId);

        Long userTotalNotReadCount = getUserTotalNotReadCount(chatRooms, userId);

        return ChatTotalNotReadResponseDto.of(userTotalNotReadCount);
    }

    private ChatRoom createChatRoomByIds(String chatRoomId, Long firstUserId, Long secondUserId) {
        User firstUser = getUserOrThrow(firstUserId);
        User secondUser = getUserOrThrow(secondUserId);
        return ChatRoom.createChatRoom(chatRoomId, firstUser, secondUser);
    }

    private void sendNotificationToOtherUser(Boolean isOtherUserInChatRoom, Chat savedChat, Long otherUserId) {
        if(!isOtherUserInChatRoom) {
            NewChatResponseDto notificationChat = NewChatResponseDto.of(savedChat);
            sendingOperations.convertAndSend("/queue/user/" + otherUserId, SocketBaseResponse.of(MessageType.NEW_CHAT_NOTIFICATION, notificationChat));
        }
    }

    private void sortChatRoomListDtoByLastSendTime(List<ChatRoomListDto> chatRoomListDtos) {
        if(!chatRoomListDtos.isEmpty()) {
            chatRoomListDtos.sort(new ListComparatorChatSendTime());
        }
    }

    private ChatRoomListDto getChatRoomListResponse(ChatRoom chatRoom, Long userId) {
        Long otherUserId = getChatRoomOtherUserId(chatRoom, userId);
        // 채팅방 목록에 표시돼야하는 상대 유저의 정보, 게시물 마감일을 가져옴
        ChatRoomListUserInfoDto otherUserInfo = getUserChatRoomListInfoByUserId(otherUserId);
        // 채팅방에 있는 마지막 채팅 내용 가져옴
        ChatLastMessageDto chatLastMessageDto = getLastChatRoomContent(chatRoom);
        // 채팅방 유저가 안읽은 메세지 수 가져옴
        Long userNotReadCount = getUserNotReadCount(chatRoom, userId);

        return ChatRoomListDto.of(chatLastMessageDto,userNotReadCount, otherUserInfo);
    }

    private ChatRoomListUserInfoDto getUserChatRoomListInfoByUserId(Long userId) {
        return userQuerydslRepository.findUserChatRoomListInfo(userId);
    }

    private Long getUserNotReadCount(ChatRoom chatRoom, Long userId) {
        return chatRepository.findUserNotReadCount(chatRoom.getId(), userId);
    }


    private Long getUserTotalNotReadCount(List<ChatRoom> chatRooms, Long userId) {
        return chatRepository.findUserNotReadTotalCount(chatRooms.stream().map(ChatRoom::getId).toList(), userId);
    }

    private ChatLastMessageDto getLastChatRoomContent(ChatRoom chatRoom) {
        return chatRepository.findLastChatRoomContent(chatRoom.getId());
    }

    private Long getChatRoomOtherUserId(ChatRoom chatRoom, Long userId) {
        return chatRoom.getFirstUser().getId().equals(userId) ? chatRoom.getSecondUser().getId() : chatRoom.getFirstUser().getId();
    }

    private List<ChatRoom> getUserChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findUserAllChatRoom(userId);
    }

    private ChatUserInfoDto getUserChatUserInfoByUserId(ChatListRequestDto request) {
        return userQuerydslRepository.findUserChatUserInfo(request.otherUserId());
    }

    private void saveUserInLiveChatRoom(String chatRoomId, Long userId) {
        LiveChatRoom liveChatRoom = createLiveChatRoom(chatRoomId, userId);
        liveChatRoomRepository.save(liveChatRoom);
    }

    private Boolean isUserInChatRoom(String roomId, Long userId) {
        return liveChatRoomRepository.existsLiveChatRoomByChatRoomIdAndUserId(roomId, userId);
    }

    private ChatRoom getChatRoomById(String roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new SocketNotFoundException(SocketErrorCode.CHATROOM_NOT_FOUND));
    }

    private Long getOtherUserIdInChatRoom(ChatRoom chatRoom, Long myUserId) {
        return chatRoom.getFirstUser().getId().equals(myUserId) ? chatRoom.getSecondUser().getId() : chatRoom.getFirstUser().getId();
    }

    private boolean validateChatRoomExist(String chatRoomId) {
        return chatRoomRepository.existsById(chatRoomId);
    }

    private String getChatRoomId(Long otherUserId, Long userId) {
        return userId < otherUserId ? userId + "+" + otherUserId : otherUserId + "+" + userId;
    }

    private Long getUserIdInAttributes(Map<String, Object> simpSessionAttributes) {
        return (Long)simpSessionAttributes.get("userId");
    }

    private String getRoomIdInAttributes(Map<String, Object> simpSessionAttributes) {
        return (String)simpSessionAttributes.get("roomId");
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

}
