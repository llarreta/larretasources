package ar.com.larreta.stepper.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.EmailType;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class PersonEmailRelationshipData extends JSONable {

	@Exist(message="emailType.inexistent", entityType=EmailType.class)
	@NotNull(message="emailType.required")
	private Long 		emailType;

	@NotNull(message="email.required")
	@Valid
	private EmailData email;

	public Long getEmailType() {
		return emailType;
	}

	public void setEmailType(Long emailType) {
		this.emailType = emailType;
	}

	public EmailData getEmail() {
		return email;
	}

	public void setEmail(EmailData email) {
		this.email = email;
	}
	
	
}
