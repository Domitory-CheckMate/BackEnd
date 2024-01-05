package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record ChatRoomEnterResponseDto (
        Long userId,
        String chatRoomId
) {
    public static ChatRoomEnterResponseDto of(Long userId, String chatRoomId) {
        return ChatRoomEnterResponseDto.builder()
                .userId(userId)
                .chatRoomId(chatRoomId)
                .build();
    }
}
