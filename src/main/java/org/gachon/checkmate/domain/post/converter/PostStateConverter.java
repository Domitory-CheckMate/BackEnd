package org.gachon.checkmate.domain.post.converter;

import org.gachon.checkmate.domain.post.entity.PostState;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

public class PostStateConverter extends AbstractEnumCodeAttributeConverter<PostState> {
    public PostStateConverter() {
        super(PostState.class);
    }
}
