package ar.com.larreta.school.messages;

import javax.validation.Valid;

import ar.com.larreta.persistence.model.Person;
import ar.com.larreta.rest.messages.JSONable;
import ar.com.larreta.rest.messages.JSONableCollection;
import ar.com.larreta.validators.annotations.Exist;
import ar.com.larreta.validators.annotations.Format;
import ar.com.larreta.validators.annotations.NotNull;
import ar.com.larreta.validators.annotations.Size;

public class PayData extends JSONable {

	@NotNull(message="value.required")
	private Double value;

	@NotNull(message="pay.personWhoPays.required")
	@Exist(message="personWhoPays.inexistent", entityType=Person.class)
	private Long personWhoPays;

	@Valid
	@NotNull(message="pay.payUnits.required")
	@Size(mayorOrEqual=1, message="pay.payUnits.size")
	private JSONableCollection<PayUnitData> payUnits;

	@NotNull(message="pay.paymentDate.required")
	@Format(formatType=Format.FormatType.DATE, message="pay.paymentDate.invalid")
	private String paymentDate;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getPersonWhoPays() {
		return personWhoPays;
	}

	public void setPersonWhoPays(Long personWhoPays) {
		this.personWhoPays = personWhoPays;
	}

	public JSONableCollection<PayUnitData> getPayUnits() {
		return payUnits;
	}

	public void setPayUnits(JSONableCollection<PayUnitData> payUnits) {
		this.payUnits = payUnits;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
