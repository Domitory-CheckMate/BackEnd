package org.gachon.checkmate.domain.checkList.dto.support;

import lombok.Builder;
import org.gachon.checkmate.domain.checkList.entity.*;

@Builder
public record CheckListEnumDto(
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
