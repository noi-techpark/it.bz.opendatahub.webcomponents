package it.bz.opendatahub.webcomponents.dataservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

	@GetMapping(value = "/{uuid}/{versionTag}")
	public String getPreview(@PathVariable final String uuid, @PathVariable(required = true) final String versionTag,
			@RequestParam(defaultValue = "") String style, @RequestParam(defaultValue = "") String slot,
			@MatrixVariable Map<String, String> parameters, Model model) throws UnsupportedEncodingException {

		WebcomponentConfiguration conf;
		if ("latest".equalsIgnoreCase(versionTag)) {
			conf = webcomponentService.getConfiguration(uuid);
		} else {
			conf = webcomponentService.getConfiguration(uuid, versionTag);
		}

		model.addAttribute("snippetScripts", conf.getScriptSources());
		if (!style.isEmpty()) {
			parameters.put("style", style);
		}

		StringJoiner sj = new StringJoiner(" ", " ", "");
		for (Entry<String, String> entry : parameters.entrySet()) {
			sj.add(entry.getKey() + "=\"" + decode(entry.getValue()) + "\"");
		}

		model.addAttribute("snippetCode", "<" + conf.getConfiguration().getTagName() + sj.toString() + ">"
				+ decode(slot) + "</" + conf.getConfiguration().getTagName() + ">");
		return "preview";
	}

	private static String decode(final String rawString) throws UnsupportedEncodingException {
		return URLDecoder.decode(rawString, StandardCharsets.UTF_8.name());
	}

}
