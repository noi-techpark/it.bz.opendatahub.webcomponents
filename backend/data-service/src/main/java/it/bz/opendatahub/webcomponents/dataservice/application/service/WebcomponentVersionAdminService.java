package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.data.struct.DistFile;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.DeleteWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.RecalculateAllDistSizesUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ReplaceWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ScheduleWebcomponentVersionMetricsUpdateUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.ConflictException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class WebcomponentVersionAdminService implements CreateWebcomponentVersionUseCase, ReplaceWebcomponentVersionUseCase, DeleteWebcomponentVersionUseCase, ScheduleWebcomponentVersionMetricsUpdateUseCase, RecalculateAllDistSizesUseCase {
	private final ReadWebcomponentPort readWebcomponentPort;
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final WriteWebcomponentVersionPort writeWebcomponentVersionPort;
	private final WriteWorkspacePort writeWorkspacePort;
	private final ReadWorkspacePort readWorkspacePort;

	private static final String READ_ME_FILE_NAME = "README.md";
	private static final String LICENSE_FILE_NAME = "LICENSE.md";

	public WebcomponentVersionAdminService(ReadWebcomponentPort readWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort, WriteWebcomponentVersionPort writeWebcomponentVersionPort, WriteWorkspacePort writeWorkspacePort, ReadWorkspacePort readWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.writeWebcomponentVersionPort = writeWebcomponentVersionPort;
		this.writeWorkspacePort = writeWorkspacePort;
		this.readWorkspacePort = readWorkspacePort;
	}

	@Override
	@Transactional
	public WebcomponentVersion createWebcomponentVersion(String webcomponentUuid, WebcomponentVersionCreateRequest request) {
		readWebcomponentPort.getWebcomponentById(webcomponentUuid); // just to throw an exception if it does not exist

		val webcomponentVersion = createDomainObject(webcomponentUuid, request.getDistFiles());

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		webcomponentVersion.setDistSizeTotalKb(
			(int) (readWorkspacePort.getDirectorySizeInBytes(
							Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), "dist")
						) / 1024)
		);

		webcomponentVersion.setReadMe(Arrays.toString(readWorkspacePort.readFile(Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), READ_ME_FILE_NAME))));
		webcomponentVersion.setLicenseAgreement(Arrays.toString(readWorkspacePort.readFile(Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), LICENSE_FILE_NAME))));

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public WebcomponentVersion replaceWebcomponentVersion(String webcomponentUuid, String versionTag, WebcomponentVersionReplaceRequest request) {
		val webcomponentVersion = createDomainObject(webcomponentUuid, request.getDistFiles());

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		wipeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag());
		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		webcomponentVersion.setDistSizeTotalKb(
			(int) (readWorkspacePort.getDirectorySizeInBytes(
				Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), "dist")
			) / 1024)
		);

		webcomponentVersion.setReadMe(Arrays.toString(readWorkspacePort.readFile(Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), READ_ME_FILE_NAME))));
		webcomponentVersion.setLicenseAgreement(Arrays.toString(readWorkspacePort.readFile(Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), LICENSE_FILE_NAME))));


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

	@Override
	@Transactional
	public WebcomponentVersion scheduleMetricsUpdate(String webcomponentUuid, String versionTag) {
		val webcomponentVersion = readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);

		webcomponentVersion.setLighthouseUpdateRequired(true);

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public void recalculateAllDistSizes() {
		val allWebcomponents = readWebcomponentPort.listAll();

		for(val webcomponent : allWebcomponents) {
			val allVersions = readWebcomponentVersionPort.listAllVersionsOfWebcomponent(webcomponent.getUuid());

			for (val webcomponentVersion : allVersions) {
				webcomponentVersion.setDistSizeTotalKb(
					(int) (readWorkspacePort.getDirectorySizeInBytes(
						Paths.get(webcomponentVersion.getWebcomponentUuid(), webcomponentVersion.getVersionTag(), "dist")
					) / 1024)
				);

				writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
			}
		}
	}

	private WebcomponentVersion createDomainObject(String webcomponentUuid, List<DistFile> distFiles) {
		val webcomponentVersion = new WebcomponentVersion();
		webcomponentVersion.setWebcomponentUuid(webcomponentUuid);
		webcomponentVersion.setDeleted(false);
		webcomponentVersion.setDist(createDistFromFiles(distFiles));
		webcomponentVersion.setLighthouseUpdateRequired(true);
		return webcomponentVersion;
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
