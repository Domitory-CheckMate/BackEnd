package org.gachon.checkmate.domain.checkList.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;

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

    public static CheckListResponseDto ofPostCheckList(PostCheckList checkList) {
        return CheckListResponseDto.builder()
                .cleanType(checkList.getCleanType().getDesc())
                .drinkType(checkList.getDrinkType().getDesc())
                .homeType(checkList.getHomeType().getDesc())
                .lifePatterType(checkList.getLifePatternType().getDesc())
                .noiseType(checkList.getNoiseType().getDesc())
                .sleepType(checkList.getSleepType().getDesc())
                .smokeType(checkList.getSmokeType().getDesc())
                .build();
    }
}
