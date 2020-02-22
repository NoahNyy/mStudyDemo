package org.noahnyy.demo.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuyy
 * @since 2020/2/21
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/test1")
    public String test1() {
        return "Hello World!";
    }
}
