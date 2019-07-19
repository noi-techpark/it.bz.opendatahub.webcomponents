package it.bz.opendatahub.webcomponents.crawlerservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("application.workspace")
@Getter
@Setter
public class WorkspaceConfiguration {
    private String path;
}
