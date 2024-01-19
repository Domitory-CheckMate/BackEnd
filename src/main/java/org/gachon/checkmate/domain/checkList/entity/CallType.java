package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CallType implements EnumField {
    OUTSIDE("1", "통화는 밖에서"),
    INSIDE("2", "5분 이내는 안에서"),
    ANYWAY("3", "상관없음");

    private final String code;
    private final String desc;

    public int compareRateTo(CallType e) {
        return this.equals(e) ? 1 : 0;
    }
}
