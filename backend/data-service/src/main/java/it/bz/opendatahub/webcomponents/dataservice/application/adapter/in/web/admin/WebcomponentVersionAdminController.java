package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin.converter.WebcomponentVersionAdminWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin.rest.WebcomponentVersionAdminRest;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentVersionWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentVersionRest;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.DeleteWebcomponentVersionUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.ReplaceWebcomponentVersionUseCase;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@WebAdapter
@RestController
@RequestMapping("/admin/webcomponent/{uuid}")
public class WebcomponentVersionAdminController {
	private final CreateWebcomponentVersionUseCase createWebcomponentVersionUseCase;
	private final ReplaceWebcomponentVersionUseCase replaceWebcomponentVersionUseCase;
	private final DeleteWebcomponentVersionUseCase deleteWebcomponentVersionUseCase;
	private final WebcomponentVersionAdminWebConverter webcomponentVersionWebConverter;

	public WebcomponentVersionAdminController(CreateWebcomponentVersionUseCase createWebcomponentVersionUseCase, ReplaceWebcomponentVersionUseCase replaceWebcomponentVersionUseCase, DeleteWebcomponentVersionUseCase deleteWebcomponentVersionUseCase, WebcomponentVersionAdminWebConverter webcomponentVersionWebConverter) {
		this.createWebcomponentVersionUseCase = createWebcomponentVersionUseCase;
		this.replaceWebcomponentVersionUseCase = replaceWebcomponentVersionUseCase;
		this.deleteWebcomponentVersionUseCase = deleteWebcomponentVersionUseCase;
		this.webcomponentVersionWebConverter = webcomponentVersionWebConverter;
	}

	@PostMapping
	public WebcomponentVersionAdminRest createNewVersion(@PathVariable String uuid, @RequestBody @Valid CreateWebcomponentVersionUseCase.WebcomponentVersionCreateRequest request) {
		val result = createWebcomponentVersionUseCase.createWebcomponentVersion(uuid, request);

		return webcomponentVersionWebConverter.convert(result);
	}

	@PutMapping("/{versionTag}")
	public WebcomponentVersionAdminRest replaceVersion(@PathVariable String uuid, @PathVariable String versionTag, @RequestBody @Valid ReplaceWebcomponentVersionUseCase.WebcomponentVersionReplaceRequest request) {
		val result = replaceWebcomponentVersionUseCase.replaceWebcomponentVersion(uuid, versionTag, request);

		return webcomponentVersionWebConverter.convert(result);
	}

	@DeleteMapping("/{versionTag}")
	public void deleteVersion(@PathVariable String uuid, @PathVariable String versionTag) {
		deleteWebcomponentVersionUseCase.deleteWebcomponentVersionById(uuid, versionTag);
	}
}
