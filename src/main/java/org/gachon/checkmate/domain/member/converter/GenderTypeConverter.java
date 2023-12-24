package org.gachon.checkmate.domain.member.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.member.entity.GenderType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class GenderTypeConverter extends AbstractEnumCodeAttributeConverter<GenderType> {
    public GenderTypeConverter() {
        super(GenderType.class);
    }
}
