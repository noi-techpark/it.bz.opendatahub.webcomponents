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
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class WebcomponentAdminService implements CreateWebcomponentUseCase, UpdateWebcomponentUseCase, DeleteWebcomponentUseCase, ReplaceWebcomponentLogoUseCase {
	public static final String WCS_LOGO_PNG = "wcs-logo.png";
	private final ReadWebcomponentPort readWebcomponentPort;
	private final WriteWebcomponentPort writeWebcomponentPort;

	private final WriteWorkspacePort writeWorkspacePort;

	public WebcomponentAdminService(ReadWebcomponentPort readWebcomponentPort, WriteWebcomponentPort writeWebcomponentPort, WriteWorkspacePort writeWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.writeWebcomponentPort = writeWebcomponentPort;
		this.writeWorkspacePort = writeWorkspacePort;
	}

	@Override
	@Transactional
	public Webcomponent createWebcomponent(@NonNull final WebcomponentCreateRequest request) {
		var webcomponent = new Webcomponent();

		webcomponent.setUuid(
			UUID.randomUUID().toString()
		);
		webcomponent.setImage(WCS_LOGO_PNG);
		webcomponent.setDeleted(false);

		ConverterUtils.copyProperties(request, webcomponent);

		webcomponent = writeWebcomponentPort.saveWebcomponent(webcomponent);

		replaceLogo(
			webcomponent.getUuid(),
			new WebcomponentLogoReplaceRequest(
				request.getImagePngBase64()
			)
		);

		return webcomponent;
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
		readWebcomponentPort.getWebcomponentById(webcomponentUuid); // just to raise a not found exception

		val logoPath = Paths.get(webcomponentUuid, WCS_LOGO_PNG);

		writeWorkspacePort.wipe(logoPath);

		writeWorkspacePort.writeFile(logoPath, Base64.getDecoder().decode(request.getLogoPngBase64()));

		return readWebcomponentPort.getWebcomponentById(webcomponentUuid);
	}
}