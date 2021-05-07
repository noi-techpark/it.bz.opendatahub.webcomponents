package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface DeleteWebcomponentVersionUseCase {
	void deleteWebcomponentVersionById(@NonNull String webcomponentUuid, @NonNull String versionTag);
}
