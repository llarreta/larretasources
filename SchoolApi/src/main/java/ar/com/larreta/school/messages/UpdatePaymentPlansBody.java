package ar.com.larreta.school.messages;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.tools.Const;
import ar.com.larreta.validators.annotations.NotNull;

@Component @Scope(Const.PROTOTYPE)
public class UpdatePaymentPlansBody extends Body {

	@NotNull(message="id.required", avaiableActions={"update"})
	private Long id;
	
	@NotNull(message="description.required") @Size(min=5, message="description.min.length")
	private String 			description;
	
	@Valid
	@NotNull(message="obligations.required")
	@ar.com.larreta.validators.annotations.Size(message="obligations.mayorOrEqual", mayorOrEqual=1)
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
