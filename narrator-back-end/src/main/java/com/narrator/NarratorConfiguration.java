package com.narrator;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
//@ComponentScan("com.narrator")
//@EntityScan("com.narrator.entity")
@PropertySources(@PropertySource("file:src/main/resources/application.properties"))
// @EnableJpaRepositories(entityManagerFactoryRef = "narratorEntityManager", transactionManagerRef = "narratorTransactionManager", basePackages = { "com.narrator" })
public class NarratorConfiguration {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Primary
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
/*
    @Primary
    @Bean(name = "narratorEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages("com.narrator").persistenceUnit("entity").build();
    }

    @Primary
    @Bean(name = "narratorTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("narratorEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
*/
}
