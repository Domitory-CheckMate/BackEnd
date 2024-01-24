package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SleepGrindingType implements EnumField {
    TRUE("1", "이갈이"),
    FALSE("2", "false");

    private final String code;
    private final String desc;

    public int compareRateTo(SleepGrindingType e) {
        return this.equals(e) ? 1 : 0;
    }
}
