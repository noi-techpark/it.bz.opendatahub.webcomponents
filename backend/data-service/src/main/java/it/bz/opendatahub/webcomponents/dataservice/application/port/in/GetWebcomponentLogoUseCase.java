package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface GetWebcomponentLogoUseCase {
	byte[] getLogoImage(@NonNull String uuid);
}
