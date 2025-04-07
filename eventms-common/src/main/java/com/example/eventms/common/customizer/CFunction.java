package com.example.eventms.common.customizer;

import java.io.Serializable;
import java.util.function.Function;

/**
 * A Serialization of Lambda is consumed by {@link com.example.eventms.common.reflection.PropertyUtils}.
 *
 * @param <T> same as java.util.function.Function
 * @param <R> same as java.util.function.Function
 */
@FunctionalInterface
public interface CFunction<T, R> extends Function<T, R>, Serializable {
}
