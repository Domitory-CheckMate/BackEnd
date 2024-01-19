package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EarPhoneType implements EnumField {
    NEED("1", "이어폰 필수"),
    NOT_NEED("2", "상관없음");

    private final String code;
    private final String desc;

    public int compareRateTo(EarPhoneType e) {
        return this.equals(e) ? 1 : 0;
    }
}
