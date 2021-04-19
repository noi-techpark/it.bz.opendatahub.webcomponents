package it.bz.opendatahub.webcomponents.dataservice.application.port.in;

public interface SendEmailUseCase {
	void sendPlaintextEmail(String to, String subject, String payload);
}
