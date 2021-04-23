package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;
import lombok.NonNull;

public interface ReadSpdxLicensePort {
	SpdxLicense getById(@NonNull String id);
}
