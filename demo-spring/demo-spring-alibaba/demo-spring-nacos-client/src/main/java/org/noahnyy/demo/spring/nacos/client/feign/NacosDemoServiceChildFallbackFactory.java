package org.noahnyy.demo.spring.nacos.client.feign;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

/**
 * @author niuyy
 * @since 2020/7/6
 */
@Component
public class NacosDemoServiceChildFallbackFactory implements FallbackFactory<NacosDemoServiceChild> {
    @Override
    public NacosDemoServiceChild create(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return new NacosDemoServiceChild(){
            @Override
            public String getInstances() {
                return "可以获得原因的降级";
            }
        };
    }
}
