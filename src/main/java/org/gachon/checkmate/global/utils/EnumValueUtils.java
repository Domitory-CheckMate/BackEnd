package org.gachon.checkmate.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gachon.checkmate.global.error.exception.InvalidValueException;

import java.util.EnumSet;

import static org.gachon.checkmate.global.error.ErrorCode.INVALID_ENUM_CODE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumValueUtils {
    public static <T extends Enum<T> & EnumField> String toDBCode(T enumClass) {
        if (enumClass == null) return "";
        return enumClass.getCode();
    }

    public static <T extends Enum<T> & EnumField> T toEntityCode(Class<T> enumClass, String dbCode) {
        if (dbCode.isEmpty()) return null;
        return EnumSet.allOf(enumClass).stream()
                .filter(e -> e.getCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(INVALID_ENUM_CODE));
    }
}
