package org.gachon.checkmate.domain.checkList.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.checkList.entity.CallType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class CallTypeConverter extends AbstractEnumCodeAttributeConverter<CallType> {
    public CallTypeConverter() {
        super(CallType.class);
    }
}
