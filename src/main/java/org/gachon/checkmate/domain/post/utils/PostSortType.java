package org.gachon.checkmate.domain.post.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PostSortType implements EnumField {
    ACCURACY("1", "accuracy"),
    REGISTER("2", "register"),
    REMAIN_DATE("3", "remain date"),
    SCRAP("4", "scrap");

    private String code;
    private String desc;
}
