package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import it.bz.opendatahub.webcomponents.common.data.rest.SpdxLicense;
import it.bz.opendatahub.webcomponents.dataservice.converter.ModelToDtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.SpdxLicenseDto;
import org.springframework.stereotype.Component;

@Component
public class SpdxLicenseConverter extends ModelToDtoToRestConverter<SpdxLicenseModel, SpdxLicenseDto, SpdxLicense> {
}
