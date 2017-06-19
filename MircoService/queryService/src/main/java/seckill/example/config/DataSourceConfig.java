package seckill.example.config;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by wang on 17-6-5.
 */
@Configuration
public class DataSourceConfig {
    @Autowired
    DataSource dataSource;
    /*
    dataSource直接注入，需要数据库连接池则在application.properties中配置
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    */
    @Bean
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        /*下面的两个配置，全部放在application.properties*/
        //为映射文件的resultType设置别名，package批量设置，默认去除包名的类名为别名
        sessionFactory.setTypeAliasesPackage("seckill.example.entity");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
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
    /*对于接口如果使用@Mapper注解可以省掉下面配置
    * Mybatis MapperScannerConfigurer 自动扫描 将Mapper接口生成代理注入到Spring
    * 否则使用MapperFactoryBean，如果存在多个Mapper接口会写多个
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
        mapperScannerConfigurer.setBasePackage("seckill.example.dao");
        return  mapperScannerConfigurer;
    }
    */
}
