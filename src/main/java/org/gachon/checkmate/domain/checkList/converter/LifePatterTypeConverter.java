package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.LifePatterType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class LifePatterTypeConverter extends AbstractEnumCodeAttributeConverter<LifePatterType> {
    public LifePatterTypeConverter() {
        super(LifePatterType.class);
    }
}
