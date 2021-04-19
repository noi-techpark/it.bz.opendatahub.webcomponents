package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebcomponentVersionService implements GetWebcomponentVersionUseCase, ListWebcomponentVersionUseCase {
    private final ReadWebcomponentVersionPort readWebcomponentVersionPort;

	public WebcomponentVersionService(ReadWebcomponentVersionPort readWebcomponentVersionPort) {
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
	}

	@Override
    public List<WebcomponentVersion> listAllVersionsOfWebcomponent(String webcomponentUuid) {
        return readWebcomponentVersionPort.listAllVersionsOfWebcomponent(webcomponentUuid);
    }

    @Override
    public WebcomponentVersion getLatestVersionOfWebcomponent(String webcomponentUuid) {
        return readWebcomponentVersionPort.getLatestVersionOfWebcomponent(webcomponentUuid);
    }

    @Override
    public WebcomponentVersion getSpecificVersionOfWebcomponent(String webcomponentUuid, String versionTag) {
        return readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);
    }
}
