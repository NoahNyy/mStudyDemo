package org.noahnyy.demo.spring.nacos.client.template;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author niuyy
 * @since 2020/6/30
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class MyRestTemplate extends RestTemplate {
    private DiscoveryClient discoveryClient;

    public MyRestTemplate(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;
        try {
            url = replaceUrl(url);

            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }

            response = request.execute();
            handleResponse(url, method, response);
            return (responseExtractor != null ? responseExtractor.extractData(response) : null);
        } catch (IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf('?')) : resource);
            throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private URI replaceUrl(URI url) {

        String serviceName = url.getHost();
        log.info("调用微服务的名称:{}", serviceName);

        String reqPath = url.getPath();
        log.info("请求path:{}", reqPath);


        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
        if (serviceInstanceList.isEmpty()) {
            throw new RuntimeException("没有可用的微服务实例列表:" + serviceName);
        }

        String serviceIp = chooseTargetIp(serviceInstanceList);

        String source = serviceIp + reqPath;
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            log.error("根据source:{}构建URI异常", source);
        }
        return url;
    }

    private String chooseTargetIp(List<ServiceInstance> serviceInstanceList) {
        //采取随机的获取一个
        Random random = new Random();
        int randomIndex = random.nextInt(serviceInstanceList.size());
        String serviceIp = serviceInstanceList.get(randomIndex).getUri().toString();
        log.info("随机选举的服务IP:{}", serviceIp);
        return serviceIp;
    }


}
