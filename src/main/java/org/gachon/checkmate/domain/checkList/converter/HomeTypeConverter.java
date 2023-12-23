package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.HomeType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class HomeTypeConverter extends AbstractEnumCodeAttributeConverter<HomeType> {
    public HomeTypeConverter() {
        super(HomeType.class);
    }
}
