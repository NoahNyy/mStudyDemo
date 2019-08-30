package org.noahnyy.demo.spring.es.repository;

import org.noahnyy.demo.spring.es.entity.ItemEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author niuyy
 * @since 2019-08-30
 */
@Repository
public interface ItemRepository extends ElasticsearchRepository<ItemEntity, Long> {
}
