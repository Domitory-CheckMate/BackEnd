package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DrinkType implements EnumField {
    NEVER("1", "안마심"),
    SOMETIMES("2", "1주에 2~3번"),
    OFTEN("3", "1주에 4~5번"),
    ALWAYS("4", "매일");

    private final String code;
    private final String desc;
    private final int size = 4;

    public double compareRateTo(DrinkType e) {
        return 1 - (double) Math.abs(Integer.parseInt(this.getCode()) - Integer.parseInt(e.getCode())) / (this.size - 1);
    }
}
