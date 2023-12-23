package org.gachon.checkmate.global.utils;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public class AbstractEnumCodeAttributeConverter<T extends Enum<T> & EnumField> implements AttributeConverter<T, String> {
    private Class<T> targetEnumClass;

    public AbstractEnumCodeAttributeConverter(Class<T> targetEnumClass) {
        this.targetEnumClass = targetEnumClass;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return EnumValueUtils.toDBCode(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return EnumValueUtils.toEntityCode(targetEnumClass, dbData);
    }
}
