package org.nyy.demo.java8.lamda;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author niuyy
 * @since 2020/3/20
 */
public class LambdaUtils {

    /**
     * 方法缓存
     */
    private static Map<Class, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    /**
     * 从lambda表达式获取SerializedLambda实例
     *
     * @param fn lambda表达式
     * @return 获取SerializedLambda
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        if (lambda == null) {
            try {
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }

    /***
     * 转换方法引用为属性名
     *
     * @param fn 方法引用
     * @return 属性名
     */
    public static <T, R> String convertToFieldName(SFunction<T, R> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        return methodToProperty(methodName);
    }

    /**
     * 方法名转换成属性名
     *
     * @param name 方法名
     * @return 属性名
     */
    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
            throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static <T, R> String convertToFieldName(SBiConsumer<T, R> bc) {
        SerializedLambda lambda = getSerializedLambda(bc);
        String methodName = lambda.getImplMethodName();
        return methodToProperty(methodName);
    }
}
