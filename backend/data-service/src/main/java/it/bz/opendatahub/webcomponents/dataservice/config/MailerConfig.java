package it.bz.opendatahub.webcomponents.dataservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties("application.mailer")
@Configuration
@Getter
@Setter
public class MailerConfig {
	@NotNull
	private String to;

	@NotNull
	private String from;

	@NotNull
	private String subject;

    @NotNull
    private String smtpHost;

    @NotNull
    private Integer smtpPort;

    @NotNull
    private String smtpUser;

    @NotNull
    private String smtpPassword;

    @NotNull
    private Boolean debug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        val mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);

        mailSender.setUsername(smtpUser);
        mailSender.setPassword(smtpPassword);

        val props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.debug", debug);

        return mailSender;
    }
}
