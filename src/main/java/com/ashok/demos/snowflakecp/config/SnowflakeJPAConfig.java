package com.ashok.demos.snowflakecp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.ashok.demos.snowflakecp.repository"},
        entityManagerFactoryRef = "snowflakeEntityManager",
        transactionManagerRef = "snowflakeTransactionManager"
)
public class SnowflakeJPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean snowflakeEntityManager(@Qualifier("snowflakeDatasource") DataSource dataSource,
                                                                         EntityManagerFactoryBuilder builder){
                return builder
                            .dataSource(dataSource)
                        .packages("com.ashok.demos.snowflakecp.entity")
                        .build();
    }

    @Bean
    public PlatformTransactionManager snowflakeTransactionManager(@Qualifier("snowflakeEntityManager") LocalContainerEntityManagerFactoryBean factoryBean){
        return new JpaTransactionManager(Objects.requireNonNull(factoryBean.getObject()));
    }

}
