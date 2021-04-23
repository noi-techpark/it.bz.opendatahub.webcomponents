package it.bz.opendatahub.webcomponents.dataservice.application.service;

import it.bz.opendatahub.webcomponents.dataservice.application.domain.Webcomponent;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentVersion;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.ReadWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWebcomponentVersionPort;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.WriteWorkspacePort;
import it.bz.opendatahub.webcomponents.dataservice.exception.impl.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebcomponentVersionAdminServiceTest {
	private ReadWebcomponentVersionPort readWebcomponentVersionPort;
	private WriteWebcomponentVersionPort writeWebcomponentVersionPort;

	private ReadWorkspacePort readWorkspacePort;
	private WriteWorkspacePort writeWorkspacePort;

	private ReadWebcomponentPort readWebcomponentPort;

	private WebcomponentVersionAdminService webcomponentVersionAdminService;

	private static final Webcomponent WEBCOMPONENT_A = new Webcomponent();
	private static final WebcomponentVersion WEBCOMPONENT_VERSION_A = new WebcomponentVersion();

	private static final String NON_EXISTING_WEBCOMPONENT_ID = "Z";
	private static final String NON_EXISTING_TAG_ID = "ZZ";

	@BeforeEach
	void initMocks() {
		WEBCOMPONENT_A.setUuid("A");
		WEBCOMPONENT_A.setImage("image.png");
		WEBCOMPONENT_A.setDeleted(false);

		WEBCOMPONENT_VERSION_A.setWebcomponentUuid("A");
		WEBCOMPONENT_VERSION_A.setVersionTag("AA");
		WEBCOMPONENT_VERSION_A.setDeleted(false);

		readWebcomponentPort = Mockito.mock(ReadWebcomponentPort.class);
		readWebcomponentVersionPort = Mockito.mock(ReadWebcomponentVersionPort.class);
		writeWebcomponentVersionPort = Mockito.mock(WriteWebcomponentVersionPort.class);
		readWorkspacePort = Mockito.mock(ReadWorkspacePort.class);
		writeWorkspacePort = Mockito.mock(WriteWorkspacePort.class);

		webcomponentVersionAdminService = new WebcomponentVersionAdminService(readWebcomponentPort, readWebcomponentVersionPort, writeWebcomponentVersionPort, writeWorkspacePort, readWorkspacePort);
	}

	@Test
	void deleteWebcomponentByIdThrowsIfUuidIsNull() {
		assertThatNullPointerException().isThrownBy(
			() -> webcomponentVersionAdminService.deleteWebcomponentVersionById(null, "")
		);
	}

	@Test
	void deleteWebcomponentByIdThrowsIfTagIsNull() {
		assertThatNullPointerException().isThrownBy(
			() -> webcomponentVersionAdminService.deleteWebcomponentVersionById("", null)
		);
	}

	@Test
	void deleteWebcomponentByIdDoesNotThrowIfNotExists() {
		when(readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(NON_EXISTING_WEBCOMPONENT_ID, NON_EXISTING_TAG_ID)).thenThrow(new NotFoundException(""));

		assertThatCode(
			() -> webcomponentVersionAdminService.deleteWebcomponentVersionById(NON_EXISTING_WEBCOMPONENT_ID, NON_EXISTING_TAG_ID)
		).doesNotThrowAnyException();
	}

	@Test
	void deleteWebcomponentByIdDoesNotThrowIfAlreadyDeleted() {
		when(readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag())).thenReturn(WEBCOMPONENT_VERSION_A);
		WEBCOMPONENT_A.setDeleted(true);

		assertThatCode(
			() -> webcomponentVersionAdminService.deleteWebcomponentVersionById(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag())
		).doesNotThrowAnyException();
	}

	@Test
	void deleteWebcomponentByIdDoesSetDeletedFlag() {
		when(readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag())).thenReturn(WEBCOMPONENT_VERSION_A);

		webcomponentVersionAdminService.deleteWebcomponentVersionById(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag());

		assertThat(WEBCOMPONENT_VERSION_A.getDeleted()).isTrue();
	}

	@Test
	void deleteWebcomponentByIdDoesSave() {
		when(readWebcomponentVersionPort.getSpecificVersionOfWebcomponent(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag())).thenReturn(WEBCOMPONENT_VERSION_A);

		webcomponentVersionAdminService.deleteWebcomponentVersionById(WEBCOMPONENT_VERSION_A.getWebcomponentUuid(), WEBCOMPONENT_VERSION_A.getVersionTag());

		verify(writeWebcomponentVersionPort, times(1)).saveWebcomponentVersion(WEBCOMPONENT_VERSION_A);
	}
}
