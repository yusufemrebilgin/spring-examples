package com.yusufemrebilgin.boot.jps.config;

import com.yusufemrebilgin.boot.jps.client.JsonPlaceHolderClientImpl;
import com.yusufemrebilgin.boot.jps.core.JsonPlaceHolderClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonPlaceHolderClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(JsonPlaceHolderClientAutoConfiguration.class))
            .withPropertyValues("jsonplaceholder.client.enabled:true");


    @Test
    void shouldContainJsonPlaceHolderClientBeans() {
        contextRunner.run(context -> {
            assertTrue(context.containsBean("jsonPlaceHolderClient"));
            assertTrue(context.containsBean("jsonPlaceHolderRestClient"));
        });
    }

    @Test
    void shouldContainSingleBeanIfThereIsAlreadyOne() {
        contextRunner.withUserConfiguration(AnotherJsonPlaceHolderConfiguration.class).run(context -> {
            assertThat(context).hasSingleBean(JsonPlaceHolderClient.class);
            JsonPlaceHolderClient bean = context.getBean(JsonPlaceHolderClient.class);
            assertThat(bean).isSameAs(context.getBean("anotherJsonPlaceHolderClient"));
            // Autoconfiguration bean isn't created due to @ConditionalOnMissingBean
            assertThat(context).doesNotHaveBean("jsonPlaceHolderClient");
            // RestClient bean still created (not conditional)
            assertThat(context).hasBean("jsonPlaceHolderRestClient");
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class AnotherJsonPlaceHolderConfiguration {
        @Bean
        JsonPlaceHolderClient anotherJsonPlaceHolderClient() {
            return new JsonPlaceHolderClientImpl(null);
        }
    }

}