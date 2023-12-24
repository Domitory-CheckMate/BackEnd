package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.CleanType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class CleanTypeConverter extends AbstractEnumCodeAttributeConverter<CleanType> {
    public CleanTypeConverter() {
        super(CleanType.class);
    }
}
