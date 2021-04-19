package it.bz.opendatahub.webcomponents.dataservice.application.domain;

import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebcomponentVersion {
	private String webcomponentUuid;

	private String versionTag;

	private Date releaseTimestamp;

	private Dist dist;

	private Configuration configuration;

	private Boolean deleted;
}
