package org.gachon.checkmate.domain.post.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.member.entity.RoomType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class RoomTypeConverter extends AbstractEnumCodeAttributeConverter<RoomType> {
    public RoomTypeConverter() {
        super(RoomType.class);
    }
}
