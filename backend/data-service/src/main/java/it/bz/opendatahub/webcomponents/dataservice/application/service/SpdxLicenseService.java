package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadSpdxLicensePort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpdxLicenseService implements GetSpdxLicenseUseCase, ListSpdxLicenseUseCase {
    private final ReadSpdxLicensePort readSpdxLicensePort;

	public SpdxLicenseService(ReadSpdxLicensePort readSpdxLicensePort) {
		this.readSpdxLicensePort = readSpdxLicensePort;
	}

	@Override
    public SpdxLicense getById(@NonNull String id) {
        return readSpdxLicensePort.getById(id);
    }

	@Override
	public Map<String, SpdxLicense> listByIds(@NonNull List<String> licenseIds) {
		return readSpdxLicensePort.listByIds(licenseIds);
	}
}
