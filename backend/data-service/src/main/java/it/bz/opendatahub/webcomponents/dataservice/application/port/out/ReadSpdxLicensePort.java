package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;

public interface ReadSpdxLicensePort {
	SpdxLicense getById(String id);
}
