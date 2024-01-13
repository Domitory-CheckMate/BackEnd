package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SmokeType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SmokeTypeConverter extends AbstractEnumCodeAttributeConverter<SmokeType> {
    public SmokeTypeConverter() {
        super(SmokeType.class);
    }
}
