package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum GenderType implements EnumField {
    MAN("1", "남자"),
    WOMAN("2", "여자");

    private final String code;
    private final String desc;
}