package org.gachon.checkmate.domain.chat.dto;

import com.querydsl.core.annotations.QueryProjection;
import org.gachon.checkmate.domain.member.entity.GenderType;

import java.time.LocalDate;

public record ChatRoomListUserInfoDto(
        Long userId,
        String name,
        String profile,
        String major,
        GenderType gender,
        LocalDate endDate
) {
    @QueryProjection
    public ChatRoomListUserInfoDto(Long userId, String name, String profile, String major, GenderType gender, LocalDate endDate) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.major = major;
        this.gender = gender;
        this.endDate = endDate;
    }
}
