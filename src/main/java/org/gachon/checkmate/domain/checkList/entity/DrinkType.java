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

    private String code;
    private String desc;
}
