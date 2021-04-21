package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.lighthouse;

import it.bz.opendatahub.webcomponents.common.stereotype.Adapter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.GoogleLighthouseMetrics;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.GoogleLighthousePort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Adapter
@Slf4j
public class GoogleLighthouseAdapter implements GoogleLighthousePort {
	@Value("${application.google.lighthouse.api-key:null}")
	private String apiKey;

	private final RestTemplate restTemplate;

	public GoogleLighthouseAdapter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public GoogleLighthouseMetrics getMetricsForUrl(String url) {
		val lighthouseUrl = "https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url="+ url+"&key="+apiKey;

		log.debug(lighthouseUrl);

		val desktop = restTemplate.getForObject(lighthouseUrl+"&strategy=desktop", String.class);
		val mobile = restTemplate.getForObject(lighthouseUrl+"&strategy=mobile", String.class);

		val metrics = new GoogleLighthouseMetrics();
		metrics.setResultDesktop(desktop);
		metrics.setResultMobile(mobile);

		return metrics;
	}
}
