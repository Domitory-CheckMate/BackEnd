package org.gachon.checkmate.domain.checkList.dto.response;

import lombok.Builder;

@Builder
public record CheckListResponseDto(
        String cleanType,
        String drinkType,
        String homeType,
        String lifePatterType,
        String noiseType,
        String sleepType,
        String smokeType
) {
    public static CheckListResponseDto of(String cleanType,
                                          String drinkType,
                                          String homeType,
                                          String lifePatterType,
                                          String noiseType,
                                          String sleepType,
                                          String smokeType) {
        return CheckListResponseDto.builder()
                .cleanType(cleanType)
                .drinkType(drinkType)
                .homeType(homeType)
                .lifePatterType(lifePatterType)
                .noiseType(noiseType)
                .sleepType(sleepType)
                .smokeType(smokeType)
                .build();
    }
}
