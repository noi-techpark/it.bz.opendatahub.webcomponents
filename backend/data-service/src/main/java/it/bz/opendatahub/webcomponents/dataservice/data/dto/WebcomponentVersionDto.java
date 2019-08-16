package it.bz.opendatahub.webcomponents.dataservice.data.dto;

import it.bz.opendatahub.webcomponents.dataservice.data.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebcomponentVersionDto implements Dto {
    private String webcomponentUuid;

    private String versionTag;

    private Date releaseTimestamp;
}
