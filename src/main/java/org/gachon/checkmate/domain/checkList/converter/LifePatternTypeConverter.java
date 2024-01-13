package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.LifePatternType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class LifePatternTypeConverter extends AbstractEnumCodeAttributeConverter<LifePatternType> {
    public LifePatternTypeConverter() {
        super(LifePatternType.class);
    }
}
