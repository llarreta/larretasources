package ar.com.larreta.colegio.domain;

import java.util.Date;
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
@Table(name = "price")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Price SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Price extends ar.com.larreta.commons.domain.Entity {

	private Date validityStartDate;
	private Double value;
	private Set<Detail> details;
	private Obligation obligation;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Obligation.class)
	@JoinColumn (name="idObligation")
	public Obligation getObligation() {
		return obligation;
	}
	public void setObligation(Obligation obligation) {
		this.obligation = obligation;
	}
	@Basic @Column (name="validityStartDate")
	public Date getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	
	@Basic @Column (name="priceValue")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@OneToMany (mappedBy="price", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Detail.class)
	@Where(clause="deleted IS NULL")
	public Set<Detail> getDetails() {
		return details;
	}
	public void setDetails(Set<Detail> details) {
		this.details = details;
	}
}
