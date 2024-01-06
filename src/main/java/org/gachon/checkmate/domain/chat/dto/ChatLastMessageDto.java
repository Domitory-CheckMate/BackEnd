package org.gachon.checkmate.domain.chat.dto;

import lombok.Builder;
import org.gachon.checkmate.domain.chat.entity.Chat;

import java.time.LocalDateTime;

@Builder
public record ChatLastMessageDto (
        String content,
        LocalDateTime sendTime
) {
    public static ChatLastMessageDto of(Chat chat) {
        return ChatLastMessageDto.builder()
                .content(chat.getContent())
                .sendTime(chat.getSendTime())
                .build();
    }
    public static ChatLastMessageDto createEmptyChat() {
        return ChatLastMessageDto.builder()
                .content(null)
                .sendTime(null)
                .build();
    }
}
