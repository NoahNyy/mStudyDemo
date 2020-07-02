package org.noahnyy.demo.spring.nacos2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String getInstances(){
        System.out.println("nacos2-demo 被调用");
        return "nacos2-demo 被调用";
    }
}
