package org.gachon.checkmate.global.error.exception;

import org.gachon.checkmate.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(ErrorCode.BAD_REQUEST);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
