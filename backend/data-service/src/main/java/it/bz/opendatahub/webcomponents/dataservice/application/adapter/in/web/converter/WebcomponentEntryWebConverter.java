package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentEntryRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;

@Converter
public class WebcomponentEntryWebConverter extends BeanConverter<Webcomponent, WebcomponentEntryRest> {
	private final GetWebcomponentVersionUseCase getWebcomponentVersionUseCase;
	private final WebcomponentVersionWebConverter webcomponentVersionConverter;

	private final GetSpdxLicenseUseCase getSpdxLicenseUseCase;
	private final SpdxLicenseWebConverter spdxLicenseConverter;

	public WebcomponentEntryWebConverter(GetWebcomponentVersionUseCase getWebcomponentVersionUseCase, WebcomponentVersionWebConverter webcomponentVersionConverter, GetSpdxLicenseUseCase getSpdxLicenseUseCase, SpdxLicenseWebConverter spdxLicenseConverter) {
		this.getWebcomponentVersionUseCase = getWebcomponentVersionUseCase;
		this.webcomponentVersionConverter = webcomponentVersionConverter;
		this.getSpdxLicenseUseCase = getSpdxLicenseUseCase;
		this.spdxLicenseConverter = spdxLicenseConverter;
	}

	@Override
	public WebcomponentEntryRest convert(Webcomponent dto) {
		WebcomponentEntryRest entry = super.convert(dto);

		try {
			entry.setCurrentVersion(webcomponentVersionConverter.convert(getWebcomponentVersionUseCase.getLatestVersionOfWebcomponent(dto.getUuid())));
		}
		catch (NotFoundException ignored) {
			// nothing to do
		}

		entry.setLicenseString(dto.getLicense());
		try {
			entry.setLicense(spdxLicenseConverter.convert(getSpdxLicenseUseCase.getById(dto.getLicense())));
		}
		catch (NotFoundException ignored) {
			// nothing to do
		}

		return entry;
	}
}
