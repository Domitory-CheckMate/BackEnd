package org.gachon.checkmate.domain.member.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.member.entity.MbtiType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class MbtiTypeConverter extends AbstractEnumCodeAttributeConverter<MbtiType> {
    public MbtiTypeConverter() {
        super(MbtiType.class);
    }
}
