package org.nyy.demo.java8.lamda;

/**
 * 使用lambda代替字符串
 *
 * @author niuyy
 * @since 2020/3/11
 */
public class LambdaReplaceStringDemo {

    public static void main(String[] args) {
        String name = LambdaUtils.convertToFieldName(User::getName);
        System.out.println(name);
    }


}
