package org.gachon.checkmate.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.chat.entity.ChatRoom;
import org.gachon.checkmate.domain.chat.repository.ChatRoomRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.post.repository.PostRepository;
import org.gachon.checkmate.domain.report.dto.request.PostReportRequestDto;
import org.gachon.checkmate.domain.report.entity.PostReport;
import org.gachon.checkmate.domain.report.repository.ChatRoomReportRepository;
import org.gachon.checkmate.domain.report.repository.PostReportRepository;
import org.gachon.checkmate.global.error.exception.ConflictException;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.domain.report.entity.PostReport.*;
import static org.gachon.checkmate.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

    private final PostReportRepository postReportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void reportPost(Long userId, PostReportRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(requestDto.postId());
        validateUserAlreadyReportPost(user.getId(), post.getId());
        PostReport postReport = createPostReport(user, post, requestDto.reason());
        postReportRepository.save(postReport);
    }

    private void validateUserAlreadyReportPost(Long userId, Long postId) {
        if(postReportRepository.existsPostReportByUserIdAndPostId(userId, postId)) {
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

}
