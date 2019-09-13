package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.rest.SpdxLicense;
import it.bz.opendatahub.webcomponents.common.data.rest.Webcomponent;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.converter.ModelToDtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import it.bz.opendatahub.webcomponents.dataservice.service.SpdxLicenseService;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class WebcomponentConverter extends ModelToDtoToRestConverter<WebcomponentModel, WebcomponentDto, Webcomponent> {
    private final WebcomponentVersionService webcomponentVersionService;
    private final WebcomponentVersionConverter webcomponentVersionConverter;

    private final SpdxLicenseService spdxLicenseService;
    private final SpdxLicenseConverter spdxLicenseConverter;

    @Autowired
    public WebcomponentConverter(final WebcomponentVersionService webcomponentVersionService,
                                 final WebcomponentVersionConverter webcomponentVersionConverter,
                                 final SpdxLicenseService spdxLicenseService,
                                 final SpdxLicenseConverter spdxLicenseConverter) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentVersionConverter = webcomponentVersionConverter;
        this.spdxLicenseService = spdxLicenseService;
        this.spdxLicenseConverter = spdxLicenseConverter;
    }

    @Override
    public Webcomponent dtoToRest(WebcomponentDto dto) {
        Webcomponent entry = super.dtoToRest(dto);

        entry.setVersions(webcomponentVersionConverter.dtoToRest(webcomponentVersionService.listAllVersionsOfWebcomponent(dto.getUuid())));

        try {
            entry.setLicense(spdxLicenseConverter.dtoToRest(spdxLicenseService.getById(dto.getLicense())));
        }
        catch (NotFoundException e) {
            SpdxLicense license = new SpdxLicense();
            license.setName(dto.getLicense());
            entry.setLicense(license);
        }

        entry.getVersions().sort(Comparator.comparing(WebcomponentVersion::getReleaseTimestamp));

        entry.setDatePublished(entry.getVersions().get(0).getReleaseTimestamp());
        entry.setDateUpdated(entry.getVersions().get(entry.getVersions().size()-1).getReleaseTimestamp());

        return entry;
    }
}
