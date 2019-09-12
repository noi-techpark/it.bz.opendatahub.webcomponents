package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.rest.SpdxLicense;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentEntry;
import it.bz.opendatahub.webcomponents.dataservice.converter.DtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.service.SpdxLicenseService;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentEntryConverter extends DtoToRestConverter<WebcomponentDto, WebcomponentEntry> {
    private WebcomponentVersionService webcomponentVersionService;
    private WebcomponentVersionConverter webcomponentVersionConverter;

    private SpdxLicenseService spdxLicenseService;
    private SpdxLicenseConverter spdxLicenseConverter;

    @Autowired
    public WebcomponentEntryConverter(WebcomponentVersionService webcomponentVersionService,
                                      WebcomponentVersionConverter webcomponentVersionConverter,
                                      SpdxLicenseService spdxLicenseService,
                                      SpdxLicenseConverter spdxLicenseConverter) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentVersionConverter = webcomponentVersionConverter;
        this.spdxLicenseService = spdxLicenseService;
        this.spdxLicenseConverter = spdxLicenseConverter;
    }

    @Override
    public WebcomponentEntry dtoToRest(WebcomponentDto dto) {
        WebcomponentEntry entry = super.dtoToRest(dto);

        entry.setCurrentVersion(webcomponentVersionConverter.dtoToRest(webcomponentVersionService.getLatestVersionOfWebcomponent(dto.getUuid())));

        try {
            entry.setLicense(spdxLicenseConverter.dtoToRest(spdxLicenseService.getById(dto.getLicense())));
        }
        catch (NotFoundException e) {
            SpdxLicense license = new SpdxLicense();
            license.setName(dto.getLicense());
            entry.setLicense(license);
        }

        return entry;
    }
}
