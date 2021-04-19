package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;

public interface GetSpdxLicenseUseCase {
	SpdxLicense getById(String id);
}
