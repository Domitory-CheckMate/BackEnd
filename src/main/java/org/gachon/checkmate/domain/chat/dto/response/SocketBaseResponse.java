package org.gachon.checkmate.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gachon.checkmate.domain.chat.dto.MessageType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SocketBaseResponse<T> {
    private MessageType messageType;
    private T data;

    public static <T> SocketBaseResponse<?> of(MessageType messageType, T data) {
        return SocketBaseResponse.builder()
                .messageType(messageType)
                .data(data)
                .build();
    }
}
