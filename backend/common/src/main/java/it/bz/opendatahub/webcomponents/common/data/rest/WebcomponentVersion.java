package it.bz.opendatahub.webcomponents.common.data.rest;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebcomponentVersion implements Rest {
    private String versionTag;

    private Date releaseTimestamp;
}
