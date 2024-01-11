package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum UserState implements EnumField {
    JOIN("1", "가입"),
    WITHDRAW("2", "탈퇴");

    private final String code;
    private final String desc;
}
