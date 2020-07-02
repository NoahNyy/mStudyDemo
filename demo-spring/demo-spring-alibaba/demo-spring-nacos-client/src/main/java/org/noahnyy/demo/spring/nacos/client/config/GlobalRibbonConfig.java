package org.noahnyy.demo.spring.nacos.client.config;

import com.netflix.loadbalancer.IRule;

import org.noahnyy.demo.spring.nacos.client.rule.MyWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuyy
 * @since 2020/7/2
 */
@Configuration
public class GlobalRibbonConfig {

    @Bean
    public IRule roundRobinRule(){
        return new MyWeightedRule();
//        return new RandomRule();
//        return new RoundRobinRule();
    }
}
