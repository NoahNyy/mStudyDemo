package org.noahnyy.demo.spring.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.noahnyy.demo.spring.es.entity.ItemEntity;
import org.noahnyy.demo.spring.es.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
    public void testCreateIndex() {
        esTemplate.createIndex(ItemEntity.class);
    }

    @Test
    public void testDeleteIndex() {
        esTemplate.deleteIndex(ItemEntity.class);
    }

    @Test
    public void testInsert() {
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
    public void testInsertList() {
        List<ItemEntity> list = new ArrayList<>();
        list.add(new ItemEntity(1L, "2019新款上衣3", "上衣", "UNIQ", 187.9D, "https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70"));
        list.add(new ItemEntity(2L, "2019新款上衣4", "上衣", "UNIQQQ", 188.9D, "https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70"));
        list.add(new ItemEntity(3L, "2019新款上衣5", "上衣", "UNIQQQ", 186.9D, "https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70"));
        list.add(new ItemEntity(4L, "2019新款上衣6", "上衣", "UNIQ", 189.9D, "https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70"));
        itemRepository.saveAll(list);
    }

    @Test
    public void testUpdate() {
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
    public void testFindAll() {
        Iterable<ItemEntity> all = itemRepository.findAll();
        all.forEach(System.out::println);

        Iterable<ItemEntity> price = itemRepository.findAll(Sort.by("price"));
        price.forEach(System.out::println);
    }

    @Test
    public void testDIYMethod() {
        //spring 根据方法名自动生成方法的实现
        List<ItemEntity> between = itemRepository.findByPriceBetween(180D, 189D);
        between.forEach(System.out::println);
    }

    @Test
    public void testMathQuery() {
        // Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 利用QueryBuilders来生成一个查询。QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
        // matchQuery:底层就是使用的termQuery
        // A family of match queries that accept text/numerics/dates, analyzes it, and constructs a query out of it.
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "上"));
        // 查询，search 默认就是分页查找
        print(queryBuilder);

        /*
        获取的总条数:4
ItemEntity(id=1, title=2019新款上衣3, category=上衣, brand=UNIQ, price=186.9, image=https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70)
ItemEntity(id=2, title=2019新款上衣4, category=上衣, brand=UNIQ, price=187.9, image=https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70)
ItemEntity(id=3, title=2019新款上衣5, category=上衣, brand=UNIQ, price=188.9, image=https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70)
ItemEntity(id=4, title=2019新款上衣6, category=上衣, brand=UNIQ, price=189.9, image=https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70)
         */
    }


    @Test
    public void testTermQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // termQuery:功能更强大，除了匹配字符串以外，还可以匹配int/long/double/float/....
        builder.withQuery(QueryBuilders.termQuery("price", 187.9D));
        print(builder);
    }


    @Test
    public void testBooleanQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 布尔查询
        builder.withQuery(QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("title", "华为"))
                .must(QueryBuilders.matchQuery("brand", "UNIQ"))
        );
        print(builder); // 获取的总条数:0


        builder = new NativeSearchQueryBuilder();
        // 布尔查询
        builder.withQuery(QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("title", "2019"))
                .must(QueryBuilders.matchQuery("brand", "UNIQ"))
        );
        print(builder); // 获取的总条数:4
    }

    @Test
    public void testFuzzyQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 模糊查询
        // The fuzzy query uses similarity based on Levenshtein edit distance for string fields, and a +/- margin on numeric and date fields.
        builder.withQuery(QueryBuilders.fuzzyQuery("title", "上"));

        print(builder);
    }


    private void print(NativeSearchQueryBuilder builder) {
        Page<ItemEntity> page = itemRepository.search(builder.build());
        long totalElements = page.getTotalElements();
        System.out.println("获取的总条数:" + totalElements);

        page.forEach(System.out::println);
    }

    @Test
    public void searchByPageAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "上衣"));
        // 分页：es 分页从0开始
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        // 搜索，获取结果
        Page<ItemEntity> items = this.itemRepository.search(queryBuilder.build());
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        System.out.println("总页数 = " + items.getTotalPages());
        System.out.println("当前页：" + items.getNumber());
        System.out.println("每页大小：" + items.getSize());
        items.forEach(System.out::println);
    }


    @Test
    public void testAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<ItemEntity> aggPage = (AggregatedPage<ItemEntity>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
        /*
        （1）统计某个字段的数量
  ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
（2）去重统计某个字段的数量（有少量误差）
 CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
（3）聚合过滤
FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));
（4）按某个字段分组
TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
（5）求和
SumBuilder  sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
（6）求平均
AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
（7）求最大值
MaxBuilder mb= AggregationBuilders.max("max_price").field("price");
（8）求最小值
MinBuilder min=	AggregationBuilders.min("min_price").field("price");
（9）按日期间隔分组
DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
（10）获取聚合里面的结果
TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
（11）嵌套的聚合
NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
（12）反转嵌套
AggregationBuilders.reverseNested("res_negsted").path("kps ");
         */
    }

    @Test
    public void testSubAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(AggregationBuilders
                .terms("brands").field("brand")
                .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<ItemEntity> aggPage = (AggregatedPage<ItemEntity>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }


}
