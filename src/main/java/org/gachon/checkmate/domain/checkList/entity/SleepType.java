package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SleepType implements EnumField {
    SNORE("1", "코골이"),
    GRIDING("2", "이갈이"),
    TALKING("3", "잠꼬대"),
    TURNING("4", "뒤척임"),
    NOTHING("5", "없음");

    private final String code;
    private final String desc;
}
