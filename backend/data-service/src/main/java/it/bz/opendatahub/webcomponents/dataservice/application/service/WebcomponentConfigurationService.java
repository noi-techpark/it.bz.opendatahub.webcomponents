package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentConfigurationUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebcomponentConfigurationService implements GetWebcomponentConfigurationUseCase {
	@Value("${application.deliveryBaseUrl}")
	private String deliveryBaseUrl;

	private final ReadWebcomponentPort readWebcomponentPort;
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;

	public WebcomponentConfigurationService(ReadWebcomponentPort readWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
	}

	@Override
	public WebcomponentConfiguration getConfiguration(@NonNull String uuid) {
		val webcomponent = readWebcomponentPort.getWebcomponentById(uuid);

		val latestVersion = readWebcomponentVersionPort.getLatestVersionOfWebcomponent(uuid);

		val configuration = new WebcomponentConfiguration();
		configuration.setWebcomponentUuid(uuid);
		configuration.setConfiguration(latestVersion.getConfiguration());
		configuration.setDeliveryBaseUrl(deliveryBaseUrl);

		configuration.setDist(Dist.of(webcomponent.getUuid(), latestVersion.getDist().getFiles(), latestVersion.getDist().getScripts()));

		return configuration;
	}

	@Override
	public WebcomponentConfiguration getConfiguration(@NonNull String uuid, @NonNull String versionTag) {
		val webcomponent = readWebcomponentPort.getWebcomponentById(uuid);

		val version = readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(uuid, versionTag);

		val configuration = new WebcomponentConfiguration();
		configuration.setWebcomponentUuid(uuid);
		configuration.setConfiguration(version.getConfiguration());
		configuration.setDeliveryBaseUrl(deliveryBaseUrl);

		configuration.setDist(Dist.of(webcomponent.getUuid()+"/"+versionTag, version.getDist().getFiles(), version.getDist().getScripts()));

		return configuration;
	}
}
