package org.noahnyy.demo.spring.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noahnyy.demo.spring.es.entity.ItemEntity;
import org.noahnyy.demo.spring.es.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author niuyy
 * @since 2019-08-30
 */
@SpringBootTest(classes = BaseConfiguration.class)
@RunWith(SpringRunner.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateIndex(){
        esTemplate.createIndex(ItemEntity.class);
    }

    @Test
    public void testDeleteIndex(){
        esTemplate.deleteIndex(ItemEntity.class);
    }

    @Test
    public void testInsert(){
        ItemEntity entity = new ItemEntity()
                .setId(1L)
                .setTitle("2019新款上衣")
                .setBrand("UNIQ")
                .setCategory("上衣")
                .setPrice(188D)
                .setImage("https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70");
        itemRepository.save(entity);
//        itemRepository.saveAll() 批量新增

        /*
        报错:

        mapper [brand] of different type, current_type [text], merged_type [keyword]

        原因:

        索引新建之后 Entity 有改变, 需要重建索引
         */
    }

    @Test
    public void testUpdate(){
        //ES 的修改其实是删除再新增
        ItemEntity entity = new ItemEntity()
                .setId(1L)
                .setTitle("2019新款上衣2")
                .setBrand("UNIQ")
                .setCategory("上衣")
                .setPrice(188D)
                .setImage("https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70");
        itemRepository.save(entity);
    }

    @Test
    public void testFindAll(){

    }


}
