package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import it.bz.opendatahub.webcomponents.common.stereotype.PersistenceAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter.SpdxLicensePersistenceConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.repository.SpdxLicenseRepository;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadSpdxLicensePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.NonNull;

import java.util.Optional;

@PersistenceAdapter
public class SpdxLicensePersistenceAdapter implements ReadSpdxLicensePort {
	private final SpdxLicenseRepository spdxLicenseRepository;
	private final SpdxLicensePersistenceConverter spdxLicenseConverter;

	public SpdxLicensePersistenceAdapter(SpdxLicenseRepository spdxLicenseRepository, SpdxLicensePersistenceConverter spdxLicenseConverter) {
		this.spdxLicenseRepository = spdxLicenseRepository;
		this.spdxLicenseConverter = spdxLicenseConverter;
	}

	@Override
	public SpdxLicense getById(@NonNull String id) {
		Optional<SpdxLicenseModel> probe = spdxLicenseRepository.findById(id);

		if(probe.isPresent()) {
			return spdxLicenseConverter.convert(probe.get());
		}

		throw new NotFoundException("license '"+id+"' not found");
	}
}
