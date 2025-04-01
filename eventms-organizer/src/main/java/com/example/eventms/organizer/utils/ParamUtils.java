package com.example.eventms.organizer.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ParamUtils {
    private ParamUtils() {
    }

    public static List<String> parse(String param) {
        if (param != null && !param.isEmpty()) return Arrays.asList(param.split(","));
        return Collections.emptyList();
    }
}
