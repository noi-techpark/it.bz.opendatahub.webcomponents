package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateCodingSandboxUseCase;
import lombok.NonNull;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface CodingSandboxPort {

	String createCodeSandbox(@NonNull CreateCodingSandboxUseCase.CodeSandboxRequest codeSandboxRequest);
}
