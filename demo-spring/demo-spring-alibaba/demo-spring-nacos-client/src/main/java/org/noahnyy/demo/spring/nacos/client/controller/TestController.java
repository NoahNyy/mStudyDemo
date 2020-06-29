package org.noahnyy.demo.spring.nacos.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/test")
    public List<ServiceInstance> getInstances(){
        return this.discoveryClient.getInstances("nacos-demo");
    }

    @RequestMapping("/test1")
    public String callByServiceId(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("nacos-demo");
        String targetUrl = instances.stream()
                .map(instance -> instance.getUri() + "/test")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("该服务名无实实例"));

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(targetUrl, String.class);

    }
}
