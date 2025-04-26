package com.example.eventms.security.util;

import org.springframework.stereotype.Component;

@Component
public final class SecurityUtil {
    private SecurityUtil() {}
    public static Long getCurUserId() {
        // I'm just mocking data
        return 1L;
    }
}
