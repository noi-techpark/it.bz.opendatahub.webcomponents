package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.apache.bcel.classfile.Code;

import javax.validation.constraints.NotBlank;

public interface CreateCodingSandboxUseCase {

	String createCodeSandbox(CodeSandboxRequest codeSandboxRequest);

	@Getter
	@Setter
	class CodeSandboxRequest {
		@NotBlank
		public
		String codeSnippet;

	}
}
