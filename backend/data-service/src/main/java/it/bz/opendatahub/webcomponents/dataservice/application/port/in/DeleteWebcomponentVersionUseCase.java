package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

public interface DeleteWebcomponentVersionUseCase {
	void deleteWebcomponentVersionById(String webcomponentUuid, String versionTag);
}
