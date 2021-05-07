package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin.converter;

import it.bz.opendatahub.webcomponents.common.converter.BeanConverter;
import it.bz.opendatahub.webcomponents.common.stereotype.Converter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin.rest.WebcomponentVersionAdminRest;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;

@Converter
public class WebcomponentVersionAdminWebConverter extends BeanConverter<WebcomponentVersion, WebcomponentVersionAdminRest> {
}
