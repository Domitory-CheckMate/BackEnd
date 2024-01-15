package org.gachon.checkmate.domain.checkList.converter;

import org.gachon.checkmate.domain.checkList.entity.SleepTurningType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

public class SleepTurningTypeConverter extends AbstractEnumCodeAttributeConverter<SleepTurningType> {
    public SleepTurningTypeConverter() {
        super(SleepTurningType.class);
    }
}
