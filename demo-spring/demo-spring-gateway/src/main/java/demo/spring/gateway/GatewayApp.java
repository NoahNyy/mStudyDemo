package demo.spring.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 结合 demo-spring-eureka，demo-spring-oauth2 共同使用
 *
 * 1. 启动 demo-spring-eureka
 * 2. 启动 demo-spring-gateway
 * 3. 启动 demo-oauth-server 和 demo-oauth-client
 * 4. 访问 http://localhost:6001/api-oauth-server/oauth/token?grant_type=password&username=admin&password=123456&scope=all
 *    携带请求头 Authorization ：Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
 *    其中 dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA== 是 base64(clientId:clientSecret)
 * 5. 使用返回的 access_token 访问客户端
 *    http://localhost:6001/api-oauth-client/get
 *    携带请求头 Authorization ：bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODI2MTgxMjcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIzNzAyZjNmOC00ZjUyLTRlY2QtODNiZi1jNDZjMTEzZTA3NjUiLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCIsInNjb3BlIjpbImFsbCJdfQ.yqynxtS6BpWSjnQLyd78HEndYqM7QG79zB94YE6sq70
 *    bearer 后边是 access_token
 *
 *
 * @author niuyy
 * @since 2020/2/23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
