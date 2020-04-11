package org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource;

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

import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 代码生成器
 *
 * @author niuyy
 * @since 2019/11/15
 */
public class CodeGenerator {

    private static final String CODE_GENERATOR_PROPERTIES_FILE_NAME = "code-generator.properties";
    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private static Properties properties;
    private static Map<String, DbConfig> dbConfigMap;
    private static List<String> dbNameList;
    private static Set<String> moduleSet;
    private static String currentModule;

    /**
     * RUN THIS
     * <p>
     * 运行之前请确保 resources 目录下有文件名为 {@link CodeGenerator#CODE_GENERATOR_PROPERTIES_FILE_NAME}
     * 的文件, 具体属性请参考 {@link CodeGenerator.Key}
     */
    public static void main(String[] args) throws IOException {
        init();

        DbConfig dbConfig = dbConfigMap.get(scanner("请输入要使用的库，可选值" + dbNameList));
        Assert.notNull(dbConfig, "数据库配置未找到！");
        currentModule = scanner("请输入生成文件所在模块，可选值" + moduleSet);
        Assert.notNull(MODULE_NAME_MAP.get(currentModule), "模块未找到！");

        AutoGenerator generator = createGenerator(dbConfig);
        generator.execute();
    }

    private static AutoGenerator createGenerator(DbConfig dbConfig) {
        return new AutoGenerator()
                // 全局配置
                .setGlobalConfig(globalConfig())
                // 数据源配置
                .setDataSource(dataSourceConfig(dbConfig))
                // 包配置
                .setPackageInfo(packageInfo(dbConfig))
                // 自定义注入配置
                .setCfg(injectionConfig())
                // 模板配置
                .setTemplate(templateConfig())
                // 策略配置
                .setStrategy(strategyConfig())
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
                .setTemplateEngine(new FreemarkerTemplateEngine());
    }

    private static void init() throws IOException {
        properties = loadProperties(CODE_GENERATOR_PROPERTIES_FILE_NAME);
        dbNameList = loadDbNameList();
        moduleSet = loadModuleList();
        dbConfigMap = loadAllDbConfig();
    }

    private static Set<String> loadModuleList() {
        return MODULE_NAME_MAP.keySet();
    }

    private static List<String> getFieldValues(Class<?> clz) {
        Field[] declaredFields = clz.getDeclaredFields();
        List<String> valueList = new ArrayList<>(declaredFields.length);
        Stream.of(declaredFields).forEach(field -> {
            try {
                valueList.add((String) field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return valueList;
    }

    private static Map<String, DbConfig> loadAllDbConfig() {
        Map<String, DbConfig> dbConfigMap = new HashMap<>(dbNameList.size());
        dbNameList.forEach(dbName -> {
            DbConfigLoader loader = new DbConfigLoader(dbName);
            dbConfigMap.put(dbName, loader.loadDbConfig(properties));
        });
        return dbConfigMap;
    }

    private static List<String> loadDbNameList() {
        return getFieldValues(DB.class);
    }

    private static Properties loadProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        try (
                InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream(fileName)
        ) {
            properties.load(in);
        } catch (Exception e) {
            System.out.println("加载配置文件出错，请确保 resources 目录下有文件 " + fileName);
            throw e;
        }
        return properties;
    }

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

    private static StrategyConfig strategyConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%95%B0%E6%8D%AE%E5%BA%93%E8%A1%A8%E9%85%8D%E7%BD%AE
        return new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setInclude(scanner("表名").split(","))
                .setRestControllerStyle(Boolean.TRUE)
                .setControllerMappingHyphenStyle(Boolean.FALSE)
                .setEntityTableFieldAnnotationEnable(Boolean.TRUE)
                //@Accessors(fluent = true)
                .setEntityBuilderModel(false);
    }

    private static TemplateConfig templateConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%A8%A1%E6%9D%BF%E9%85%8D%E7%BD%AE
        return new TemplateConfig()
                .setEntity("/template/entity.java")
                // 不需要自动生成的 xml
                /*.setXml(null)*/;
    }

    private static InjectionConfig injectionConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%B3%A8%E5%85%A5-injectionconfig-%E9%85%8D%E7%BD%AE
        List<FileOutConfig> focList = new ArrayList<>();
		/*focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名称
				return projectPath + "/xstu-server-persistence/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
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

    private static PackageConfig packageInfo(DbConfig dbConfig) {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E5%8C%85%E5%90%8D%E9%85%8D%E7%BD%AE
        String name = dbConfig.name;
        String path = PROJECT_PATH + "/demo-mybatisplus-multidatasource/src/main/java/org/nyy/demo/mybatisplus/multidatasource/";
        return new PackageConfig()
                .setParent("org.nyy.demo.mybatisplus.multidatasource")
                .setEntity("model.entity." + name)
                .setService("service." + name)
                .setServiceImpl("service." + name + ".impl")
                .setMapper("mapper." + name)
                .setXml(null)
                .setController(null)
                .setPathInfo(new HashMap<String, String>(6) {{
                    put(ConstVal.XML_PATH, PROJECT_PATH + "/demo-mybatisplus-multidatasource/src/main/resources/mapper/" + name);
                    put(ConstVal.MAPPER_PATH, path + "/mapper/" + name);
                    put(ConstVal.SERVICE_PATH, path + "/service/" + name);
                    put(ConstVal.SERVICE_IMPL_PATH, path + "/service/" + name + "/impl");
                    put(ConstVal.ENTITY_PATH, path + "/model/entity/" + name);
                }});
    }

    private static DataSourceConfig dataSourceConfig(DbConfig dbConfig) {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E6%95%B0%E6%8D%AE%E6%BA%90-datasourceconfig-%E9%85%8D%E7%BD%AE
        return new DataSourceConfig()
                .setUrl(dbConfig.url)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(dbConfig.username)
                .setPassword(dbConfig.password)
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
    }

    private static GlobalConfig globalConfig() {
        // 官方文档 https://mybatis.plus/config/generator-config.html#%E5%85%A8%E5%B1%80%E7%AD%96%E7%95%A5-globalconfig-%E9%85%8D%E7%BD%AE
        return new GlobalConfig()
                // 输出目录
                .setOutputDir(PROJECT_PATH + "/demo-mybatisplus-multidatasource/src/main/java")
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
                .setEntityName("%sEntity");
				/*.setMapperName()
				.setControllerName("%sController")
				.setServiceImplName()
				.setServiceName()
				.setMapperName()
				.setXmlName()*/
    }

    interface DB {
        /**
         * 财务共享库
         */
        String DBONE = "dbone";
        /**
         * 韵达基础库
         */
        String DBTWO = "dbtwo";
    }

    public static final Map<String, String> MODULE_NAME_MAP = new HashMap<String, String>(2) {{
        put("dbone", "dbone");
        put("dbtwo", "dbtwo");
    }};


    static class DbConfig {
        String username;
        String password;
        String url;
        String name;
    }

    static class DbConfigLoader {

        private String dbName;
        private String dbUrl;
        private String dbUsername;
        private String dbPassword;

        DbConfigLoader(String dbName) {
            this.dbName = dbName;
            this.dbUrl = dbName + "." + Str.URL;
            this.dbUsername = dbName + "." + Str.USERNAME;
            this.dbPassword = dbName + "." + Str.PASSWORD;
        }

        DbConfig loadDbConfig(Properties properties) {
            DbConfig dbConfig = new DbConfig();
            dbConfig.name = dbName;
            dbConfig.url = properties.getProperty(dbUrl);
            dbConfig.username = properties.getProperty(dbUsername);
            dbConfig.password = properties.getProperty(dbPassword);
            return dbConfig;
        }
    }

    interface Str {
        String URL = "url";
        String USERNAME = "username";
        String PASSWORD = "password";
    }

    interface Key {
        String DBONE_URL = "dbone.url";
        String DBONE_USERNAME = "dbone.username";
        String DBONE_PASSWORD = "dbone.password";

        String DBTWO_URL = "dbtwo.url";
        String DBTWO_USERNAME = "dbtwo.username";
        String DBTWO_PASSWORD = "dbtwo.password";
    }
}
