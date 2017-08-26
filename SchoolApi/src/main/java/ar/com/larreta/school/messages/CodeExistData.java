package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class CodeExistData extends Body {
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
