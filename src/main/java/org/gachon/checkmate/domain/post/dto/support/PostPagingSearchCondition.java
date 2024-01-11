package org.gachon.checkmate.domain.post.dto.support;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record PostPagingSearchCondition(
        String text,
        Long selectedUser,
        Pageable pageable
) {
    public static PostPagingSearchCondition searchText(String text, Pageable pageable) {
        return PostPagingSearchCondition.builder()
                .text(text)
                .selectedUser(null)
                .pageable(pageable)
                .build();
    }

    public static PostPagingSearchCondition searchSelectedUser(Long userId, Pageable pageable) {
        return PostPagingSearchCondition.builder()
                .text(null)
                .selectedUser(userId)
                .pageable(pageable)
                .build();
    }
}
