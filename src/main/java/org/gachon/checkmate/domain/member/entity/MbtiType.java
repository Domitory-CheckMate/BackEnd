package org.gachon.checkmate.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MbtiType implements EnumField {
    ISTJ("1", "istj"),
    ISFJ("2", "isfj"),
    INFJ("3", "infj"),
    INTJ("4", "intj"),
    ISTP("5", "istp"),
    ISFP("6", "isfp"),
    INFP("7", "infp"),
    INTP("8", "intp"),
    ESTP("9", "estp"),
    ESFP("10", "esfp"),
    ENFP("11", "enfp"),
    ENTP("12", "entp"),
    ESTJ("13", "estj"),
    ESFJ("14", "esfj"),
    ENFJ("15", "enfj"),
    ENTJ("16", "entj");

    private final String code;
    private final String desc;
}
