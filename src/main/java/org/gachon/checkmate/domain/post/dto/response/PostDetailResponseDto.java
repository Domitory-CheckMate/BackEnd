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
        boolean isScrap,
        CheckListResponseDto checkList
) {
    public static PostDetailResponseDto of(PostDetailDto postDetailDto, CheckListResponseDto checkList, boolean isScrap) {
        return PostDetailResponseDto.builder()
                .name(postDetailDto.name())
                .major(postDetailDto.major())
                .profile(postDetailDto.profile())
                .gender(postDetailDto.gender().getDesc())
                .mbti(postDetailDto.mbti().getDesc())
                .isScrap(isScrap)
                .checkList(checkList)
                .build();
    }
}
