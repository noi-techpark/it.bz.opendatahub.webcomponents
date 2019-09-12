package it.bz.opendatahub.webcomponents.dataservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties("application.workspace")
@Getter
@Setter
public class WorkspaceConfiguration {
    @NotNull
    private String path;
}
