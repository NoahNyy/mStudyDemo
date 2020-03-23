package org.nyy.demo.java8.lamda;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 支持序列化的 Function
 *
 * @author niuyy
 * @since 2020/3/20
 */
@FunctionalInterface
public interface SBiConsumer<T, R> extends BiConsumer<T, R>, Serializable {
}
