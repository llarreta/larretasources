package ar.com.larreta.colegio.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "product")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Product SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Product extends ar.com.larreta.commons.domain.Entity{
	
	private String name;
	private ProductGroup productGroup;
	
	@Basic @Column (name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=ProductGroup.class)
	@JoinColumn (name="idGroupProduct")
	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	
}
