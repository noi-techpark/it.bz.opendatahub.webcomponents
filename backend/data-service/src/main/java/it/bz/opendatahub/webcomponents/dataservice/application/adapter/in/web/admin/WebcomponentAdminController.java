package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentRest;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.DeleteWebcomponentUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.UpdateWebcomponentUseCase;
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
@RequestMapping("/admin/webcomponent")
public class WebcomponentAdminController {
	private final UpdateWebcomponentUseCase updateWebcomponentUseCase;
	private final DeleteWebcomponentUseCase deleteWebcomponentUseCase;
	private final CreateWebcomponentUseCase createWebcomponentUseCase;
	private final WebcomponentWebConverter webcomponentWebConverter;

	public WebcomponentAdminController(UpdateWebcomponentUseCase updateWebcomponentUseCase, DeleteWebcomponentUseCase deleteWebcomponentUseCase, CreateWebcomponentUseCase createWebcomponentUseCase, WebcomponentWebConverter webcomponentWebConverter) {
		this.updateWebcomponentUseCase = updateWebcomponentUseCase;
		this.deleteWebcomponentUseCase = deleteWebcomponentUseCase;
		this.createWebcomponentUseCase = createWebcomponentUseCase;
		this.webcomponentWebConverter = webcomponentWebConverter;
	}

	@PostMapping
	public WebcomponentRest createWebcomponent(@RequestBody @Valid CreateWebcomponentUseCase.WebcomponentCreateRequest request) {
		val result = createWebcomponentUseCase.createWebcomponent(request);

		return webcomponentWebConverter.convert(result);
	}

	@PutMapping("/{uuid}")
	public WebcomponentRest updateWebcomponent(@PathVariable String uuid, @RequestBody @Valid UpdateWebcomponentUseCase.WebcomponentUpdateRequest request) {
		val result = updateWebcomponentUseCase.updateWebcomponent(uuid, request);

		return webcomponentWebConverter.convert(result);
	}

	@DeleteMapping("/{uuid}")
	public void deleteWebcomponent(@PathVariable String uuid) {
		deleteWebcomponentUseCase.deleteWebcomponentById(uuid);
	}
}
