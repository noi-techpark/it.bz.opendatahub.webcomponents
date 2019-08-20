package it.bz.opendatahub.webcomponents.common.data.rest;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WebcomponentConfiguration implements Rest {
    private String webcomponentUuid;

    private String deliveryBaseUrl;

    private List<String> dist;

    private Configuration configuration;
}
