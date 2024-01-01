package org.gachon.checkmate.global.socket.error;

public class SocketUnauthorizedException extends SocketException {
    public SocketUnauthorizedException(SocketErrorCode socketErrorCode) {
        super(socketErrorCode);
    }
}
