package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentVersionRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;

import java.util.Comparator;

@Converter
public class WebcomponentWebConverter extends BeanConverter<Webcomponent, WebcomponentRest> {
	private final ListWebcomponentVersionUseCase listWebcomponentVersionUseCase;
	private final WebcomponentVersionWebConverter webcomponentVersionConverter;

	private final GetSpdxLicenseUseCase getSpdxLicenseUseCase;
	private final SpdxLicenseWebConverter spdxLicenseConverter;

	public WebcomponentWebConverter(ListWebcomponentVersionUseCase listWebcomponentVersionUseCase, WebcomponentVersionWebConverter webcomponentVersionConverter, GetSpdxLicenseUseCase getSpdxLicenseUseCase, SpdxLicenseWebConverter spdxLicenseConverter) {
		this.listWebcomponentVersionUseCase = listWebcomponentVersionUseCase;
		this.webcomponentVersionConverter = webcomponentVersionConverter;
		this.getSpdxLicenseUseCase = getSpdxLicenseUseCase;
		this.spdxLicenseConverter = spdxLicenseConverter;
	}

	@Override
	public WebcomponentRest convert(Webcomponent dto) {
		WebcomponentRest entry = super.convert(dto);

		entry.setVersions(webcomponentVersionConverter.convert(listWebcomponentVersionUseCase.listAllVersionsOfWebcomponent(dto.getUuid())));

		entry.setLicenseString(dto.getLicense());
		try {
			entry.setLicense(spdxLicenseConverter.convert(getSpdxLicenseUseCase.getById(dto.getLicense())));
		}
		catch (NotFoundException e) {
			// nothing to do
		}

		entry.getVersions().sort(Comparator.comparing(WebcomponentVersionRest::getReleaseTimestamp).reversed());

		entry.setDateUpdated(entry.getVersions().get(0).getReleaseTimestamp());
		entry.setDatePublished(entry.getVersions().get(entry.getVersions().size()-1).getReleaseTimestamp());

		return entry;
	}
}
