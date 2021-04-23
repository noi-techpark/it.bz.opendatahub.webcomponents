package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;
import lombok.NonNull;

public interface GetSpdxLicenseUseCase {
	SpdxLicense getById(@NonNull String id);
}
