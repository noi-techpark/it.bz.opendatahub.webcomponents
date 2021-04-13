package it.bz.opendatahub.webcomponents.dataservice.service;

public interface EmailService {
	void sendPlaintextEmail(String to, String subject, String payload);
}
