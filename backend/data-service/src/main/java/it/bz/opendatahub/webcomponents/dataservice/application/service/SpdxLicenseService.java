package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadSpdxLicensePort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SpdxLicenseService implements GetSpdxLicenseUseCase {
    private final ReadSpdxLicensePort readSpdxLicensePort;

	public SpdxLicenseService(ReadSpdxLicensePort readSpdxLicensePort) {
		this.readSpdxLicensePort = readSpdxLicensePort;
	}

	@Override
    public SpdxLicense getById(@NonNull String id) {
        return readSpdxLicensePort.getById(id);
    }
}
