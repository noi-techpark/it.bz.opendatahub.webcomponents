package it.bz.opendatahub.webcomponents.dataservice.scheduler;

import com.jayway.jsonpath.JsonPath;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.GoogleLighthouseMetrics;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.GoogleLighthousePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class LighthouseMetricsScheduler {
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final WriteWebcomponentVersionPort writeWebcomponentVersionPort;
	private final GoogleLighthousePort googleLighthousePort;

	public LighthouseMetricsScheduler(ReadWebcomponentVersionPort readWebcomponentVersionPort, WriteWebcomponentVersionPort writeWebcomponentVersionPort, GoogleLighthousePort googleLighthousePort) {
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.writeWebcomponentVersionPort = writeWebcomponentVersionPort;
		this.googleLighthousePort = googleLighthousePort;
	}

	@Scheduled(fixedDelayString = "60000")
	public void updateLighthouseMetrics() throws InterruptedException {
		val allVersionsToUpdate = readWebcomponentVersionPort.getAllWithScheduledLighthouseUpdate();

 		for(val webcomponentVersion : allVersionsToUpdate) {
			log.info("updating metrics for: "+webcomponentVersion.getWebcomponentUuid()+"/"+webcomponentVersion.getVersionTag());

			val previewUrl = "https://api.webcomponents.opendatahub.bz.it/preview/"+webcomponentVersion.getWebcomponentUuid()+"/"+webcomponentVersion.getVersionTag()+getAttribs(webcomponentVersion);

			GoogleLighthouseMetrics metrics;
			try {
				metrics = googleLighthousePort.getMetricsForUrl(previewUrl);
			} catch (HttpServerErrorException e) {
				log.error("error fetching metrics: "+e.getMessage());
				//continue;
				metrics = null;
			}

			webcomponentVersion.setLighthouseMetricsDesktopDatetime(LocalDateTime.now());
			webcomponentVersion.setLighthouseMetricsMobileDatetime(LocalDateTime.now());

			if(metrics != null) {
				webcomponentVersion.setLighthouseMetricsMobileData(metrics.getResultMobile());
				webcomponentVersion.setLighthouseMetricsDesktopData(metrics.getResultMobile());

				webcomponentVersion.setLighthouseMobilePerformanceRating(
					extractPerformanceRating(
						metrics.getResultMobile()
					)
				);

				webcomponentVersion.setLighthouseDesktopPerformanceRating(
					extractPerformanceRating(
						metrics.getResultDesktop()
					)
				);
			}
			else {
				webcomponentVersion.setLighthouseDesktopPerformanceRating(0);
				webcomponentVersion.setLighthouseMobilePerformanceRating(0);
			}

			webcomponentVersion.setLighthouseUpdateRequired(false);

			writeWebcomponentVersionPort.saveWebcomponentVersion(
				webcomponentVersion
			);
			log.info("metrics gathered");

			// Thread.sleep(60*1000L);
		}
		log.info("all metrics up to date");
	}

	private int extractPerformanceRating(String json) {
		Number rating = JsonPath.read(json, "$.lighthouseResult.categories.performance.score");

		if(rating == null) {
			return 0;
		}

		return (int) (rating.doubleValue() * 100);
	}

	@SneakyThrows(UnsupportedEncodingException.class)
	private String getAttribs(WebcomponentVersion webcomponentVersion) {
		val options = webcomponentVersion.getConfiguration().getOptions();

		val attribs = new ArrayList<String>();

		for(val option : options) {
			if(!(option.getOptions() instanceof LinkedHashMap)) {
				continue;
			}

			val innerOptions = (LinkedHashMap<String, Object>)option.getOptions();

			if(innerOptions == null) {
				continue;
			}

			if((option.getRequired() != null && option.getRequired()) || innerOptions.containsKey("default")) {
				attribs.add(option.getKey()+"=\""+URLEncoder.encode(String.valueOf(innerOptions.get("default")), StandardCharsets.UTF_8.toString())+"\"");
			}
		}

		if(attribs.isEmpty()) {
			return "";
		}

		return "?attribs="+String.join(";", attribs);
	}
}
