package org.gachon.checkmate.global.socket.error;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocketErrorCode {

    /**
     * 1401 UnAuthorized
     */
    USER_NOT_AUTHORIZED(1401, "접근할 수 있는 권한이 없습니다."),
    EXPIRED_ACCESS_TOKEN(1401, "액세스 토큰이 만료 되었습니다."),
    INVALID_ACCESS_TOKEN_VALUE(1401, "액세스 토큰의 값이 올바르지 않습니다."),


    /**
     * 1404 Not found
     */
    SESSION_ATTRIBUTE_NOT_FOUND(1404, "SessionAttributes를 찾을 수 없습니다."),
    SESSION_ATTRIBUTE_NULL_VALUE(1404, "세션 속성값이 null입니다."),

    /**
     * 1500 Socket Server
     */
    SOCKET_SERVER_ERROR(1500, "소켓 서버 오류입니다.");

    private final int code;
    private final String message;
}
