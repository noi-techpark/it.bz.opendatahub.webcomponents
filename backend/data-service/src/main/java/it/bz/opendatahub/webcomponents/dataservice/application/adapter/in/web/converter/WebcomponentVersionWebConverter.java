package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentVersionRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

@Converter
public class WebcomponentVersionWebConverter extends BeanConverter<WebcomponentVersion, WebcomponentVersionRest> {
}
