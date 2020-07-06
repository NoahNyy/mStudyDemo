package org.noahnyy.demo.spring.nacos.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * name: 客户端
 * fallback: 降级服务
 * fallbackFactory: 获取具体的错误日志
 *
 * @author niuyy
 * @since 2020/7/6
 */
public interface NacosDemoServiceExtend {
    @RequestMapping("/test")
    String getInstances();
}
