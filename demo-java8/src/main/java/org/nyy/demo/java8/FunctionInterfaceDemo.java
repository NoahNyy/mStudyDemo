package org.nyy.demo.java8;

import java.sql.Time;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author niuyy
 * @since 2020/3/11
 */
public class FunctionInterfaceDemo {

    public static void main(String[] args) {

        //正常，？标识接收任何类型，String可以被接收
        BiConsumer<Company, ?> setName0 = new BiConsumer<Company, String>() {
            @Override
            public void accept(Company company, String name) {
                company.setName(name);
            }
        };

        //lambda 方式，声明入参类型，则lambda生成的接口是 BiConsumer<Company, String> 类型，可赋值给 BiConsumer<Company, ?>
        BiConsumer<Company, ?> setName01 = (Company company, String name) -> {
            company.setName(name);
        };

        //lambda 方式，入参类型不声明，通过编译器推断，因为使用 BiConsumer<Company, String> 接收，所以推断出
        //company 是 Company 类型，name 是 String 类型， 编译通过
        BiConsumer<Company, String> setName02 = (company, name) -> {
            company.setName(name);
        };

        //因为使用 BiConsumer<Company, ?> 接收，所以推断出 company 是 Company 类型，name 是 ? super Object 类型，
        //也就是 Object 才行，但是 setName 入参是 String 类型，传入的 name 是 Object 类型，
        //所以报错 setName(String) cannot be applied to setName(Object)
        BiConsumer<Company, ?> setName03 = (company, name) -> {
//            company.setName(name);
        };

        //(company, name) -> {company.setName(name);} 等于 Company::setName ，同理
        BiConsumer<Company, String> setName1 = Company::setName;
//        BiConsumer<Company, ?> setName2 = Company::setName;
        BiConsumer<Company, Time> setName = Company::setCreateTime;

        //company 被推断为 Company，lambda的返回值是 String 可以被 ？接收
        Function<Company, ?> getNumber = (company) -> {
            return company.getName();
        };
        Function<Company, ?> getNumber1 = Company::getName;
    }

    static class Company {
        private String name;
        private Date createTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
    }
}
