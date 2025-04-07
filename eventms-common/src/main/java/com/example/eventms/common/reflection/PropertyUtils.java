package com.example.eventms.common.reflection;

import com.example.eventms.common.customizer.CFunction;

import java.lang.invoke.SerializedLambda;

public final class PropertyUtils {
    private PropertyUtils() {
    }

    public static String getPropertyName(CFunction<?, ?> serializable) throws Exception {
        SerializedLambda serializedLambda = LambdaUtils.getSerializedLambda(serializable);
        String name = serializedLambda.getImplMethodName();
        if (!name.startsWith("get"))
            throw new RuntimeException("Error parsing property name '" + name + "'. Didn't start with 'get'.");
        return name.substring(3, 4).toLowerCase() + name.substring(4);
    }
}
