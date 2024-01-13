package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.SleepTalkingType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SleepTalkingTypeConverter extends AbstractEnumCodeAttributeConverter<SleepTalkingType> {
    public SleepTalkingTypeConverter() {
        super(SleepTalkingType.class);
    }
}
