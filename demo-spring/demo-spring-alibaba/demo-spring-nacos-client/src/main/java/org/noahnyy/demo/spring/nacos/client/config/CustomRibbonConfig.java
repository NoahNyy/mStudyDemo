package org.noahnyy.demo.spring.nacos.client.config;

import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @author niuyy
 * @since 2020/7/2
 */
@RibbonClients(defaultConfiguration = GlobalRibbonConfig.class)
//@Configuration
//@RibbonClients(value = {
//        @RibbonClient(name = "nacos-demo", configuration = NacosDemoRibbonConfiguration.class),
//        @RibbonClient(name = "nacos2-demo", configuration = Nacos2DemoRibbonConfiguration.class)
//})
public class CustomRibbonConfig {
}
