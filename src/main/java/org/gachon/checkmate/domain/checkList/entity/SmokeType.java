package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SmokeType implements EnumField {
    SMOKE("1", "흡연자 선호"),
    NONE("2", "비흡연자 선호");

    private final String code;
    private final String desc;

    public int compareRateTo(SmokeType e) {
        return this.equals(e) ? 1 : 0;
    }
}

