package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SleepType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SleepTypeConverter extends AbstractEnumCodeAttributeConverter<SleepType> {
    public SleepTypeConverter() {
        super(SleepType.class);
    }
}
