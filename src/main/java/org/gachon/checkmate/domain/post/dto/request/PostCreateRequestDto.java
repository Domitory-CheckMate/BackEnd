package org.gachon.checkmate.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.NonNull;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.RoomType;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;

import java.time.LocalDate;

public record PostCreateRequestDto(
        @NotBlank(message = "제목을 입력해주세요") String title,
        @NotBlank(message = "내용을 입력해주세요") String content,
        @NotBlank(message = "중요 키워드를 입력해주세요") ImportantKeyType importantKey,
        @NotBlank(message = "유사도를 입력해주세요") SimilarityKeyType similarityKey,
        @NotBlank(message = "기숙사 유형을 입력해주세요") RoomType roomType,
        @NotBlank(message = "모집 마감기간을 입력해주세요") LocalDate endDate,
        @NotNull(message = "체크리스트를 입력해주세요") CheckListRequestDto checkList
) {
}
