import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author niuyy
 * @since 2019/12/20
 */
@Configuration
@ComponentScan(basePackages = "org.nyy.demo")
@EnableAutoConfiguration
public class BaseConfiguration {
}
