package it.bz.opendatahub.webcomponents.dataservice.application.adapter.out.sandbox;

import it.bz.opendatahub.webcomponents.common.stereotype.Adapter;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateCodingSandboxUseCase;
import it.bz.opendatahub.webcomponents.dataservice.application.port.out.CodingSandboxPort;
import lombok.NonNull;

@Adapter
public class StackblitzCodingSandboxAdapter implements CodingSandboxPort {
	@Override
	public String createCodeSandbox(CreateCodingSandboxUseCase.@NonNull CodeSandboxRequest codeSandboxRequest) {
		return null;
	}
}
