package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RoomType implements EnumField {
    TWO("1", "2인실"),
    FOUR("2", "4인실");
    private String code;
    private String desc;
}
