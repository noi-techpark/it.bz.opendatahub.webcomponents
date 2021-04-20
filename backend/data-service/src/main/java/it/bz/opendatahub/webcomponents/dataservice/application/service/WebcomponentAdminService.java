package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.common.converter.ConverterUtils;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.DeleteWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ReplaceWebcomponentLogoUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.UpdateWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class WebcomponentAdminService implements CreateWebcomponentUseCase, UpdateWebcomponentUseCase, DeleteWebcomponentUseCase, ReplaceWebcomponentLogoUseCase {
	private final ReadWebcomponentPort readWebcomponentPort;
	private final WriteWebcomponentPort writeWebcomponentPort;

	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final WriteWorkspacePort writeWorkspacePort;

	public WebcomponentAdminService(ReadWebcomponentPort readWebcomponentPort, WriteWebcomponentPort writeWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort, WriteWorkspacePort writeWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.writeWebcomponentPort = writeWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.writeWorkspacePort = writeWorkspacePort;
	}

	@Override
	@Transactional
	public Webcomponent createWebcomponent(@NonNull final WebcomponentCreateRequest request) {
		val webcomponent = new Webcomponent();

		webcomponent.setUuid(
			UUID.randomUUID().toString()
		);
		webcomponent.setDeleted(false);

		ConverterUtils.copyProperties(request, webcomponent);

		return writeWebcomponentPort.saveWebcomponent(webcomponent);
	}

	@Override
	@Transactional
	public Webcomponent updateWebcomponent(@NonNull final String webcomponentUuid, @NonNull final WebcomponentUpdateRequest request) {
		val webcomponent = readWebcomponentPort.getWebcomponentById(webcomponentUuid);

		return writeWebcomponentPort.saveWebcomponent(webcomponent);
	}

	@Override
	@Transactional
	public void deleteWebcomponentById(@NonNull final String uuid) {
		try {
			val webcomponent = readWebcomponentPort.getWebcomponentById(uuid);
			webcomponent.setDeleted(true);
			writeWebcomponentPort.saveWebcomponent(webcomponent);
		}
		catch (NotFoundException ignored) {}
	}

	@Override
	@Transactional
	public Webcomponent replaceLogo(String webcomponentUuid, WebcomponentLogoReplaceRequest request) {
		val latestVersion = readWebcomponentVersionPort.getLatestVersionOfWebcomponent(webcomponentUuid);

		val logoPath = Paths.get(webcomponentUuid, latestVersion.getVersionTag(), "wcs-logo.png");

		writeWorkspacePort.wipe(logoPath);

		writeWorkspacePort.writeFile(logoPath, Base64.getDecoder().decode(request.getLogoPngBase64()));

		return readWebcomponentPort.getWebcomponentById(webcomponentUuid);
	}
}
