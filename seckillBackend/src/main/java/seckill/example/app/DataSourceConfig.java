package seckill.example.app;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by wang on 17-2-2.
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //为映射文件的resultType设置别名，package批量设置，默认去除包名的类名为别名
        sessionFactory.setTypeAliasesPackage("seckill.example.entity");
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
            sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory.getObject();
    }
    /*
     *如果不是多数据源，无需手动指定DataSourceTransactionManager ,启动类的@EnableTransactionManagement足矣
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    */
    @Bean
    public SqlSession sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        try {
            return new SqlSessionTemplate(sqlSessionFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * Mybatis MapperScannerConfigurer 自动扫描 将Mapper接口生成代理注入到Spring
     * 否则使用MapperFactoryBean，如果存在多个Mapper接口会写多个
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        /*
         必 要 去 指 定 SqlSessionFactory 或 SqlSessionTemplate ,
         因 为 MapperScannerConfigurer 将会创建 MapperFactoryBean,之后自动装配。
         但是,如果你使用了一个以上的 DataSource ,那么自动装配可能会失效
         这种情况下 ,你可以使用 sqlSessionFactoryBeanName 或 sqlSessionTemplateBeanName 属性来设置正确 bean名称来使用。
        try {
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        mapperScannerConfigurer.setBasePackage("seckill.example.dao");
        return  mapperScannerConfigurer;
    }

}
