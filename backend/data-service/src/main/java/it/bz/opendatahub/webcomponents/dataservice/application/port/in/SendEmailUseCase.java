package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

import lombok.NonNull;

public interface SendEmailUseCase {
	void sendPlaintextEmail(@NonNull String to, @NonNull String subject, @NonNull String payload);
}
