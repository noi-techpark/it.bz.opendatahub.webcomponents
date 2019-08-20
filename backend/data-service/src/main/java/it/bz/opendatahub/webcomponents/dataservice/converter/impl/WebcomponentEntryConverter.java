package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentEntry;
import it.bz.opendatahub.webcomponents.dataservice.converter.DtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentEntryConverter extends DtoToRestConverter<WebcomponentDto, WebcomponentEntry> {
    private WebcomponentVersionService webcomponentVersionService;
    private WebcomponentVersionConverter webcomponentVersionConverter;

    @Autowired
    public WebcomponentEntryConverter(WebcomponentVersionService webcomponentVersionService, WebcomponentVersionConverter webcomponentVersionConverter) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentVersionConverter = webcomponentVersionConverter;
    }

    @Override
    public WebcomponentEntry dtoToRest(WebcomponentDto dto) {
        WebcomponentEntry entry = super.dtoToRest(dto);

        entry.setCurrentVersion(webcomponentVersionConverter.dtoToRest(webcomponentVersionService.getLatestVersionOfWebcomponent(dto.getUuid())));

        return entry;
    }
}
