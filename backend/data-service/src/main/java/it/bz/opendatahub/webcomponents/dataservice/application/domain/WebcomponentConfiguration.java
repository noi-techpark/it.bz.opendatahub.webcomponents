package it.bz.opendatahub.webcomponents.dataservice.application.domain;

import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import it.bz.opendatahub.webcomponents.common.data.struct.DistSource;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WebcomponentConfiguration {
	private String webcomponentUuid;

    private String deliveryBaseUrl;

    private Dist dist;

	private Configuration configuration;

	public List<String> getScriptSources() {
		List<String> result = new ArrayList<>();
		if (dist.getScripts().isEmpty()) {
			for (String file : dist.getFiles()) {
				result.add(
					String.format("<script src='%s'></script>",
								  deliveryBaseUrl + "/" + dist.getBasePath() + "/" + file)
				);
			}
		} else {
			for (DistSource script : dist.getScripts()) {
				result.add(
					String.format("<script %s src='%s'></script>",
								  script.getParameter(),
								  deliveryBaseUrl + "/" + dist.getBasePath() + "/" + script.getFile())
				);
			}
		}
		return result;
	}
}
