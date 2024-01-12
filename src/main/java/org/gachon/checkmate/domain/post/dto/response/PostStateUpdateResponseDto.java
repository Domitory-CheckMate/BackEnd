package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.post.entity.Post;

@Builder
public record PostStateUpdateResponseDto (
        Long postId,
        String postState
) {
    public static PostStateUpdateResponseDto of(Post post) {
        return PostStateUpdateResponseDto.builder()
                .postId(post.getId())
                .postState(post.getPostState().getDesc())
                .build();
    }
}
