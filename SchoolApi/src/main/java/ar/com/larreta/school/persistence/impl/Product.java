package ar.com.larreta.school.persistence.impl;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.persistence.model.ParametricEntity;

@Entity
@Table(name = "product")
@Where(clause="deleted IS NULL")
@Inheritance(strategy=InheritanceType.JOINED)
@SQLDelete (sql="UPDATE Product SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Product extends ParametricEntity{
	
	private ProductGroup productGroup;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ProductGroup.class)
	@JoinColumn (name="idProductGroup")
	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = (ProductGroup) productGroup;
	}

	
}
