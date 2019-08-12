package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.rest.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.converter.ModelToDtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import it.bz.opendatahub.webcomponents.common.data.model.WebcomponentModel;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentConverter extends ModelToDtoToRestConverter<WebcomponentModel, WebcomponentDto, Webcomponent> {
}
