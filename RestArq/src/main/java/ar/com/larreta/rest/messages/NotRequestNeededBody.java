package ar.com.larreta.rest.messages;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(NotRequestNeededBody.COMPONENT_NAME) @Scope("prototype")
public class NotRequestNeededBody extends Body {
	
	public static final String COMPONENT_NAME = "notRequestNeededBody";
	
	@Autowired
	private MessageSource messageSource;
	
	private String message;

	@PostConstruct
	public void initialize(){
		message = messageSource.getMessage("not.request.needed", null, "not.request.needed", Locale.ROOT);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
