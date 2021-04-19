package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.SendEmailUseCase;
import it.bz.opendatahub.webcomponents.dataservice.config.MailerConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@WebAdapter
@RestController
@RequestMapping("/contact")
public class ContactFormController {
	private final SendEmailUseCase sendEmailUseCase;
	private final MailerConfig mailerConfig;

	public ContactFormController(SendEmailUseCase sendEmailUseCase, MailerConfig mailerConfig) {
		this.sendEmailUseCase = sendEmailUseCase;
		this.mailerConfig = mailerConfig;
	}

	@SneakyThrows
	@PostMapping
	public void sendContactForm(@RequestBody @Valid ContactFormRequest request) {
		val requestAsText = "Category: "+nullToEmpty(request.getCategory())+"\n" +
			"First name: "+nullToEmpty(request.getNameFirst())+"\n" +
			"Last name: "+nullToEmpty(request.getNameLast())+"\n" +
			"E-mail: "+nullToEmpty(request.getEmail())+"\n" +
			"Phone: "+nullToEmpty(request.getPhone())+"\n" +
			"Text:\n"+nullToEmpty(request.getText());

		sendEmailUseCase.sendPlaintextEmail(mailerConfig.getTo(), mailerConfig.getSubject(), requestAsText);
	}

	private String nullToEmpty(String in) {
		if(in == null) {
			return "";
		}

		return in;
	}

	@Getter
	@Setter
	public static class ContactFormRequest {
		@NotBlank
		private String category;

		@NotBlank
		private String nameFirst;

		private String nameLast;

		private String email;

		private String phone;

		@NotBlank
		private String text;
	}
}
