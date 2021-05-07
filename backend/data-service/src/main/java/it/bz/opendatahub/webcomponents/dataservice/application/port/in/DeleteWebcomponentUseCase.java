package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface DeleteWebcomponentUseCase {
	void deleteWebcomponentById(@NonNull String uuid);
}
