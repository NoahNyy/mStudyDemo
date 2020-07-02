package org.noahnyy.demo.spring.nacos.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@SpringBootApplication
public class NacosClientDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(NacosClientDemoApp.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
