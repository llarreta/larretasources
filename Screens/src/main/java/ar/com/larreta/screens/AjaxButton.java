package ar.com.larreta.screens;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "button")
@DiscriminatorValue(value = "ajaxButton")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class AjaxButton extends Button {

	private String actionListener;
	private String update;
	private Set<Attribute> attributes;

	public AjaxButton() {
		super();
		ajax = Boolean.TRUE;
	}
	
	@OneToMany (mappedBy="button", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=Attribute.class)
	@Where(clause="deleted IS NULL")
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Basic @Column(name="updateValue")
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	@Basic
	public String getActionListener() {
		return actionListener;
	}

	public void setActionListener(String actionListener) {
		this.actionListener = actionListener;
	}

	@Transient
	@Override
	public String getActionEvaluated() {
		return StringUtils.EMPTY;
	}
	
	@Transient
	@Override
	public Long getNextScreenId(){
		return null;
	}
	
}
