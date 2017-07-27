package ar.com.larreta.school.persistence;

import java.util.Iterator;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.ParametricEntity;
import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "detail")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Detail SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Detail extends ParametricEntity{

	private Double value;
	private Set<LittleDetail> littleDetails;
	private Price price;

	@Basic @Column (name="detailValue")
	public Double getValue() {
		if (littleDetails!=null && littleDetails.size()>0){
			Iterator<LittleDetail> it = littleDetails.iterator();
			value = new Double(0);
			while (it.hasNext()) {
				LittleDetail littleDetail = (LittleDetail) it.next();
				value += littleDetail.getValue();
			}
		}
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
		writeToAll(littleDetails, "detail", this);
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
