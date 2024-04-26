package com.example.task_manager.conf.h2;


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
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(
        basePackages = "com.example.task_manager.repository.h2",
        basePackageClasses = Task.class,
        entityManagerFactoryRef = "H2EntityManagerFactory",
        transactionManagerRef = "H2TransactionManager"

)
public class H2JpaConfiguration {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean H2EntityManagerFactory(
            @Qualifier("H2DataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(dataSource)
                .properties(Map.of("hibernate.hbm2ddl.auto", "update"))
                .packages(Task.class)
                .build();

    }

    @Bean
    public PlatformTransactionManager H2TransactionManager(
            @Qualifier("H2EntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }

    private static LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(DataSource dataSource,
                                                                                                    DataSource dataSourcePostgres,
                                                                                                    EntityManagerFactoryBuilder builder) throws SQLException {
        if (dataSource.getConnection().isValid(1)) {
            return buildLocalContainerEntityManagerFactoryBean(dataSource, builder);
        } else {
            return buildLocalContainerEntityManagerFactoryBean(dataSourcePostgres, builder);
        }
    }

    private static LocalContainerEntityManagerFactoryBean buildLocalContainerEntityManagerFactoryBean(DataSource dataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(Map.of("hibernate.hbm2ddl.auto", "update"))
                .packages(Task.class)
                .build();
    }
}