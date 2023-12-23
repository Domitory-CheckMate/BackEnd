package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MajorType implements EnumField {
    SOFTWARE("1", "software"),
    AI("2", "ai");

    private final String code;
    private final String desc;
}
