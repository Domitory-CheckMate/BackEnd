package org.gachon.checkmate.domain.post.dto.support;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import org.gachon.checkmate.domain.member.entity.GenderType;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.SortType;
import org.springframework.data.domain.Pageable;

import static org.gachon.checkmate.global.utils.EnumValueUtils.toEntityCode;

@Builder(access = AccessLevel.PRIVATE)
public record PostSearchCondition(
        ImportantKeyType importantKeyType,
        GenderType genderType,
        @NotNull SortType sortType,
        Pageable pageable

) {
    public static PostSearchCondition of(@NotNull String sortType, String importantKeyType, String genderType, Pageable pageable) {
        return PostSearchCondition.builder()
                .importantKeyType(importantKeyType != null ? toEntityCode(ImportantKeyType.class, importantKeyType) : null)
                .genderType(genderType != null ? toEntityCode(GenderType.class, genderType) : null)
                .sortType(toEntityCode(SortType.class, sortType))
                .pageable(pageable)
                .build();

    }
}
