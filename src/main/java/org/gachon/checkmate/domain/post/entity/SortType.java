package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SortType implements EnumField {
    DATE("1", "register"),
    REMAIN_DATE("2", "remain date"),
    ACCURACY("3", "accuracy");

    private String code;
    private String desc;
}
