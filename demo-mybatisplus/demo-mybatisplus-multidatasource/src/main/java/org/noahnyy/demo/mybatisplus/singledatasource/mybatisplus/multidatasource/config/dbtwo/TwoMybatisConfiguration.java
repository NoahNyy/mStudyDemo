package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.config.dbtwo;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.constant.StringPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

/**
 * @author niuyy
 * @since 2020/2/18
 */
@Configuration
@EnableTransactionManagement
@MapperScan(
        sqlSessionTemplateRef = "dbtwoSqlSessionTemplate",
        basePackages = {"org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.mapper.dbtwo"}
)
public class TwoMybatisConfiguration {

    @Autowired
    private TwoMybatisConfig twoMybatisConfig;

    @Autowired
    private Environment environment;

    @Bean(name = "dbtwoDataSource")
    public DataSource dbtwoDataSource() {
        return DataSourceBuilder.create()
                .url(twoMybatisConfig.getUrl())
                .username(twoMybatisConfig.getUsername())
                .password(twoMybatisConfig.getPassword())
                .driverClassName(twoMybatisConfig.getDriverClassName())
                .type(twoMybatisConfig.getType())
                .build();
    }

    @Bean(name = "dbtwoSqlSessionFactory")
    public SqlSessionFactory dbtwoSqlSessionFactory(@Qualifier("dbtwoDataSource") DataSource dataSource) throws Exception {
        List<String> activeProfileList = Arrays.asList(environment.getActiveProfiles());
        if (activeProfileList.contains(StringPool.DEV) || activeProfileList.contains(StringPool.TEST)) {
            return getSqlSessionFactory(dataSource, new PaginationInterceptor(), new PerformanceInterceptor());
        }
        return getSqlSessionFactory(dataSource, new PaginationInterceptor(), null);
    }

    private SqlSessionFactory getSqlSessionFactory(DataSource dataSource, PaginationInterceptor paginationInterceptor, @Nullable PerformanceInterceptor performanceInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        GlobalConfig globalConfig = new GlobalConfig().setBanner(false);
        sqlSessionFactory.setGlobalConfig(globalConfig);
        sqlSessionFactory.setTypeAliasesPackage("org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.model.entity.dbtwo");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper.dbtwo.*.xml"));
        if (Objects.isNull(performanceInterceptor)) {
            sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor});
        } else {
            sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor, performanceInterceptor});
        }
        return sqlSessionFactory.getObject();
    }


    @Bean(name = "dbtwoTransactionManager")
    public DataSourceTransactionManager dbtwoTransactionManager(@Qualifier("dbtwoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dbtwoSqlSessionTemplate")
    public SqlSessionTemplate dbtwoSqlSessionTemplate(@Qualifier("dbtwoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
