package it.bz.opendatahub.webcomponents.dataservice.controller;

import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.bz.opendatahub.webcomponents.common.data.rest.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.service.WebcomponentService;

/**
 * PreviewController
 */
@Controller
@RequestMapping("/preview")
public class PreviewController {
	private final WebcomponentService webcomponentService;

	@Autowired
	public PreviewController(final WebcomponentService webcomponentService) {
		this.webcomponentService = webcomponentService;
	}

	@GetMapping(value = "/{uuid}/{versionTag}/{parameters}")
	public String getPreview(@PathVariable final String uuid, @PathVariable(required = true) final String version, @RequestParam(defaultValue = "") String style,
			@RequestParam(defaultValue = "") String slot,
			@MatrixVariable(pathVar = "parameters") Map<String, String> parameters, Model model) {

		WebcomponentConfiguration conf;
		if ("latest".equalsIgnoreCase(version)) {
			conf = webcomponentService.getConfiguration(uuid);
		} else {
			conf = webcomponentService.getConfiguration(uuid, version);
		}

		model.addAttribute("snippetScripts", conf.getScriptSources());
		model.addAttribute("style", style);

		StringJoiner sj = new StringJoiner(" ", " ", "");
		for (Entry<String, String> entry : parameters.entrySet()) {
			sj.add(entry.getKey() + "=\"" + entry.getValue() + "\"");
		}

		model.addAttribute("snippetCode", "<" + conf.getConfiguration().getTagName() + sj.toString() + ">" + slot + "</"
				+ conf.getConfiguration().getTagName() + ">");
		return "preview";
	}

}
