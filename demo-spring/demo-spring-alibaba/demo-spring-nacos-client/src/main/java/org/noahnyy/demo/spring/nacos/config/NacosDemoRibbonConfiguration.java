package org.noahnyy.demo.spring.nacos.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuyy
 * @since 2020/7/2
 */
@Configuration
public class NacosDemoRibbonConfiguration {
    @Bean
    public IRule roundRobinRule(){
        return new RoundRobinRule();
    }
}
