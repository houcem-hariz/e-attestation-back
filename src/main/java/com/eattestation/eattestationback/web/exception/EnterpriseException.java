package com.eattestation.eattestationback.web.exception;


import lombok.Getter;

import java.io.Serial;

@Getter
public class EnterpriseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorId;

    private final String responseCode;

    public EnterpriseException(String code, String message) {
        super(message);
        this.responseCode = code;
        this.errorId = null;
    }
}
