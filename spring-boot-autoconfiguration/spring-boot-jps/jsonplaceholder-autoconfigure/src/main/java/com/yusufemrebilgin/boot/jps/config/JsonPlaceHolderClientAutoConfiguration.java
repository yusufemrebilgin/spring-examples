package com.yusufemrebilgin.boot.jps.config;

import com.yusufemrebilgin.boot.jps.client.JsonPlaceHolderClientImpl;
import com.yusufemrebilgin.boot.jps.core.JsonPlaceHolderClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@EnableConfigurationProperties(JsonPlaceHolderClientProperties.class)
@ConditionalOnProperty(name = "jsonplaceholder.client", havingValue = "true", matchIfMissing = true)
public class JsonPlaceHolderClientAutoConfiguration {

    @Bean
    public RestClient jsonPlaceHolderRestClient(JsonPlaceHolderClientProperties jsonPlaceHolderClientProperties) {
        return RestClient.builder().baseUrl(jsonPlaceHolderClientProperties.baseUrl()).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonPlaceHolderClient jsonPlaceHolderClient(RestClient jsonPlaceHolderRestClient) {
        return new JsonPlaceHolderClientImpl(jsonPlaceHolderRestClient);
    }

}
