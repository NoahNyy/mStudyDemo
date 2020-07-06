package org.noahnyy.demo.spring.nacos.client.feign;

import org.springframework.stereotype.Component;

/**
 * @author niuyy
 * @since 2020/7/6
 */
@Component
public class NacosDemoServiceFallback implements NacosDemoService {
    @Override
    public String getInstances() {
        return "服务降级";
    }
}
