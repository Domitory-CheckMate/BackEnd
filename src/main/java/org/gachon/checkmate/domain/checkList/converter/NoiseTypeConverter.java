package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.NoiseType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class NoiseTypeConverter extends AbstractEnumCodeAttributeConverter<NoiseType> {
    public NoiseTypeConverter() {
        super(NoiseType.class);
    }
}
