package com.ghb.coltpath;

import com.ghb.coltpath.state.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

import java.util.EnumSet;

/**
 * Created by Ghebo on 8/27/2015.
 */
@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "auditorAware")
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


    @Configuration
    @EnableStateMachine
    static class UserStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

        @Override
        public void configure(StateMachineStateConfigurer<String, String> states)
                throws Exception {
            states
                    .withStates()
                    .initial("CREATED")
                    .state("STARTED")
                    .state("APPRENTICE");
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<String, String> transitions)
                throws Exception {
            transitions
                    .withExternal()
                        .source("CREATED").target("STARTED")
                        .event("START")
                        .and()
                    .withExternal()
                    .source("STARTED").target("APPRENTICE")
                    .event("PASS");
        }

        @Configuration
        static class PersistHandlerConfig {

            @Autowired
            private StateMachine<String, String> stateMachine;

            @Bean
            public Persist persist() {
                return new Persist(persistStateMachineHandler());
            }

            @Bean
            public PersistStateMachineHandler persistStateMachineHandler() {
                return new PersistStateMachineHandler(stateMachine);
            }

        }
    }
}