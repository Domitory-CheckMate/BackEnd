package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.chat.entity.Chat;

import java.time.LocalDateTime;

@Builder
public record ChatResponseDto (
        Long senderId,
        String content,
        Boolean isRead,
        LocalDateTime sendTime
) {
    public static ChatResponseDto of(Chat chat) {
        return ChatResponseDto.builder()
                .senderId(chat.getSenderId())
                .content(chat.getContent())
                .isRead(chat.getIsRead())
                .sendTime(chat.getSendTime())
                .build();
    }
}
