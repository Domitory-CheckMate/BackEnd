package org.gachon.checkmate.domain.member.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.member.entity.MajorType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class MajorTypeConverter extends AbstractEnumCodeAttributeConverter<MajorType> {
    public MajorTypeConverter() {
        super(MajorType.class);
    }
}
