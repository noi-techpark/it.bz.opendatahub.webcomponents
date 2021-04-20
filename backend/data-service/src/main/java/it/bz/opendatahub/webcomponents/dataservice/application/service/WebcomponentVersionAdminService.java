package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.data.struct.DistFile;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.DeleteWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ReplaceWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.ConflictException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service // TODO: logo image? and manifest?
public class WebcomponentVersionAdminService implements CreateWebcomponentVersionUseCase, ReplaceWebcomponentVersionUseCase, DeleteWebcomponentVersionUseCase {
	private final ReadWebcomponentPort readWebcomponentPort;
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final WriteWebcomponentVersionPort writeWebcomponentVersionPort;
	private final WriteWorkspacePort writeWorkspacePort;

	public WebcomponentVersionAdminService(ReadWebcomponentPort readWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort, WriteWebcomponentVersionPort writeWebcomponentVersionPort, WriteWorkspacePort writeWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.writeWebcomponentVersionPort = writeWebcomponentVersionPort;
		this.writeWorkspacePort = writeWorkspacePort;
	}

	@Override
	@Transactional
	public WebcomponentVersion createWebcomponentVersion(String webcomponentUuid, WebcomponentVersionCreateRequest request) {
		readWebcomponentPort.getWebcomponentById(webcomponentUuid); // just to throw an exception if it does not exist

		val webcomponentVersion = new WebcomponentVersion();
		webcomponentVersion.setWebcomponentUuid(webcomponentUuid);
		webcomponentVersion.setDeleted(false);
		webcomponentVersion.setDist(createDistFromFiles(request.getDistFiles()));

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public WebcomponentVersion replaceWebcomponentVersion(String webcomponentUuid, String versionTag, WebcomponentVersionReplaceRequest request) {
		val webcomponentVersion = new WebcomponentVersion();
		webcomponentVersion.setWebcomponentUuid(webcomponentUuid);
		webcomponentVersion.setDeleted(false);
		webcomponentVersion.setDist(createDistFromFiles(request.getDistFiles()));

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		wipeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag());
		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public void deleteWebcomponentVersionById(String webcomponentUuid, String versionTag) {
		try {
			val webcomponentVersion = readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);

			webcomponentVersion.setDeleted(true);

			writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
		}
		catch (NotFoundException ignored) {}
	}

	private void checkForDuplicates(String webcomponentUuid, WebcomponentVersion webcomponentVersion) {
		try {
			readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, webcomponentVersion.getVersionTag());
			throw new ConflictException("specified version is already present");
		} catch (NotFoundException ignored) {}
	}

	private void wipeDistFiles(String webcomponentUuid, String versionTag) {
		writeWorkspacePort.wipe(Paths.get(webcomponentUuid, versionTag));
	}

	private void storeDistFiles(String webcomponentUuid, String versionTag, List<DistFile> distFiles) {
		for(val file : distFiles) {
			val payload = Base64.getDecoder().decode(file.getFileDataBase64());

			writeWorkspacePort.writeFile(
				Paths.get(webcomponentUuid, versionTag, "dist", file.getFileName()),
				payload
			);
		}
	}

	private Dist createDistFromFiles(List<DistFile> distFiles) {
		val dist = new Dist();
		dist.setBasePath("dist");
		for(val file : distFiles) {
			dist.getFiles().add(file.getFileName());
		}

		return dist;
	}
}
