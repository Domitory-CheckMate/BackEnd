package org.gachon.checkmate.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_ENUM_CODE(HttpStatus.BAD_REQUEST, "잘못된 Enum class code 입니다."),
    INVALID_PAGING_SIZE(HttpStatus.BAD_REQUEST, "잘못된 Paging 크기입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 8~20자 대소문자 영문, 숫자, 특수문자의 조합이어야 합니다."),
    INVALID_POST_TITLE(HttpStatus.BAD_REQUEST, "이미 존재하는 게시물입니다."),
    INVALID_POST_DATE(HttpStatus.BAD_REQUEST, "마감시간이 지난 게시물입니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    CHECK_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "체크리스트를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 게시글을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 리프레시 토큰을 찾을 수 없습니다. 다시 로그인해 주세요."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    DUPLICATE_CHECK_LIST(HttpStatus.CONFLICT, "체크리스트가 이미 존재합니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    EMAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
