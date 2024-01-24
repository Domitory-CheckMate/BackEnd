package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SleepGrindingType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SleepGrindingTypeConverter extends AbstractEnumCodeAttributeConverter<SleepGrindingType> {
    public SleepGrindingTypeConverter() {
        super(SleepGrindingType.class);
    }
}
