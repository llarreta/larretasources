package ar.com.larreta.stepper.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class RelatedBody extends Body {

	public Long idRelated;

	public Long getIdRelated() {
		return idRelated;
	}

	public void setIdRelated(Long idRelated) {
		this.idRelated = idRelated;
	}
	
	
	
}
