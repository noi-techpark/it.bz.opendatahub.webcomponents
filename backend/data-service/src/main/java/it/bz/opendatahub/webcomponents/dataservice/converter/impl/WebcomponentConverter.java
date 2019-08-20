package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import it.bz.opendatahub.webcomponents.common.data.rest.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.converter.ModelToDtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentConverter extends ModelToDtoToRestConverter<WebcomponentModel, WebcomponentDto, Webcomponent> {
    private WebcomponentVersionService webcomponentVersionService;
    private WebcomponentVersionConverter webcomponentVersionConverter;

    @Autowired
    public WebcomponentConverter(WebcomponentVersionService webcomponentVersionService, WebcomponentVersionConverter webcomponentVersionConverter) {
        this.webcomponentVersionService = webcomponentVersionService;
        this.webcomponentVersionConverter = webcomponentVersionConverter;
    }

    @Override
    public Webcomponent dtoToRest(WebcomponentDto dto) {
        Webcomponent entry = super.dtoToRest(dto);

        entry.setVersions(webcomponentVersionConverter.dtoToRest(webcomponentVersionService.listAllVersionsOfWebcomponent(dto.getUuid())));

        entry.setDatePublished(entry.getVersions().get(0).getReleaseTimestamp());
        entry.setDateUpdated(entry.getVersions().get(entry.getVersions().size()-1).getReleaseTimestamp());

        return entry;
    }
}
