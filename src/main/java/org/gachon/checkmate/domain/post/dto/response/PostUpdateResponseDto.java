package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.post.entity.Post;

import java.time.LocalDate;

@Builder
public record PostUpdateResponseDto(
        Long postId,
        String title,
        String content,
        String importantKey,
        String similarityKey,
        String roomType,
        LocalDate endDate,
        CheckListResponseDto checkList
) {
    public static PostUpdateResponseDto of(Post post, CheckListResponseDto checkListResponseDto) {
        return PostUpdateResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .importantKey(post.getImportantKeyType().getDesc())
                .similarityKey(post.getSimilarityKeyType().getDesc())
                .roomType(post.getRoomType().getDesc())
                .checkList(checkListResponseDto)
                .build();
    }
}

