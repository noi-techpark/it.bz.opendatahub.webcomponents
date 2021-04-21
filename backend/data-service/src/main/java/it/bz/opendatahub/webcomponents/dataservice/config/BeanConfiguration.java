package it.bz.opendatahub.webcomponents.dataservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate template = builder.build();

        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        return template;
    }
}
