package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.EarPhoneType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class EarPhoneTypeConverter extends AbstractEnumCodeAttributeConverter<EarPhoneType> {
    public EarPhoneTypeConverter() {
        super(EarPhoneType.class);
    }
}
