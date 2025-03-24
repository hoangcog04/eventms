package com.example.eventms.common.exception;

import com.example.eventms.common.enums.ResultCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {

    ResultCode errorCode;

    public AppException(ResultCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(String message) {
        super(message);
    }

    public static void of(ResultCode errorCode) {
        throw new AppException(errorCode);
    }

    public static void of(String message) {
        throw new AppException(message);
    }
}
