package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentConfigurationUseCase;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebAdapter
@Controller
@RequestMapping("/preview")
public class PreviewController {
	private final GetWebcomponentConfigurationUseCase getWebcomponentConfigurationUseCase;

	public PreviewController(GetWebcomponentConfigurationUseCase getWebcomponentConfigurationUseCase) {
		this.getWebcomponentConfigurationUseCase = getWebcomponentConfigurationUseCase;
	}

	@GetMapping(value = "/{uuid}/{versionTag}")
	public String getPreview(@PathVariable final String uuid, @PathVariable(required = true) final String versionTag,
			@RequestParam(defaultValue = "") String style, @RequestParam(defaultValue = "") String slot,
			@RequestParam(defaultValue = "") String attribs, Model model) throws UnsupportedEncodingException {

		WebcomponentConfiguration conf;
		if ("latest".equalsIgnoreCase(versionTag)) {
			conf = getWebcomponentConfigurationUseCase.getConfiguration(uuid);
		} else {
			conf = getWebcomponentConfigurationUseCase.getConfiguration(uuid, versionTag);
		}

		var snippet = new String(Base64.getDecoder().decode(attribs), StandardCharsets.UTF_8);

		if (!style.isEmpty()) {
			var firstSpaceIndex = snippet.indexOf(" ");
			snippet = snippet.substring(0, firstSpaceIndex) +" style=\"" + style + "\"" + snippet.substring(firstSpaceIndex);
		}

		if(!slot.isEmpty()) {
			var slotIndex = snippet.lastIndexOf("></") + 1;
			snippet = snippet.substring(0, slotIndex) + slot + snippet.substring(slotIndex);
		}

		model.addAttribute("snippetCode", snippet);
		model.addAttribute("snippetScripts", conf.getScriptSources());

		return "preview";
	}
}
