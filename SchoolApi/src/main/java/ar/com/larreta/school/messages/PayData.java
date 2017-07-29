package ar.com.larreta.school.messages;

import javax.validation.Valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Person;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.validators.annotations.Exist;
import ar.com.larreta.stepper.validators.annotations.Format;
import ar.com.larreta.stepper.validators.annotations.NotNull;
import ar.com.larreta.stepper.validators.annotations.Size;
import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class PayData extends JSONable {

	@NotNull(message="value.required")
	private Double value;

	@NotNull(message="pay.personWhoPays.required")
	@Exist(message="personWhoPays.inexistent", entityType=Person.class)
	private Long personWhoPays;

	@Valid
	@NotNull(message="pay.payUnits.required")
	@Size(mayorOrEqual=1, message="pay.payUnits.size")
	private JSONableCollection<PayUnitData> paymentUnits;

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

	public JSONableCollection<PayUnitData> getPaymentUnits() {
		return paymentUnits;
	}

	public void setPaymentUnits(JSONableCollection<PayUnitData> paymentUnits) {
		this.paymentUnits = paymentUnits;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
