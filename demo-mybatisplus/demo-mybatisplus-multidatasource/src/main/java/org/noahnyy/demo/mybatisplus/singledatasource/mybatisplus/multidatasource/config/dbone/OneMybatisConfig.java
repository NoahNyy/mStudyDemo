package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.config.dbone;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import lombok.Data;

/**
 * @author niuyy
 * @since 2020/2/18
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.dbone")
@Data
public class OneMybatisConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Class<? extends DataSource> type;
}
