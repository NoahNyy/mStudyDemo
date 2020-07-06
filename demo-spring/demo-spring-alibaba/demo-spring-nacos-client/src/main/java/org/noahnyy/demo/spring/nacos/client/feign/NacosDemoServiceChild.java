package org.noahnyy.demo.spring.nacos.client.feign;

import org.noahnyy.demo.spring.nacos.controller.NacosDemoServiceExtend;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author niuyy
 * @since 2020/7/6
 */
@FeignClient(name = "nacos-demo", fallbackFactory = NacosDemoServiceChildFallbackFactory.class)
public interface NacosDemoServiceChild extends NacosDemoServiceExtend {
}

