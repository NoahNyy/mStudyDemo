package org.noahnyy.demo.spring.nacos.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@RestController
public class TestControllerFeign implements NacosDemoServiceExtend {

    @Override
    public String getInstances(){
        System.out.println("nacos2-demo 被调用");
        return "nacos2-demo 被调用";
    }
}
