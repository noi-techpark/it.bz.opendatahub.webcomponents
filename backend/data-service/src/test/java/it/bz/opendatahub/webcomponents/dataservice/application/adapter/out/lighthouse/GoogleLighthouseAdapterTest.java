package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.lighthouse;

import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsErrorException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsInvalidRequestException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.MetricsUnavailableException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GoogleLighthouseAdapterTest {
	@Test
	void getMetricsForUrlThrowsIfNullArgument() {
		val adapter = new GoogleLighthouseAdapter(Mockito.mock(RestTemplate.class));

		assertThatNullPointerException().isThrownBy(
			() -> adapter.getMetricsForUrl(null)
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "  ", "	"})
	void getMetricsForUrlTrhowsIfEmptyArgument(String empty) {
		val adapter = new GoogleLighthouseAdapter(Mockito.mock(RestTemplate.class));

		assertThatIllegalArgumentException().isThrownBy(
			() -> adapter.getMetricsForUrl(empty)
		);
	}

	@Test
	void getMetricsForUrlUsesApiKeyIfProvided() throws NoSuchFieldException, IllegalAccessException, MetricsInvalidRequestException, MetricsUnavailableException, MetricsErrorException {
		val restTemplate = Mockito.mock(RestTemplate.class);

		val adapter = new GoogleLighthouseAdapter(restTemplate);

		val field = adapter.getClass().getDeclaredField("apiKey");
		field.setAccessible(true);
		field.set(adapter, "TheKey");

		adapter.getMetricsForUrl("https://google.at");

		val captur = ArgumentCaptor.forClass(String.class);
		verify(restTemplate, times(2)).getForObject(captur.capture(), any());

		assertThat(captur.getValue()).contains("key=TheKey");
	}

	@Test
	void getMetricsForUrlQueriesTheDesiredUrl() throws MetricsInvalidRequestException, MetricsUnavailableException, MetricsErrorException {
		val restTemplate = Mockito.mock(RestTemplate.class);

		val adapter = new GoogleLighthouseAdapter(restTemplate);
		adapter.getMetricsForUrl("https://google.at");

		val captur = ArgumentCaptor.forClass(String.class);
		verify(restTemplate, times(2)).getForObject(captur.capture(), any());

		assertThat(captur.getValue()).contains("url=https://google.at");
	}

	@Test
	void getMetricsForUrlReturnsTwoValues() throws MetricsInvalidRequestException, MetricsUnavailableException, MetricsErrorException {
		val restTemplate = Mockito.mock(RestTemplate.class);
		when(restTemplate.getForObject(anyString(), any())).thenReturn("data");

		val adapter = new GoogleLighthouseAdapter(restTemplate);

		val result = adapter.getMetricsForUrl("https://google.at");

		assertThat(result.getResultDesktop()).isEqualTo("data");
		assertThat(result.getResultMobile()).isEqualTo("data");
	}

	@Test
	void getMetricsForUrlThrowsOnHttp4xx() {
		val restTemplate = Mockito.mock(RestTemplate.class);
		when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

		val adapter = new GoogleLighthouseAdapter(restTemplate);

		assertThatExceptionOfType(MetricsInvalidRequestException.class).isThrownBy(
			() -> adapter.getMetricsForUrl("any")
		);
	}

	// PEMOSER Currently disable to test other features
	// @Test
	// void getMetricsForUrlThrowsOnHttp500() {
	// 	val restTemplate = Mockito.mock(RestTemplate.class);
	// 	when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

	// 	val adapter = new GoogleLighthouseAdapter(restTemplate);

	// 	assertThatExceptionOfType(MetricsErrorException.class).isThrownBy(
	// 		() -> adapter.getMetricsForUrl("any")
	// 	);
	// }

	// PEMOSER Currently disable to test other features
	// @Test
	// void getMetricsForUrlThrowsOnHttp5xx() {
	// 	val restTemplate = Mockito.mock(RestTemplate.class);
	// 	when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT));

	// 	val adapter = new GoogleLighthouseAdapter(restTemplate);

	// 	assertThatExceptionOfType(MetricsUnavailableException.class).isThrownBy(
	// 		() -> adapter.getMetricsForUrl("any")
	// 	);
	// }
}
