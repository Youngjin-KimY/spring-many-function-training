package com.rooon.manyfunctiontutorial.config;

import com.atomikos.spring.AtomikosDataSourceBean;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        value = {"com.rooon.manyfunctiontutorial.repository.mariadb"},
        entityManagerFactoryRef = "mariadbEntityManagerFactory",
        transactionManagerRef = "jtaTransactionManager"
)
public class MariadbConfig {

    @Bean(name = "mariadbDataSource")
    @DependsOn("jtaTransactionManager")
    @ConfigurationProperties("spring.jta.atomikos.datasource.maria")
    public DataSource mariadbDataSource() {
        return new AtomikosDataSourceBean();
    }


    @Bean(name = "mariadbEntityManagerFactory")
    @DependsOn("mariadbDataSource")
    public LocalContainerEntityManagerFactoryBean mariadbEntityManagerFactory() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto","none");
        properties.put("hibernate.physical_naming_strategy",CamelCaseToUnderscoresNamingStrategy.class);
        properties.put("hibernate.dialect","org.hibernate.dialect.MariaDBDialect");
        properties.put("hibernate.transaction.jta.platform",AtomikosJtaPlatform.class);
        properties.put("jakarta.persistence.transactionType","JTA");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mariadbDataSource());
        em.setPackagesToScan("com.rooon.manyfunctiontutorial.domain.mariadb");
        em.setJpaPropertyMap(properties);
        em.setPersistenceUnitName("maria");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean(name = "mariadbTransactionManager")
    @DependsOn("mariadbEntityManagerFactory")
    public JpaTransactionManager mariadbTransactionManager(
        @Qualifier("mariadbEntityManagerFactory") LocalContainerEntityManagerFactoryBean mariadbEntityManagerFactory
    ) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(Objects.requireNonNull(mariadbEntityManagerFactory.getObject()));
        jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());
        return jpaTransactionManager;

    }
}
