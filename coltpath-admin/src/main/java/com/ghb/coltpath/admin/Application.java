package com.ghb.coltpath.admin;

import com.ghb.coltpath.core.AuditingDateTimeProvider;
import com.ghb.coltpath.core.ModelAuditor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Ghebo on 8/27/2015.
 */
@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "auditorAware")
@EntityScan(basePackages = {"com.ghb.coltpath.core.model"})
@EnableJpaRepositories(basePackages = {"com.ghb.coltpath.core.repository","com.ghb.coltpath.admin.repository"})
public class Application implements CommandLineRunner {
    private static Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Override
    public void run(String... strings) throws Exception {

    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return new AuditingDateTimeProvider();
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return new ModelAuditor();
    }

}