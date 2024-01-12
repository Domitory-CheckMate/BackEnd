package org.gachon.checkmate.domain.checkList.dto.request;

import org.gachon.checkmate.domain.checkList.entity.*;

public record CheckListRequestDto(
        CleanType cleanType,
        DrinkType drinkType,
        HomeType homeType,
        LifePatternType lifePatternType,
        NoiseType noiseType,
        SleepType sleepType,
        SmokeType smokeType

) {
}
