// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.dataservice.application.port.out;

import it.bz.opendatahub.webcomponents.dataservice.application.port.in.CreateCodingSandboxUseCase;
import lombok.NonNull;

public interface CodingSandboxPort {

	String createCodeSandbox(@NonNull CreateCodingSandboxUseCase.CodeSandboxRequest codeSandboxRequest);
}
