package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebcomponentVersionRest {
    private String versionTag;

    private Date releaseTimestamp;
}
