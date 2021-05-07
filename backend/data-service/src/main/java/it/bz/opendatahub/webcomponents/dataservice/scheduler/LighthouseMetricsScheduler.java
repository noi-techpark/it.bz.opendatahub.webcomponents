package it.bz.opendatahub.webcomponents.dataservice.scheduler;

import it.bz.opendatahub.webcomponents.dataservice.application.port.in.UpdateWebcomponentVersionMetricsUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LighthouseMetricsScheduler {
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final UpdateWebcomponentVersionMetricsUseCase updateWebcomponentVersionMetricsUseCase;

	public LighthouseMetricsScheduler(ReadWebcomponentVersionPort readWebcomponentVersionPort, UpdateWebcomponentVersionMetricsUseCase updateWebcomponentVersionMetricsUseCase) {
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.updateWebcomponentVersionMetricsUseCase = updateWebcomponentVersionMetricsUseCase;
	}

	@Scheduled(fixedDelayString = "60000")
	public void updateLighthouseMetrics() throws InterruptedException {
		val allVersionsToUpdate = readWebcomponentVersionPort.getAllWithScheduledLighthouseUpdate();

 		for(val webcomponentVersion : allVersionsToUpdate) {
			updateWebcomponentVersionMetricsUseCase.updateMetricsForWebcomponentVersion(webcomponentVersion);
		}

		log.debug("all metrics up to date");
	}
}
