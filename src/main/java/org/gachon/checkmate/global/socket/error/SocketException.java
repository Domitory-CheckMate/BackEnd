package org.gachon.checkmate.global.socket.error;

import lombok.Getter;
import org.springframework.messaging.MessageDeliveryException;

@Getter
public class SocketException extends MessageDeliveryException  {
    private final SocketErrorCode socketErrorCode;

    public SocketException(SocketErrorCode socketErrorCode) {
        super(socketErrorCode.getMessage());
        this.socketErrorCode = socketErrorCode;
    }
}
