package it.bz.opendatahub.webcomponents.crawlerservice.interceptor;

import it.bz.opendatahub.webcomponents.crawlerservice.config.GithubConfiguration;
import it.bz.opendatahub.webcomponents.crawlerservice.util.AutowireHelper;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class GithubHeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    private GithubConfiguration githubConfiguration;

    @Override
    public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request, byte @NonNull [] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        assureConfigurationIsWired();

        if(tokenAvailableAndRequestToGithub(request)) {
            setTokenInHeader(request);
        }

        return execution.execute(request, body);
    }

    private void assureConfigurationIsWired() {
        if(githubConfiguration == null) {
            githubConfiguration = AutowireHelper.getApplicationContext().getAutowireCapableBeanFactory().getBean(GithubConfiguration.class);
        }
    }

    private boolean tokenAvailableAndRequestToGithub(HttpRequest request) {
        return (
                githubConfiguration.getToken() != null
                && !githubConfiguration.getToken().isEmpty()
                && request.getURI().getHost().equals("api.github.com")
        );
    }

    private void setTokenInHeader(HttpRequest request) {
        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "token " + githubConfiguration.getToken());
    }
}
