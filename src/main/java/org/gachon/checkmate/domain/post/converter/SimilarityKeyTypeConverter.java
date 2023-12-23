package org.gachon.checkmate.domain.post.converter;

import jakarta.persistence.Converter;
import org.gachon.checkmate.domain.post.entity.SimilarityKeyType;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

@Converter
public class SimilarityKeyTypeConverter extends AbstractEnumCodeAttributeConverter<SimilarityKeyType> {
    public SimilarityKeyTypeConverter() {
        super(SimilarityKeyType.class);
    }
}
