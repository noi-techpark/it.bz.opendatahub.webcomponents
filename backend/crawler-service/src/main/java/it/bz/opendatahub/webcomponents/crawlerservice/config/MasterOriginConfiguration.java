package it.bz.opendatahub.webcomponents.crawlerservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties("application.repository.origin")
@Getter
@Setter
public class MasterOriginConfiguration {
    @NotNull
    private String url;

    @NotNull
    private String branch;
}
