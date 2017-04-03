package ar.com.larreta.school.persistence.impl;

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

import ar.com.larreta.persistence.model.impl.PersistenceEntityImpl;

@Entity
@Table(name = "littleDetail")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE LittleDetail SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class LittleDetail extends PersistenceEntityImpl {

	private Double value;
	private Detail detail;
	
	@Basic @Column (name="littleDetailValue")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Detail.class)
	@JoinColumn (name="idDetail")
	public Detail getDetail() {
		return detail;
	}
	public void setDetail(Detail detail) {
		this.detail = detail;
	}
	

}
