package org.noahnyy.demo.spring.boot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author niuyy
 * @since 2019-06-21
 */
@Configuration
@Import(WebConfiguration.class)
public class WebAutoConfiguration {
}
