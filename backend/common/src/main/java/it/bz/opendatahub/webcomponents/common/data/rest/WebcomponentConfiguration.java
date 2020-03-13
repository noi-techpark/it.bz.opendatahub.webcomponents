package it.bz.opendatahub.webcomponents.common.data.rest;

import java.util.ArrayList;
import java.util.List;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import it.bz.opendatahub.webcomponents.common.data.struct.Configuration;
import it.bz.opendatahub.webcomponents.common.data.struct.Dist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebcomponentConfiguration implements Rest {
	private String webcomponentUuid;

    private String deliveryBaseUrl;

    private Dist dist;

	private Configuration configuration;

	public List<String> getScriptSources() {
		List<String> result = new ArrayList<String>();
		for (String file : dist.getFiles()) {
			result.add(deliveryBaseUrl + "/" + dist.getBasePath() + "/" + file);
		}
		return result;
	}
}
