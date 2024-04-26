package com.example.task_manager.conf.postgres;

import com.example.task_manager.entity.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(
        basePackages = "com.example.task_manager.repository.postgres",
        basePackageClasses = Task.class,
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresJpaConfiguration {
    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            @Qualifier("postgresDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
                return builder
                        .dataSource(dataSource)
                        .properties(Map.of("hibernate.hbm2ddl.auto", "update"))
                        .packages(Task.class)
                        .build();

    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(postgresEntityManagerFactory.getObject()));
    }

}