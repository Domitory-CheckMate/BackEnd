package org.gachon.checkmate.domain.checkList.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.entity.*;

@Builder
public record CheckListResponseDto(
        String cleanType,
        String drinkType,
        String homeType,
        String lifePatterType,
        String noiseType,
        String sleepGridingType,
        String sleepSnoreType,
        String sleepTalkingType,
        String sleepTurningType,
        String smokeType
) {
    public static CheckListResponseDto of(CheckList checkList) {
        return CheckListResponseDto.builder()
                .cleanType(checkList.getCleanType().getDesc())
                .drinkType(checkList.getDrinkType().getDesc())
                .homeType(checkList.getHomeType().getDesc())
                .lifePatterType(checkList.getLifePatternType().getDesc())
                .noiseType(checkList.getNoiseType().getDesc())
                .sleepGridingType(checkList.getSleepGridingType().getDesc())
                .sleepSnoreType(checkList.getSleepSnoreType().getDesc())
                .sleepTalkingType(checkList.getSleepTalkingType().getDesc())
                .sleepTurningType(checkList.getSleepTurningType().getDesc())
                .smokeType(checkList.getSmokeType().getDesc())
                .build();
    }

    public static CheckListResponseDto ofPostCheckList(PostCheckList checkList) {
        return CheckListResponseDto.builder()
                .cleanType(checkList.getCleanType().getDesc())
                .drinkType(checkList.getDrinkType().getDesc())
                .homeType(checkList.getHomeType().getDesc())
                .lifePatterType(checkList.getLifePatternType().getDesc())
                .noiseType(checkList.getNoiseType().getDesc())
                .sleepGridingType(checkList.getSleepGridingType().getDesc())
                .sleepSnoreType(checkList.getSleepSnoreType().getDesc())
                .sleepTalkingType(checkList.getSleepTalkingType().getDesc())
                .sleepTurningType(checkList.getSleepTurningType().getDesc())
                .smokeType(checkList.getSmokeType().getDesc())
                .build();
    }
}
