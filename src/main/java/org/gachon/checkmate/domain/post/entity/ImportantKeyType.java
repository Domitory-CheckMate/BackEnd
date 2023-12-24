package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ImportantKeyType implements EnumField {
    CLEAN("1", "청결도"),
    NON_SMOKE("2", "비흡연"),
    SMOKE("3", "흡연"),
    MORNING("4", "아침형"),
    EVENING("5", "저녁형"),
    SLEEP("6", "잠버릇"),
    DRINK("7", "애주가");
    private String code;
    private String desc;
}
