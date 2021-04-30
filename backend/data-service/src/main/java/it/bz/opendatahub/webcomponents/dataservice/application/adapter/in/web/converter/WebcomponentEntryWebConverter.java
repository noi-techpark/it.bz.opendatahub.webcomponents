package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentEntryRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListSpdxLicenseUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Converter
public class WebcomponentEntryWebConverter extends BeanConverter<Webcomponent, WebcomponentEntryRest> {
	private final GetWebcomponentVersionUseCase getWebcomponentVersionUseCase;
	private final WebcomponentVersionWebConverter webcomponentVersionConverter;

	private final ListWebcomponentVersionUseCase listWebcomponentVersionUseCase;

	private final ListSpdxLicenseUseCase listSpdxLicenseUseCase;
	private final GetSpdxLicenseUseCase getSpdxLicenseUseCase;
	private final SpdxLicenseWebConverter spdxLicenseConverter;

	public WebcomponentEntryWebConverter(GetWebcomponentVersionUseCase getWebcomponentVersionUseCase, WebcomponentVersionWebConverter webcomponentVersionConverter, ListWebcomponentVersionUseCase listWebcomponentVersionUseCase, ListSpdxLicenseUseCase listSpdxLicenseUseCase, GetSpdxLicenseUseCase getSpdxLicenseUseCase, SpdxLicenseWebConverter spdxLicenseConverter) {
		this.getWebcomponentVersionUseCase = getWebcomponentVersionUseCase;
		this.webcomponentVersionConverter = webcomponentVersionConverter;
		this.listWebcomponentVersionUseCase = listWebcomponentVersionUseCase;
		this.listSpdxLicenseUseCase = listSpdxLicenseUseCase;
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

	@Override
	public Page<WebcomponentEntryRest> convert(Page<Webcomponent> sources) {
		val uuids = new ArrayList<String>();
		val licenceIds = new ArrayList<String>();
		for(val entry : sources) {
			uuids.add(entry.getUuid());
			if(entry.getLicense() != null) {
				licenceIds.add(entry.getLicense());
			}
		}

		val versions = listWebcomponentVersionUseCase.getLatestVersionOfEachWebcomponent(uuids);
		val licenses = listSpdxLicenseUseCase.listByIds(licenceIds);

		val result = new ArrayList<WebcomponentEntryRest>();
		for(val source : sources) {
			val entry = super.convert(source);

			if(versions.containsKey(entry.getUuid())) {
				entry.setCurrentVersion(
					webcomponentVersionConverter.convert(
						versions.get(entry.getUuid())
					)
				);
			}

			if(licenses.containsKey(source.getLicense())) {
				entry.setLicense(
					spdxLicenseConverter.convert(
						licenses.get(source.getLicense())
					)
				);
			}

			result.add(entry);
		}

		return new PageImpl<>(
			result,
			sources.getPageable(),
			sources.getTotalElements()
		);
	}
}
