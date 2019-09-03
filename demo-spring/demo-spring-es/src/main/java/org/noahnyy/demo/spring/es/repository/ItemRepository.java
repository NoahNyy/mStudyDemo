package org.noahnyy.demo.spring.es.repository;

import org.noahnyy.demo.spring.es.entity.ItemEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author niuyy
 * @since 2019-08-30
 */
@Repository
public interface ItemRepository extends ElasticsearchRepository<ItemEntity, Long> {

    /**
     * 根据价格区间查询
     *
     * @param price1
     * @param price2
     */
    List<ItemEntity> findByPriceBetween(double price1, double price2);
}
