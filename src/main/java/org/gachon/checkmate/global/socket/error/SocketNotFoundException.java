package org.gachon.checkmate.global.socket.error;

public class SocketNotFoundException extends SocketException {
    public SocketNotFoundException(SocketErrorCode socketErrorCode) {
        super(socketErrorCode);
    }
}
