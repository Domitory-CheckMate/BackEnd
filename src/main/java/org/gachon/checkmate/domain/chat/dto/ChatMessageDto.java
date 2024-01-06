package org.gachon.checkmate.domain.chat.dto;


import lombok.Builder;
import org.gachon.checkmate.domain.chat.entity.Chat;

import java.time.LocalDateTime;

@Builder
public record ChatMessageDto (
        Long userId,
        String content,
        Boolean isRead,
        LocalDateTime sendTime
) {
    public static ChatMessageDto of(Chat chat) {
        return ChatMessageDto.builder()
                .userId(chat.getSenderId())
                .content(chat.getContent())
                .isRead(chat.getIsRead())
                .sendTime(chat.getSendTime())
                .build();
    }
}
