package org.noahnyy.demo.spring.nacos.client.controller;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * @author niuyy
 * @since 2020/6/29
 */
@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

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

}
