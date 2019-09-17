package it.bz.opendatahub.webcomponents.dataservice.service;

import it.bz.opendatahub.webcomponents.dataservice.data.dto.SpdxLicenseDto;

public interface SpdxLicenseService {
    SpdxLicenseDto getById(String id);
}
