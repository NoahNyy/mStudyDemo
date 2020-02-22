package org.noahnyy.demo.spring.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author niuyy
 * @since 2020/2/18
 */
@Data
@ConfigurationProperties(prefix = "audience")
@Component
public class JwtAudienceConfig {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
}
