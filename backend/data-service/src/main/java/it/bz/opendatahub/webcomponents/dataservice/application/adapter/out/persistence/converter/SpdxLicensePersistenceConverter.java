package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.persistence.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.data.model.SpdxLicenseModel;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.SpdxLicense;

@Converter
public class SpdxLicensePersistenceConverter extends BeanConverter<SpdxLicenseModel, SpdxLicense> {
}
