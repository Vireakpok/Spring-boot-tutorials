package com.spring.boot.books.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceInitializationConfiguration {
  @Bean
  public DataSourceInitializer initializer(HikariDataSource dataSource,
      @Value("classpath:/init.sql") Resource sqlScript) {
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();

    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(sqlScript));
    dataSourceInitializer.afterPropertiesSet();
    return dataSourceInitializer;
  }
}
