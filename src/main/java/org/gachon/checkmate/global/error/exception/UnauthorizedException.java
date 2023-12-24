package org.gachon.checkmate.global.error.exception;

import org.gachon.checkmate.global.error.ErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
