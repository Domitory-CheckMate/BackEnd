package org.gachon.checkmate.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.post.dto.support.PostEnumDto;
import org.gachon.checkmate.domain.post.entity.DormitoryType;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.RoomType;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;

import java.time.LocalDate;

import static org.gachon.checkmate.global.utils.EnumValueUtils.toEntityCode;

public record PostRequestDto(
        @NotBlank(message = "제목을 입력해주세요") String title,
        @NotBlank(message = "내용을 입력해주세요") String content,
        @NotNull(message = "중요 키워드를 입력해주세요") String importantKey,
        @NotNull(message = "유사도를 입력해주세요") String similarityKey,
        @NotNull(message = "호실을 입력해주세요") String roomType,
        @NotNull(message = "기숙사 유형을 입력해주세요") String dormitoryType,
        @NotNull(message = "모집 마감기간을 입력해주세요") LocalDate endDate,
        @NotNull(message = "체크리스트를 입력해주세요") CheckListRequestDto checkList
) {
    public static PostEnumDto toEnumDto(PostRequestDto requestDto) {
        return PostEnumDto.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .importantKey(toEntityCode(ImportantKeyType.class, requestDto.importantKey()))
                .similarityKey(toEntityCode(SimilarityKeyType.class, requestDto.similarityKey()))
                .roomType(toEntityCode(RoomType.class, requestDto.roomType()))
                .dormitoryType(toEntityCode(DormitoryType.class, requestDto.dormitoryType()))
                .endDate(requestDto.endDate())
                .checkList(CheckListRequestDto.toEnumDto(requestDto.checkList()))
                .build();
    }
}
