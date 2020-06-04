package it.bz.opendatahub.webcomponents.dataservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
			@RequestParam(defaultValue = "") String attribs, Model model) throws UnsupportedEncodingException {

		WebcomponentConfiguration conf;
		if ("latest".equalsIgnoreCase(versionTag)) {
			conf = webcomponentService.getConfiguration(uuid);
		} else {
			conf = webcomponentService.getConfiguration(uuid, versionTag);
		}

		attribs = parseAttribs(attribs);

		model.addAttribute("snippetScripts", conf.getScriptSources());

		if (!style.isEmpty()) {
			attribs += " style=\"" + style + "\"";
		}

		model.addAttribute("snippetCode", "<" + conf.getConfiguration().getTagName() + attribs + ">" + decode(slot)
				+ "</" + conf.getConfiguration().getTagName() + ">");
		return "preview";
	}

	private static String decode(final String rawString) throws UnsupportedEncodingException {
		return URLDecoder.decode(rawString, StandardCharsets.UTF_8.name());
	}

	private static String parseAttribs(final String rawString) throws UnsupportedEncodingException {
		boolean isKey = true;
		boolean isValue = false;
		String key = "";
		String value = "";
		String result = "";
		for (int i = 0; i < rawString.length(); i++) {
			char c = rawString.charAt(i);
			if (isKey) {
				switch (c) {
					case '=':
						isKey = false;
						break;
					case ';':
					case ' ':
						if (key.trim().length() > 0) {
							result += " " + key.trim();
						}
						break;
					default:
						key += c;
				}
			} else if (isValue) {
				switch (c) {
					case '"':
						result += " " + key.trim() + "=\"" + decode(value.trim()) + "\"";
						isKey = true;
						isValue = false;
						key = "";
						value = "";
						break;
					default:
						value += c;
				}
			} else if (c == '"') {
				isValue = true;
			}
		}
		return result;
	}

}
