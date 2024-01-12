package org.gachon.checkmate.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import org.gachon.checkmate.domain.post.entity.PostState;

public record PostStateUpdateRequestDto(
        @NotNull(message = "게시글 상태를 입력해주세요") PostState postState
) {
}
