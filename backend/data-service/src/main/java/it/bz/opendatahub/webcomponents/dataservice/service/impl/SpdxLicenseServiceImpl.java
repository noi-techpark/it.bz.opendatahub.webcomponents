package it.bz.opendatahub.webcomponents.dataservice.service.impl;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import it.bz.opendatahub.webcomponents.dataservice.converter.impl.SpdxLicenseConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.SpdxLicenseDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.repository.SpdxLicenseRepository;
import it.bz.opendatahub.webcomponents.dataservice.service.SpdxLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpdxLicenseServiceImpl implements SpdxLicenseService {
    private SpdxLicenseConverter spdxLicenseConverter;
    private SpdxLicenseRepository spdxLicenseRepository;

    @Autowired
    public SpdxLicenseServiceImpl(SpdxLicenseConverter spdxLicenseConverter, SpdxLicenseRepository spdxLicenseRepository) {
        this.spdxLicenseConverter = spdxLicenseConverter;
        this.spdxLicenseRepository = spdxLicenseRepository;
    }

    @Override
    public SpdxLicenseDto getById(String id) {
        Optional<SpdxLicenseModel> probe = spdxLicenseRepository.findById(id);

        if(probe.isPresent()) {
            return spdxLicenseConverter.modelToDto(probe.get());
        }

        throw new NotFoundException("license '"+id+"' not found");
    }
}
