package org.gachon.checkmate.domain.checkList.dto.request;

import org.gachon.checkmate.domain.checkList.dto.support.CheckListEnumDto;
import org.gachon.checkmate.domain.checkList.entity.*;

import static org.gachon.checkmate.global.utils.EnumValueUtils.toEntityCode;

public record CheckListRequestDto(
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
    public static CheckListEnumDto toEnumDto(CheckListRequestDto requestDto) {
        return CheckListEnumDto.builder()
                .cleanType(toEntityCode(CleanType.class, requestDto.cleanType()))
                .drinkType(toEntityCode(DrinkType.class, requestDto.drinkType()))
                .homeType(toEntityCode(HomeType.class, requestDto.homeType()))
                .lifePatternType(toEntityCode(LifePatternType.class, requestDto.lifePatternType()))
                .callType(toEntityCode(CallType.class, requestDto.callType()))
                .earPhoneType(toEntityCode(EarPhoneType.class, requestDto.earPhoneType()))
                .sleepGrindingType(toEntityCode(SleepGrindingType.class, requestDto.sleepGrindingType()))
                .sleepSnoreType(toEntityCode(SleepSnoreType.class, requestDto.sleepSnoreType()))
                .sleepTalkingType(toEntityCode(SleepTalkingType.class, requestDto.sleepTalkingType()))
                .sleepTurningType(toEntityCode(SleepTurningType.class, requestDto.sleepTurningType()))
                .smokeType(toEntityCode(SmokeType.class, requestDto.smokeType()))
                .build();
    }
}
