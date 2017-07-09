package ar.com.larreta.school.persistence;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.persistence.model.ParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "product")
@Where(clause="deleted IS NULL")
@Inheritance(strategy=InheritanceType.JOINED)
@SQLDelete (sql="UPDATE Product SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Product extends ParametricEntity{
	
	private ProductGroup productGroup;
	private Set<PaymentUnit> paymentUnits;

	@OneToMany (mappedBy="product", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=PaymentUnit.class)
	@Where(clause="deleted IS NULL")
	public Set<PaymentUnit> getPaymentUnits() {
		return paymentUnits;
	}

	public void setPaymentUnits(Set<PaymentUnit> paymentUnits) {
		this.paymentUnits = paymentUnits;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ProductGroup.class)
	@JoinColumn (name="idProductGroup")
	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = (ProductGroup) productGroup;
	}

	
}
