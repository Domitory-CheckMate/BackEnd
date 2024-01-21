package org.gachon.checkmate.domain.post.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.post.entity.DormitoryType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class DormitoryTypeConverter extends AbstractEnumCodeAttributeConverter<DormitoryType> {
    public DormitoryTypeConverter() {
        super(DormitoryType.class);
    }
}

