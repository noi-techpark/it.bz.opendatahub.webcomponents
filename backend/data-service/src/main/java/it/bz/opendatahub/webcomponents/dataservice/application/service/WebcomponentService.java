package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentLogoUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ListWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

@Service
public class WebcomponentService implements GetWebcomponentUseCase, ListWebcomponentUseCase, GetWebcomponentLogoUseCase {
	private final ReadWebcomponentPort readWebcomponentPort;
	private final ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private final ReadWorkspacePort readWorkspacePort;

	public WebcomponentService(ReadWebcomponentPort readWebcomponentPort, ReadWebcomponentVersionPort readWebcomponentVersionPort, ReadWorkspacePort readWorkspacePort) {
		this.readWebcomponentPort = readWebcomponentPort;
		this.readWebcomponentVersionPort = readWebcomponentVersionPort;
		this.readWorkspacePort = readWorkspacePort;
	}

	@Override
	public Webcomponent getWebcomponentById(String uuid) {
		return readWebcomponentPort.getWebcomponentById(uuid);
	}

	@Override
	public List<Webcomponent> listAll() {
		return readWebcomponentPort.listAll();
	}

	@Override
	public Page<Webcomponent> listPagedAndFiltered(Pageable pageRequest, WebcomponentFilter filter) {
		return readWebcomponentPort.listPagedAndFiltered(pageRequest, filter);
	}

	@Override
	public byte[] getLogoImage(String uuid) {
		val latestVersion = readWebcomponentVersionPort.getLatestVersionOfWebcomponent(uuid);

		return readWorkspacePort.readFile(Paths.get(uuid, latestVersion.getVersionTag(), "wcs-logo.png"));
	}
}