package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DormitoryType implements EnumField {
    FIRST("1", "1기숙사"),
    SECOND("2", "2기숙사"),
    THIRD("3", "3기숙사");

    private final String code;
    private final String desc;
}
