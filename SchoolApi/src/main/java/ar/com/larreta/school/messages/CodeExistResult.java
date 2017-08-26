package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class CodeExistResult extends Body {
	private Boolean exist;

	public Boolean getExist() {
		return exist;
	}

	public void setExist(Boolean exist) {
		this.exist = exist;
	}
	
}
