package org.gachon.checkmate.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.RoomType;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;

import java.time.LocalDate;

public record PostUpdateRequestDto(
        @NotBlank(message = "제목을 입력해주세요") String title,
        @NotBlank(message = "내용을 입력해주세요") String content,
        @NotNull(message = "중요 키워드를 입력해주세요") ImportantKeyType importantKey,
        @NotNull(message = "유사도를 입력해주세요") SimilarityKeyType similarityKey,
        @NotNull(message = "기숙사 유형을 입력해주세요") RoomType roomType,
        @NotNull(message = "모집 마감기간을 입력해주세요") LocalDate endDate,
        @NotNull(message = "체크리스트를 입력해주세요") CheckListRequestDto checkList
) {
}
