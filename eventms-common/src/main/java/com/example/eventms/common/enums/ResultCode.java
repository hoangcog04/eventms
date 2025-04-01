package com.example.eventms.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ResultCode {
    SUCCESS(200, "Operation successful"),
    FAILED(500, "Operation failed"),
    VALIDATE_FAILED(400, "Parameter verification failed"),
    UNAUTHORIZED(401, "Not logged in or token has expired"),
    FORBIDDEN(403, "You do not have permission to access the resource you requested"),
    NOTFOUND(404, "Not found");
    long code;
    String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
