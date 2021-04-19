package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.admin;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.converter.WebcomponentWebConverter;
import it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web.rest.WebcomponentRest;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateWebcomponentUseCase;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@WebAdapter
@RestController
@RequestMapping("/admin/webcomponent")
public class WebcomponentAdminController {
	private final CreateWebcomponentUseCase createWebcomponentUseCase;
	private final WebcomponentWebConverter webcomponentWebConverter;

	public WebcomponentAdminController(CreateWebcomponentUseCase createWebcomponentUseCase, WebcomponentWebConverter webcomponentWebConverter) {
		this.createWebcomponentUseCase = createWebcomponentUseCase;
		this.webcomponentWebConverter = webcomponentWebConverter;
	}

	@PostMapping
	public WebcomponentRest createWebcomponent(@RequestBody @Valid CreateWebcomponentUseCase.WebcomponentCreateRequest request) {
		val result = createWebcomponentUseCase.createWebcomponent(request);

		return webcomponentWebConverter.convert(result);
	}
}
