package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.post.dto.support.PostDetailDto;

@Builder
public record PostDetailResponseDto(
        String name,
        String major,
        String profile,
        String gender,
        String mbti,
        CheckListResponseDto checkList
) {
    public static PostDetailResponseDto of(PostDetailDto postDetailDto, CheckListResponseDto checkList) {
        return PostDetailResponseDto.builder()
                .name(postDetailDto.name())
                .major(postDetailDto.major())
                .profile(postDetailDto.profile())
                .gender(postDetailDto.gender().getDesc())
                .mbti(postDetailDto.mbti().getDesc())
                .checkList(checkList)
                .build();
    }
}
