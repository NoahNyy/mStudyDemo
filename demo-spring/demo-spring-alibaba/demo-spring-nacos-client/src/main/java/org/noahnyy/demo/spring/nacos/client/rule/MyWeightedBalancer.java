package org.noahnyy.demo.spring.nacos.client.rule;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;

/**
 * @author niuyy
 * @since 2020/7/2
 */
public class MyWeightedBalancer extends Balancer {

    public static Instance chooseInstanceByRandomWeight(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}

