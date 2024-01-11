package org.gachon.checkmate.domain.member.converter;

import org.gachon.checkmate.domain.member.entity.UserState;
import org.gachon.checkmate.global.utils.AbstractEnumCodeAttributeConverter;

public class UserStateConverter extends AbstractEnumCodeAttributeConverter<UserState> {
    public UserStateConverter() {
        super(UserState.class);
    }
}
