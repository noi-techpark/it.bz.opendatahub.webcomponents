package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.data.struct.DistFile;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface CreateWebcomponentVersionUseCase {
	WebcomponentVersion createWebcomponentVersion(@NonNull String webcomponentUuid, @NonNull WebcomponentVersionCreateRequest request);

	@Getter
	@Setter
	class WebcomponentVersionCreateRequest {
		@NotBlank
		private String versionTag;

		@NotNull
		private Date releaseTimestamp;

		@NotNull
		private List<DistFile> distFiles;

		@NotNull
		private Configuration configuration;
	}
}
