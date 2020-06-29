package org.noahnyy.demo.spring.nacos.controller;

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
        return "nacos-demo 被调用";
    }
}
