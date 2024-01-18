package org.gachon.checkmate.domain.post.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PostSortType implements EnumField {
    REGISTER("1", "register"),
    ACCURACY("2", "accuracy"),
    REMAIN_DATE("3", "remain date"),
    SCRAP("4", "scrap");

    private String code;
    private String desc;
}
