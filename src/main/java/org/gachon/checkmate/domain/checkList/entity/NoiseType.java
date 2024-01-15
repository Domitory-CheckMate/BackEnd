package org.gachon.checkmate.domain.checkList.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NoiseType implements EnumField {
    EARPHONE("1", "이어폰 필수"),
    OUTSIDE("2", "전화는 밖에서"),
    SHORT("3", "전화는 짧게"),
    ANYWAY("4", "상관없음");

    private final String code;
    private final String desc;

    public int compareRateTo(NoiseType e) {
        return this.equals(e) ? 1 : 0;
    }
}
