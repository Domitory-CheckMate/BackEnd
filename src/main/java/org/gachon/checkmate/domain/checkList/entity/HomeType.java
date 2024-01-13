package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum HomeType implements EnumField {
    RARELY("1", "가끔"),
    SOMETIMES("2", "달에 1번"),
    OFTEN("3", "1~2주에 한번"),
    ALWAYS("4", "매주");

    private final String code;
    private final String desc;
    private final int size = 4;

    public int compareRateTo(HomeType e) {
        return 1 - Math.abs(Integer.parseInt(this.getCode()) - Integer.parseInt(e.getCode())) / (this.size - 1);
    }
}
