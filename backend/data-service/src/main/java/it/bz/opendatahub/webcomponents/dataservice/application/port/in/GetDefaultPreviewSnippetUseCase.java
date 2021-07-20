package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface GetDefaultPreviewSnippetUseCase {
	String getDefaultPreviewSnippet(@NonNull String webcomponentUuid, @NonNull String versionTag);
}
