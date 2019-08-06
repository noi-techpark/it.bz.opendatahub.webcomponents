package it.bz.opendatahub.webcomponents.dataservice.converter.impl;

import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentEntry;
import it.bz.opendatahub.webcomponents.dataservice.converter.DtoToRestConverter;
import it.bz.opendatahub.webcomponents.dataservice.data.dto.WebcomponentDto;
import org.springframework.stereotype.Component;

@Component
public class WebcomponentEntryConverter extends DtoToRestConverter<WebcomponentDto, WebcomponentEntry> {

}
