package com.example.eventms.common.exception;

import com.example.eventms.common.enums.ResultCode;

public class Asserts {
    public static void fail(String message) {
        throw new AppException(message);
    }

    public static void fail(ResultCode errorCode) {
        throw new AppException(errorCode);
    }
}
