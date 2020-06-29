package org.noahnyy.demo.spring.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@SpringBootApplication
//@EnableDiscoveryClient 现在可以省略该注解
public class NacosDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(NacosDemoApp.class, args);
    }
}
