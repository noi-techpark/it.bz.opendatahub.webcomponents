package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebcomponentVersionService implements GetWebcomponentVersionUseCase, ListWebcomponentVersionUseCase {
    private final ReadWebcomponentVersionPort readWebcomponentVersionPort;

	public WebcomponentVersionService(ReadWebcomponentVersionPort readWebcomponentVersionPort) {
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
	}

	@Override
    public List<WebcomponentVersion> listAllVersionsOfWebcomponent(@NonNull String webcomponentUuid) {
        return readWebcomponentVersionPort.listAllVersionsOfWebcomponent(webcomponentUuid);
    }

    @Override
    public WebcomponentVersion getLatestVersionOfWebcomponent(@NonNull String webcomponentUuid) {
        return readWebcomponentVersionPort.getLatestVersionOfWebcomponent(webcomponentUuid);
    }

    @Override
    public WebcomponentVersion getSpecificVersionOfWebcomponent(@NonNull String webcomponentUuid, @NonNull String versionTag) {
        return readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);
    }
}
