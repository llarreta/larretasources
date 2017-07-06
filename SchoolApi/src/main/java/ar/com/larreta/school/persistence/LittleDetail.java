package ar.com.larreta.school.persistence;

import java.io.Serializable;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Entity @Component @Scope(Const.PROTOTYPE)
@Table(name = "littleDetail")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE LittleDetail SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class LittleDetail extends ar.com.larreta.persistence.model.Entity {

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
	public void setDetail(Serializable detail) {
		this.detail = (Detail) detail;
	}
	

}
