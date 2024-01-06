package org.gachon.checkmate.global.socket.handler;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.global.socket.error.SocketErrorCode;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex) {
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    // JWT 예외
    private Message<byte[]> errorMessage(SocketErrorCode socketErrorCode){
        String code = String.valueOf(socketErrorCode.getMessage());
        StompHeaderAccessor accessor = getStompHeaderAccessor(socketErrorCode);
        return MessageBuilder.createMessage(code.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

    private StompHeaderAccessor getStompHeaderAccessor(SocketErrorCode socketErrorCode) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(socketErrorCode.getMessage());
        accessor.setLeaveMutable(true);
        return accessor;
    }
}
