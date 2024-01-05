package org.gachon.checkmate.domain.member.dto.request;

import org.gachon.checkmate.domain.member.entity.ProfileImageType;

public record ProfileImgRequestDto(
        ProfileImageType profileImageType
) {
}
