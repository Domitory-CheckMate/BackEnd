package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PostState implements EnumField {
    RECRUITING("1", "모집중"),
    COMPLETE("2", "모집완료");

    private String code;
    private String desc;
}
