package org.nyy.demo.java8.lamda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 支持序列化的 Function
 *
 * @author niuyy
 * @since 2020/3/20
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
