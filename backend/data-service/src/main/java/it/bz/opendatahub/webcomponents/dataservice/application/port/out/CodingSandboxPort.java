package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateCodingSandboxUseCase;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface CodingSandboxPort {

	String createCodeSandbox(CreateCodingSandboxUseCase.CodeSandboxRequest codeSandboxRequest);
}
