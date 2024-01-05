package org.gachon.checkmate.domain.chat.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record ChatUserInfoDto(
        Long userId,
        String name,
        String profile,
        Long postId,
        String title,
        LocalDate endDate
) {
    @QueryProjection
    public ChatUserInfoDto(Long userId, String name, String profile, Long postId, String title, LocalDate endDate) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.postId = postId;
        this.title = title;
        this.endDate = endDate;
    }
}
