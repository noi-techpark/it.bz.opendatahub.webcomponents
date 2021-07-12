package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.domain.WebcomponentConfiguration;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.GetWebcomponentConfigurationUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
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

		attribs = parseAttribs(new String(Base64.getDecoder().decode(attribs), StandardCharsets.UTF_8));

		model.addAttribute("snippetScripts", conf.getScriptSources());

		if (!style.isEmpty()) {
			attribs += " style=\"" + style + "\"";
		}

		model.addAttribute("snippetCode", "<" + conf.getConfiguration().getTagName() + attribs + ">" + slot
				+ "</" + conf.getConfiguration().getTagName() + ">");
		return "preview";
	}

	private static String parseAttribs(final String rawString) {
		boolean isKey = true;
		boolean isValue = false;
		String key = "";
		String value = "";
		StringBuilder result = new StringBuilder();
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
							result.append(" ");
							result.append(key.trim());
						}
						break;
					default:
						key += c;
				}
			} else if (isValue) {
				switch (c) {
					case '"':
						result.append(" ");
						result.append(key.trim());
						result.append("=\"");
						result.append(value.trim());
						result.append("\"");
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
		return result.toString();
	}

}
