package org.gachon.checkmate.domain.checkList.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;

@Builder
public record CheckListResponseDto(
        String cleanType,
        String drinkType,
        String homeType,
        String lifePatternType,
        String callType,
        String earPhoneType,
        String sleepGrindingType,
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
                .lifePatternType(checkList.getLifePatternType().getDesc())
                .callType(checkList.getCallType().getDesc())
                .earPhoneType(checkList.getEarPhoneType().getDesc())
                .sleepGrindingType(checkList.getSleepGrindingType().getDesc())
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
                .lifePatternType(checkList.getLifePatternType().getDesc())
                .callType(checkList.getCallType().getDesc())
                .earPhoneType(checkList.getEarPhoneType().getDesc())
                .sleepGrindingType(checkList.getSleepGrindingType().getDesc())
                .sleepSnoreType(checkList.getSleepSnoreType().getDesc())
                .sleepTalkingType(checkList.getSleepTalkingType().getDesc())
                .sleepTurningType(checkList.getSleepTurningType().getDesc())
                .smokeType(checkList.getSmokeType().getDesc())
                .build();
    }
}
