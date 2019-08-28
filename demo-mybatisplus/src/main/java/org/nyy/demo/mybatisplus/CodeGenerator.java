package org.nyy.demo.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import org.nyy.demo.mybatisplus.model.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author niuyy
 * @since 2019-05-23 10:55
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = getGlobalConfig(projectPath);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = getPackageInfo(projectPath);
        mpg.setPackageInfo(pc);
        // 自定义注入配置
        InjectionConfig cfg = getInjectionConfig(projectPath);
        mpg.setCfg(cfg);
        // 模板配置
        TemplateConfig tcfg = getTemplateConfig();
        mpg.setTemplate(tcfg);
        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static StrategyConfig getStrategyConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%95%B0%E6%8D%AE%E5%BA%93%E8%A1%A8%E9%85%8D%E7%BD%AE
        return new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setInclude(scanner("表名"))
                .setRestControllerStyle(Boolean.TRUE)
                .setControllerMappingHyphenStyle(Boolean.FALSE)
                .setEntityTableFieldAnnotationEnable(Boolean.TRUE)
                //@Accessors(fluent = true)
                /*.setEntityBuilderModel(true)*/
                .setSuperEntityClass(BaseEntity.class);
    }

    private static TemplateConfig getTemplateConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%A8%A1%E6%9D%BF%E9%85%8D%E7%BD%AE
        return new TemplateConfig()
                .setController("/template/controller.java")
                .setEntity("/template/entity.java")
                // 不需要自动生成的 xml
                /*.setXml(null)*/;
    }

    private static InjectionConfig getInjectionConfig(String projectPath) {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%B3%A8%E5%85%A5-injectionconfig-%E9%85%8D%E7%BD%AE
        List<FileOutConfig> focList = new ArrayList<>();
		/*focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名称
				return projectPath + "/basic-web-mybatis-plus/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});*/
        return new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入属性, 模板中的属性
            }
            // 文件输出配置
        }.setFileOutConfigList(focList);

    }

    private static PackageConfig getPackageInfo(String projectPath) {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E5%8C%85%E5%90%8D%E9%85%8D%E7%BD%AE
        return new PackageConfig()
                .setParent("org.nyy.demo.mybatisplus")
                .setEntity("model.entity")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setXml(null)
                .setController("web.controller")
                .setPathInfo(new HashMap<String, String>(6) {{
                    put(ConstVal.XML_PATH, projectPath + "/demo-mybatisplus/src/main/resources/mapper/");
                    put(ConstVal.MAPPER_PATH, projectPath + "/demo-mybatisplus/src/main/java/org/nyy/demo/mybatisplus"
                            + "/mapper/");
                    put(ConstVal.SERVICE_PATH, projectPath + "/demo-mybatisplus/src/main/java/org/nyy/demo/mybatisplus"
                            + "/service/");
                    put(ConstVal.SERVICE_IMPL_PATH, projectPath + "/demo-mybatisplus/src/main/java/org/nyy/demo/mybatisplus"
                            + "/service/impl/");
                    put(ConstVal.ENTITY_PATH, projectPath + "/demo-mybatisplus/src/main/java/org/nyy/demo/mybatisplus"
                            + "/model/entity/");
                    put(ConstVal.CONTROLLER_PATH, projectPath + "/demo-mybatisplus/src/main/java/org/nyy/demo/mybatisplus"
                            + "/web/controller/");
                }});
    }

    private static DataSourceConfig getDataSourceConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%95%B0%E6%8D%AE%E6%BA%90-datasourceconfig-%E9%85%8D%E7%BD%AE
        return new DataSourceConfig()
                .setUrl("jdbc:mysql://localhost:3306/mStudyDemo?useUnicode=true&useSSL=false&characterEncoding=utf8")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("admin")
                .setPassword("admin")
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
    }

    private static GlobalConfig getGlobalConfig(String projectPath) {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E5%85%A8%E5%B1%80%E7%AD%96%E7%95%A5-globalconfig-%E9%85%8D%E7%BD%AE
        return new GlobalConfig()
                // 输出目录
                .setOutputDir(projectPath + "/demo-mybatisplus/src/main/java")
                // 是否覆盖已有文件
                .setFileOverride(Boolean.TRUE)
                // 是否打开输出目录
                .setOpen(Boolean.FALSE)
                // 作者名
                .setAuthor(scanner("作者名"))
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columnList
                .setBaseColumnList(true)
                // 时间类型
                .setDateType(DateType.ONLY_DATE)
                // ActiveRecord 模式, 通过实体类增删改查
                .setActiveRecord(false)
                .setControllerName("%sController")
                .setEntityName("%sEntity");
				/*.setMapperName()
				.setServiceImplName()
				.setServiceName()
				.setMapperName()
				.setXmlName()*/
    }
}
