package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public interface CreateCodingSandboxUseCase {

	String createCodeSandbox(@NonNull CodeSandboxRequest codeSandboxRequest);

	@Getter
	@Setter
	class CodeSandboxRequest {
		@NotBlank
		public
		String codeSnippet;

	}
}
