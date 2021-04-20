package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.data.struct.DistFile;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public interface CreateWebcomponentVersionUseCase {
	WebcomponentVersion createWebcomponentVersion(String webcomponentUuid, WebcomponentVersionCreateRequest request);

	@Getter
	@Setter
	class WebcomponentVersionCreateRequest {
		private String versionTag;

		private Date releaseTimestamp;

		private List<DistFile> distFiles;

		private Configuration configuration;
	}
}
