package org.gachon.checkmate.global.error.exception;

import org.gachon.checkmate.global.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
