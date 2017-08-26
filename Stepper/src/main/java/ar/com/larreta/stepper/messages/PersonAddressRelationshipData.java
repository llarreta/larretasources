package ar.com.larreta.stepper.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.AddressType;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class PersonAddressRelationshipData extends JSONable {

	@Exist(message="addressType.inexistent", entityType=AddressType.class)
	@NotNull(message="addressType.required")
	private Long 		addressType;

	@NotNull(message="address.required")
	@Valid
	private AddressData address;
	
	public AddressData getAddress() {
		return address;
	}
	public void setAddress(AddressData address) {
		this.address = address;
	}
	public Long getAddressType() {
		return addressType;
	}
	public void setAddressType(Long addressType) {
		this.addressType = addressType;
	}
}
