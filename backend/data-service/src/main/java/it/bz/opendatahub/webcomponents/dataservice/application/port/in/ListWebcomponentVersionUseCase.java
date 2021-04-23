package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.NonNull;

import java.util.List;

public interface ListWebcomponentVersionUseCase {
	List<WebcomponentVersion> listAllVersionsOfWebcomponent(@NonNull String webcomponentUuid);
}
