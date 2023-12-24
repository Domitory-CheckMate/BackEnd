package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CleanType implements EnumField {
    RARELY("1", "1달에 한번"),
    SOMETIMES("2", "2주에 1번"),
    OFTEN("3", "1주에 1번"),
    USUALLY("4", "1주에 3~4번"),
    ALWAYS("5", "매일매일");

    private final String code;
    private final String desc;
}
