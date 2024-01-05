package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.chat.entity.Chat;

import java.time.LocalDateTime;

@Builder
public record NewChatResponseDto(
        String chatRoomId,
        Long senderId,
        String content,
        LocalDateTime sendTime
) {
    public static NewChatResponseDto of(Chat chat) {
        return NewChatResponseDto.builder()
                .chatRoomId(chat.getChatRoomId())
                .senderId(chat.getSenderId())
                .content(chat.getContent())
                .sendTime(chat.getSendTime())
                .build();
    }
}
