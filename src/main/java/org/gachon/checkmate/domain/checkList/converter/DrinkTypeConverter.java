package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.DrinkType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class DrinkTypeConverter extends AbstractEnumCodeAttributeConverter<DrinkType> {
    public DrinkTypeConverter() {
        super(DrinkType.class);
    }
}
