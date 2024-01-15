package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SleepSnoreType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SleepSnoreTypeConverter extends AbstractEnumCodeAttributeConverter<SleepSnoreType> {
    public SleepSnoreTypeConverter() {
        super(SleepSnoreType.class);
    }
}
