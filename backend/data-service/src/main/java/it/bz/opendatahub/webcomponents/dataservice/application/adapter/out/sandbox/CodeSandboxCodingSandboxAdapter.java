// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.sandbox;

import it.bz.opendatahub.webcomponents.common.stereotype.Adapter;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateCodingSandboxUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.CodingSandboxPort;
import lombok.NonNull;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Adapter
public class CodeSandboxCodingSandboxAdapter implements CodingSandboxPort {

	@Override
	public String createCodeSandbox(CreateCodingSandboxUseCase.@NonNull CodeSandboxRequest codeSandboxRequest){
		try {
			String html = "<!DOCTYPE html>\n<html lang='en'>\n\t<head>\n\t\t<meta charset='UTF-8'>\n\t\t<title>Title</title>"+
				"\n\t</head>\n\t<body>\n\t\t"+
				codeSandboxRequest.codeSnippet +
				"\n\t</body>\n</html>";

			JSONObject jsonString = new JSONObject()
				.put("files", new JSONObject()
					.put("index.html", new JSONObject().put("content", html).put("isBinary", false))
					.put("package.json", new JSONObject()
						.put("content", new JSONObject()
							.put("main", "index.html")
							.put("scripts", new JSONObject()
								.put("start", "parcel index.html --open")
								.put("build", "parcel build index.html"))
							.put("devDependencies", new JSONObject()
								.put("parcel-bundler", "^1.6.1")
							)
						)
					));
			String uri = "https://codesandbox.io/api/v1/sandboxes/define?json=1";
			RestTemplate template = new RestTemplateBuilder().build();

			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<String>(jsonString.toString(),headers);
			ResponseEntity<String> result = template.postForEntity(uri, entity, String.class);
			JSONObject jsonObject = new JSONObject(result.getBody());
			return jsonObject.getString("sandbox_id");
		} catch (Exception e) {
			return "";
		}
	}
}
