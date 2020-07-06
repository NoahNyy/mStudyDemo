package org.noahnyy.demo.spring.nacos.client.controller;

import org.noahnyy.demo.spring.nacos.client.feign.NacosDemoService;
import org.noahnyy.demo.spring.nacos.client.feign.NacosDemoServiceChild;
import org.noahnyy.demo.spring.nacos.client.template.MyRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NacosDemoService nacosDemoService;
    @Autowired
    private NacosDemoServiceChild nacosDemoServiceChild;

    @RequestMapping("/test")
    public List<ServiceInstance> getInstances() {
        return this.discoveryClient.getInstances("nacos-demo");
    }

    @RequestMapping("/test1")
    public String callByServiceId() {
        List<ServiceInstance> instances = this.discoveryClient.getInstances("nacos-demo");
        String targetUrl = instances.stream()
                .map(instance -> instance.getUri() + "/test")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("该服务名无实实例"));

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(targetUrl, String.class);
    }

    @RequestMapping("/test2")
    public String callByServiceIdRandom() throws URISyntaxException {
        URI uri = new URI("http://nacos-demo/test");

        MyRestTemplate restTemplate = new MyRestTemplate(discoveryClient);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
        return Objects.requireNonNull(restTemplate.doExecute(uri, HttpMethod.GET, null, responseExtractor)).toString();
    }

    @RequestMapping("/test3")
    public String callUseRibbon() {
        String url = "http://nacos-demo/test";
        return restTemplate.getForObject(url, String.class);
    }

    @RequestMapping("/test4")
    public String callUseRibbon2() {
        String url = "http://nacos2-demo/test";
        return restTemplate.getForObject(url, String.class);
    }

    @RequestMapping("/test5")
    public String callByFeign() {
        return nacosDemoService.getInstances();
    }

    @RequestMapping("/test6")
    public String callByFeignExtend() {
        return nacosDemoServiceChild.getInstances();
    }

    @RequestMapping("/test7")
    public String testAsync(HttpServletRequest request) {
        String token = request.getHeader("token");
        log.info("token->{}", token);
        log.info("thread-main->{}", Thread.currentThread().getName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture.runAsync(() -> {
            log.info("thread-future1->{}", Thread.currentThread().getName());
            RequestContextHolder.setRequestAttributes(requestAttributes);
            nacosDemoServiceChild.getInstances();
        });
        CompletableFuture.runAsync(() -> {
            log.info("thread-future2->{}", Thread.currentThread().getName());
            RequestContextHolder.setRequestAttributes(requestAttributes);
            nacosDemoServiceChild.getInstances();
        });
        return nacosDemoServiceChild.getInstances();
    }

}
