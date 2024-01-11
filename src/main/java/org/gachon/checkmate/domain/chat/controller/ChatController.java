package org.gachon.checkmate.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.dto.MessageType;
import org.gachon.checkmate.domain.chat.dto.request.ChatListRequestDto;
import org.gachon.checkmate.domain.chat.dto.request.ChatRequestDto;
import org.gachon.checkmate.domain.chat.dto.response.*;
import org.gachon.checkmate.domain.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    private final SimpMessageSendingOperations sendingOperations;

    // 채팅 전송
    @MessageMapping("/chat")
    public void sendChat(@Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                             @Payload final ChatRequestDto request) {
        ChatResponseDto response = chatService.sendChat(simpSessionAttributes, request);
        sendingOperations.convertAndSend("/queue/chat/"+simpSessionAttributes.get("roomId"), SocketBaseResponse.of(MessageType.CHAT, response));
    }

    // 채팅방 정보 조회
    @MessageMapping("/room-list")
    public void getChatRoomList(@Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes) {
        ChatRoomListResponseDto response = chatService.getChatRoomList(simpSessionAttributes);
        sendingOperations.convertAndSend("/queue/user/"+simpSessionAttributes.get("userId"), SocketBaseResponse.of(MessageType.ROOM_LIST, response));
    }

    // 이전 채팅 불러오기
    @MessageMapping("/chat-list")
    public void getChatList(@Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                @Payload final ChatListRequestDto request) {
        final ChatListResponseDto response = chatService.getChatList(simpSessionAttributes, request);
        sendingOperations.convertAndSend("/queue/user/" + simpSessionAttributes.get("userId"), SocketBaseResponse.of(MessageType.CHAT_LIST, response));
    }

    // 채팅방 입장하기
    @MessageMapping("/room-enter")
    public void enterRoom(@Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                          @Payload final ChatListRequestDto request) {
        ChatRoomEnterResponseDto response = chatService.enterChatRoom(simpSessionAttributes, request);
        sendingOperations.convertAndSend("/queue/chat/" + response.chatRoomId(), SocketBaseResponse.of(MessageType.ROOM_ENTER, response));
    }

    // 유저 총 안읽은 메세지
    @MessageMapping("/not-read")
    public void getNotReadChatCount(@Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes) {
        ChatTotalNotReadResponseDto response = chatService.getTotalNotReadCount(simpSessionAttributes);
        sendingOperations.convertAndSend("/queue/user/" + simpSessionAttributes.get("userId"), SocketBaseResponse.of(MessageType.NOT_READ_COUNT, response));
    }
}
