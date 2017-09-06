package ar.com.larreta.stepper.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.TelephoneType;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class PersonTelephoneRelationshipData extends JSONable {
	@Exist(message="telephoneType.inexistent", entityType=TelephoneType.class)
	@NotNull(message="telephoneType.required")
	private Long 		telephoneType;

	@NotNull(message="telephone.required")
	@Valid
	private TelephoneData telephone;

	public Long getTelephoneType() {
		return telephoneType;
	}

	public void setTelephoneType(Long telephoneType) {
		this.telephoneType = telephoneType;
	}

	public TelephoneData getTelephone() {
		return telephone;
	}

	public void setTelephone(TelephoneData telephone) {
		this.telephone = telephone;
	}
}
