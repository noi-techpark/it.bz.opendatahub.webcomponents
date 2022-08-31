package it.bz.opendatahub.webcomponents.dataservice.application.adapter.in.web;

import it.bz.opendatahub.webcomponents.common.stereotype.WebAdapter;
import it.bz.opendatahub.webcomponents.dataservice.application.port.in.SendEmailUseCase;
import it.bz.opendatahub.webcomponents.dataservice.config.MailerConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@WebAdapter
@RestController
@RequestMapping("/contact")
public class ContactFormController {

	Logger logger = LoggerFactory.getLogger(ContactFormController.class);

	@Value("${hCaptcha.secret.key}")
	private String hCaptchaSecretKey;

	private final SendEmailUseCase sendEmailUseCase;
	private final MailerConfig mailerConfig;

	private HttpClient httpClient;

	public ContactFormController(SendEmailUseCase sendEmailUseCase, MailerConfig mailerConfig) {
		this.sendEmailUseCase = sendEmailUseCase;
		this.mailerConfig = mailerConfig;
		this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
				.build();
	}

	@SneakyThrows
	@PostMapping
	public void sendContactForm(@RequestBody @Valid ContactFormRequest request) {
		logger.info("Contact request received. Validating hCaptcha...");
		String hCaptchaToken = request.getHCaptchaToken();
		if (hCpatchaTokenValidation(hCaptchaToken)) {
			val requestAsText = "Category: " + nullToEmpty(request.getCategory()) + "\n" +
					"First name: " + nullToEmpty(request.getNameFirst()) + "\n" +
					"Last name: " + nullToEmpty(request.getNameLast()) + "\n" +
					"E-mail: " + nullToEmpty(request.getEmail()) + "\n" +
					"Phone: " + nullToEmpty(request.getPhone()) + "\n" +
					"Text:\n" + nullToEmpty(request.getText());

			logger.info("hCaptcha validated: contact request successful");
			sendEmailUseCase.sendPlaintextEmail(mailerConfig.getTo(), mailerConfig.getSubject(), requestAsText);
		} else
			logger.info("hCaptcha validation failed: contact request not successful");

	}

	private String nullToEmpty(String in) {
		if (in == null) {
			return "";
		}

		return in;
	}

	private boolean hCpatchaTokenValidation(String hCaptchaToken) throws IOException, InterruptedException {
		StringBuilder sb = new StringBuilder();
		sb.append("response=");
		sb.append(hCaptchaToken);
		sb.append("&secret=");
		sb.append(this.hCaptchaSecretKey);

		HttpRequest requestToken = HttpRequest.newBuilder()
				.uri(URI.create("https://hcaptcha.com/siteverify"))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.timeout(Duration.ofSeconds(10))
				.POST(BodyPublishers.ofString(sb.toString())).build();

		HttpResponse<String> response = this.httpClient.send(requestToken,
				BodyHandlers.ofString());

		// Simple hack to see if valid. Check here for more info
		// https://golb.hplar.ch/2020/05/hcaptcha.html
		return response.body().contains("\"success\":true\"");
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

		@NotBlank
		private String hCaptchaToken;
	}
}
