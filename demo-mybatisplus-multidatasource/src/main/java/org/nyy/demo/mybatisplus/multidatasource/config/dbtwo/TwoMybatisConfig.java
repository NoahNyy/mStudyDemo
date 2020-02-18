package org.nyy.demo.mybatisplus.multidatasource.config.dbtwo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import lombok.Data;

/**
 * @author niuyy
 * @since 2020/2/18
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.dbtwo")
@Data
public class TwoMybatisConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Class<? extends DataSource> type;
}
