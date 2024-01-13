package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum LifePatternType implements EnumField {
    MORNING("1", "아침형 인간"),
    EVENING("2", "저녁형 인간");

    private final String code;
    private final String desc;

    public int compareRateTo(LifePatternType e) {
        return this.equals(e) ? 1 : 0;
    }
}
