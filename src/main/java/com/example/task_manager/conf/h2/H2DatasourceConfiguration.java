package com.example.task_manager.conf.h2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class H2DatasourceConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;


    @Bean
    public DataSourceProperties H2DataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(userName);
        dataSourceProperties.setPassword(password);
        dataSourceProperties.setDriverClassName(driver);
        return dataSourceProperties;
    }

    @Bean
    @Primary
    public DataSource H2DataSource() {
        return H2DataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

}