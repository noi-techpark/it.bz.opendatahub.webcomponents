package it.bz.opendatahub.webcomponents.crawlerservice.interceptor;

import it.bz.opendatahub.webcomponents.crawlerservice.util.AutowireHelper;
import it.bz.opendatahub.webcomponents.crawlerservice.config.GithubConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class GithubHeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    //@Value("${application.repository.githubToken}")
    private GithubConfiguration githubConfiguration;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if(githubConfiguration == null) {
            githubConfiguration = AutowireHelper.getApplicationContext().getAutowireCapableBeanFactory().getBean(GithubConfiguration.class);
        }

        if(githubConfiguration.getToken() != null && !githubConfiguration.getToken().isEmpty() && request.getURI().getHost().equals("api.github.com")) {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "token " + githubConfiguration.getToken());
        }

        return execution.execute(request, body);
    }
}