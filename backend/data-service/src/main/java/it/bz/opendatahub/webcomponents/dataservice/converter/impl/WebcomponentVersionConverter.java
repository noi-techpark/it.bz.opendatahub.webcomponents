package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentVersionModel;
import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.converter.ModelToDtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentVersionDto;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentVersionConverter extends ModelToDtoToRestConverter<WebcomponentVersionModel, WebcomponentVersionDto, WebcomponentVersion> {

}
