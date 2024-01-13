package org.gachon.checkmate.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.chat.repository.ChatRoomRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.post.repository.PostRepository;
import org.gachon.checkmate.domain.report.dto.request.ChatRoomReportRequestDto;
import org.gachon.checkmate.domain.report.dto.request.PostReportRequestDto;
import org.gachon.checkmate.domain.report.entity.ChatRoomReport;
import org.gachon.checkmate.domain.report.entity.PostReport;
import org.gachon.checkmate.domain.report.repository.ChatRoomReportRepository;
import org.gachon.checkmate.domain.report.repository.PostReportRepository;
import org.gachon.checkmate.global.error.exception.ConflictException;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.error.exception.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.domain.report.entity.ChatRoomReport.*;
import static org.gachon.checkmate.domain.report.entity.PostReport.*;
import static org.gachon.checkmate.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

    private final PostReportRepository postReportRepository;
    private final ChatRoomReportRepository chatRoomReportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void reportPost(Long userId, PostReportRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(requestDto.postId());
        validateUserAlreadyReportPost(user, post);
        PostReport postReport = createPostReport(user, post, requestDto.reason());
        postReportRepository.save(postReport);
    }

    public void reportChatRoom(Long userId, ChatRoomReportRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        ChatRoom chatRoom = getChatRoomOrThrow(requestDto.chatRoomId());
        validateUserChatRoomMember(user, chatRoom);
        validateUserAlreadyReportChatRoom(user, chatRoom);
        ChatRoomReport chatRoomReport = createChatRoomReport(user, chatRoom, requestDto.reason());
        chatRoomReportRepository.save(chatRoomReport);
    }

    private void validateUserChatRoomMember(User user, ChatRoom chatRoom) {
        if(!chatRoom.getFirstMemberId().equals(user.getId()) &&
                !chatRoom.getSecondMemberId().equals(user.getId())) {
            throw new ForbiddenException(NOT_CHATROOM_USER);
        }
    }

    private void validateUserAlreadyReportChatRoom(User user, ChatRoom chatRoom) {
        if(chatRoomReportRepository.existsChatRoomReportByUserIdAndChatRoomId(user.getId(), chatRoom.getId())) {
            throw new ConflictException(DUPLICATE_POST_REPORT);
        }
    }

    private void validateUserAlreadyReportPost(User user, Post post) {
        if(postReportRepository.existsPostReportByUserIdAndPostId(user.getId(), post.getId())) {
            throw new ConflictException(DUPLICATE_POST_REPORT);
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
    }

    private ChatRoom getChatRoomOrThrow(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new EntityNotFoundException(CHATROOM_NOT_FOUND));
    }

}
