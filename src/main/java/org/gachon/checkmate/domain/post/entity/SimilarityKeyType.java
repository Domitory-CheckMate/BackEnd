package org.gachon.checkmate.domain.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.gachon.checkmate.global.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SimilarityKeyType implements EnumField {
    TEN("1", "10%"),
    TWENTY("2", "20%"),
    THIRTY("3", "30%"),
    FORTY("4", "40%"),
    FIFTY("5", "50%"),
    SIXTY("6", "60%"),
    SEVENTY("7", "70%"),
    EIGHTY("8", "80%"),
    NINETY("9", "90%"),
    HUNDRED("10", "100%");
    private String code;
    private String desc;
}
