package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MypageResponseDto(
        String profileImg,
        String name,
        String major,
        String gender,
        String mbti
) {
    public static MypageResponseDto of(String profileImg,
                                       String name,
                                       String major,
                                       String gender,
                                       String mbti){
        return MypageResponseDto.builder()
                .profileImg(profileImg)
                .name(name)
                .major(major)
                .gender(gender)
                .mbti(mbti)
                .build();
    }
}
