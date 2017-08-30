package ar.com.larreta.school.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.ExtendedParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "discount")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Discount SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Discount extends ExtendedParametricEntity {

	private PaymentPlan paymentPlan;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=PaymentPlan.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idPaymentPlan")
	public PaymentPlan getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(PaymentPlan paymentPlan) {
		this.paymentPlan = paymentPlan;
	}
	
}
