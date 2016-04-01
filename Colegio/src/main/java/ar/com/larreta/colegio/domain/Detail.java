package ar.com.larreta.colegio.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "detail")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Detail SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Detail extends ar.com.larreta.commons.domain.Entity{

	private String description;
	private Double value;
	private Set<LittleDetail> littleDetails;
	private Price price;

	@Basic @Column (name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Basic @Column (name="value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	@OneToMany (mappedBy="detail", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=LittleDetail.class)
	@Where(clause="deleted IS NULL")
	public Set<LittleDetail> getLittleDetails() {
		return littleDetails;
	}
	public void setLittleDetails(Set<LittleDetail> littleDetails) {
		this.littleDetails = littleDetails;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Price.class)
	@JoinColumn (name="idPrice")
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}	

}
