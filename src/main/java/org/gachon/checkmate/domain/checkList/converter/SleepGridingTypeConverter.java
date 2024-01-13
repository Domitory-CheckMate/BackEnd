package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SleepGridingType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SleepGridingTypeConverter extends AbstractEnumCodeAttributeConverter<SleepGridingType> {
    public SleepGridingTypeConverter() {
        super(SleepGridingType.class);
    }
}
