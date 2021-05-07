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
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.UpdateWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.ConflictException;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class WebcomponentVersionAdminService implements CreateWebcomponentVersionUseCase, ReplaceWebcomponentVersionUseCase, DeleteWebcomponentVersionUseCase, ScheduleWebcomponentVersionMetricsUpdateUseCase, RecalculateAllDistSizesUseCase, UpdateWebcomponentVersionUseCase {
	private final ReadWebcomponentPort readWebcomponentPort;
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final WriteWebcomponentVersionPort writeWebcomponentVersionPort;
	private final WriteWorkspacePort writeWorkspacePort;
	private final ReadWorkspacePort readWorkspacePort;

	public WebcomponentVersionAdminService(ReadWebcomponentPort readWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort, WriteWebcomponentVersionPort writeWebcomponentVersionPort, WriteWorkspacePort writeWorkspacePort, ReadWorkspacePort readWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.writeWebcomponentVersionPort = writeWebcomponentVersionPort;
		this.writeWorkspacePort = writeWorkspacePort;
		this.readWorkspacePort = readWorkspacePort;
	}

	@Override
	@Transactional
	public WebcomponentVersion createWebcomponentVersion(@NonNull String webcomponentUuid, @NonNull WebcomponentVersionCreateRequest request) {
		readWebcomponentPort.getWebcomponentById(webcomponentUuid); // just to throw an exception if it does not exist

		val webcomponentVersion = createDomainObject(webcomponentUuid);

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		webcomponentVersion.setDistSizeTotalKb(
			(int) (readWorkspacePort.getDirectorySizeInBytes(
							Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), "dist")
						) / 1024)
		);

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public WebcomponentVersion replaceWebcomponentVersion(@NonNull String webcomponentUuid, @NonNull String versionTag, @NonNull WebcomponentVersionReplaceRequest request) {
		val webcomponentVersion = createDomainObject(webcomponentUuid);

		ConverterUtils.copyProperties(request, webcomponentVersion);

		checkForDuplicates(webcomponentUuid, webcomponentVersion);

		wipeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag());
		storeDistFiles(webcomponentUuid, webcomponentVersion.getVersionTag(), request.getDistFiles());

		webcomponentVersion.setDistSizeTotalKb(
			(int) (readWorkspacePort.getDirectorySizeInBytes(
				Paths.get(webcomponentUuid, webcomponentVersion.getVersionTag(), "dist")
			) / 1024)
		);

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public WebcomponentVersion updateWebcomponentVersion(@NonNull String webcomponentUuid, @NonNull String versionTag, @NonNull WebcomponentVersionUpdateRequest request) {
		val webcomponentVersion = readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);

		ConverterUtils.copyProperties(request, webcomponentVersion);

		return writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
	}

	@Override
	@Transactional
	public void deleteWebcomponentVersionById(@NonNull String webcomponentUuid, @NonNull String versionTag) {
		try {
			val webcomponentVersion = readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, versionTag);

			webcomponentVersion.setDeleted(true);

			writeWebcomponentVersionPort.saveWebcomponentVersion(webcomponentVersion);
		}
		catch (NotFoundException ignored) {}
	}

	@Override
	@Transactional
	public WebcomponentVersion scheduleMetricsUpdate(@NonNull String webcomponentUuid, @NonNull String versionTag) {
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

	private WebcomponentVersion createDomainObject(String webcomponentUuid) {
		val webcomponentVersion = new WebcomponentVersion();

		webcomponentVersion.setWebcomponentUuid(webcomponentUuid);
		webcomponentVersion.setDeleted(false);
		webcomponentVersion.setLighthouseUpdateRequired(true);
		webcomponentVersion.setLighthouseMobilePerformanceRating(0);
		webcomponentVersion.setLighthouseDesktopPerformanceRating(0);

		return webcomponentVersion;
	}

	void checkForDuplicates(String webcomponentUuid, WebcomponentVersion webcomponentVersion) {
		try {
			readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(webcomponentUuid, webcomponentVersion.getVersionTag());
			throw new ConflictException("specified version is already present");
		} catch (NotFoundException ignored) {}
	}

	void wipeDistFiles(String webcomponentUuid, String versionTag) {
		writeWorkspacePort.wipe(Paths.get(webcomponentUuid, versionTag));
	}

	void storeDistFiles(String webcomponentUuid, String versionTag, List<DistFile> distFiles) {
		for(val file : distFiles) {
			val payload = Base64.getDecoder().decode(file.getFileDataBase64());

			writeWorkspacePort.writeFile(
				Paths.get(webcomponentUuid, versionTag, "dist", file.getFileName()),
				payload
			);
		}
	}
}
