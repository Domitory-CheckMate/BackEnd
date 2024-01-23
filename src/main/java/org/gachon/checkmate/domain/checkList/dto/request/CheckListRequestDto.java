package org.gachon.checkmate.domain.checkList.dto.request;

import org.gachon.checkmate.domain.checkList.entity.*;

public record CheckListRequestDto(
        CleanType cleanType,
        DrinkType drinkType,
        HomeType homeType,
        LifePatternType lifePatternType,
        CallType callType,
        EarPhoneType earPhoneType,
        SleepGrindingType sleepGrindingType,
        SleepSnoreType sleepSnoreType,
        SleepTalkingType sleepTalkingType,
        SleepTurningType sleepTurningType,
        SmokeType smokeType

) {
}
