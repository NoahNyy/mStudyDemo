package demo.spring.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author niuyy
 * @since 2020/2/23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OAuthClientApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuthClientApp.class, args);
    }
}
