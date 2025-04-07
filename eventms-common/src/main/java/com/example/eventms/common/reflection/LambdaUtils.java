package com.example.eventms.common.reflection;

import com.example.eventms.common.customizer.CFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public final class LambdaUtils {
    private LambdaUtils() {
    }

    public static SerializedLambda getSerializedLambda(CFunction<?, ?> serializable) throws Exception {
        Method method = serializable.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        return (SerializedLambda) method.invoke(serializable);
    }
}
