package org.noahnyy.demo.spring.es;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuyy
 * @since 2019-08-30
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"org.noahnyy.demo.spring.es"})
public class BaseConfiguration {
}
