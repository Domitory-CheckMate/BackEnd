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

    private String code;
    private String desc;
}
