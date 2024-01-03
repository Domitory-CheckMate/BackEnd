package org.gachon.checkmate.domain.checkList.dto.request;

import org.gachon.checkmate.domain.checkList.entity.*;

public record CreateCheckListRequestDto(
        CleanType cleanType,
        DrinkType drinkType,
        HomeType homeType,
        LifePatterType lifePatterType,
        NoiseType noiseType,
        SleepType sleepType

) {
}
