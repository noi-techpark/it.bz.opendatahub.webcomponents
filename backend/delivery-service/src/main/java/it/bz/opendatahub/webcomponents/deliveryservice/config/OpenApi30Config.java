package it.bz.opendatahub.webcomponents.deliveryservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@OpenAPIDefinition(info = @Info(title = "NOI - WCS", version = "v2"))
public class OpenApi30Config {
	@Value("${application.swaggerBaseUrl:}")
	private String swaggerBaseUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		Server server = new Server();
		server.setUrl(swaggerBaseUrl);
		return new OpenAPI().servers(Collections.singletonList(server));
	}
}
