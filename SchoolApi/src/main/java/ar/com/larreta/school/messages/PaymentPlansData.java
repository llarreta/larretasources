package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class PaymentPlansData extends JSONable {

	private Long id;
	
	private String 			description;
	
	private JSONableCollection<ObligationData> obligations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JSONableCollection<ObligationData> getObligations() {
		return obligations;
	}

	public void setObligations(JSONableCollection<ObligationData> obligations) {
		this.obligations = obligations;
	}
}
