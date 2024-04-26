package com.example.task_manager.conf.postgres;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class PostgresDatasourceConfiguration {

    @Value("${spring.secondary.datasource.url}")
    private String url;
    @Value("${spring.secondary.datasource.username}")
    private String userName;
    @Value("${spring.secondary.datasource.password}")
    private String password;
    @Value("${spring.secondary.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSourceProperties topicsDataSourceProperties() {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.setUrl(url);
            dataSourceProperties.setUsername(userName);
            dataSourceProperties.setPassword(password);
            dataSourceProperties.setDriverClassName(driver);
            return dataSourceProperties;

    }
    @Bean
    public DataSource postgresDataSource() {
        return topicsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

}
