package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "containedElement")
public class ContainedElement extends ar.com.larreta.commons.domain.Entity {

	private Integer order = 0;
	private ScreenElement container;
	private ScreenElement element;

	public ContainedElement(){}
	
	public ContainedElement(Integer order, ScreenElement container, ScreenElement element){
		this.order = order;
		this.container = container;
		this.element = element;
	}
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=ScreenElement.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idContainer")
	public ScreenElement getContainer() {
		return container;
	}

	public void setContainer(ScreenElement container) {
		this.container = container;
	}

	@ManyToOne (fetch=FetchType.EAGER, targetEntity=ScreenElement.class, cascade=CascadeType.ALL)
	@JoinColumn (name="idElement")
	public ScreenElement getElement() {
		return element;
	}

	public void setElement(ScreenElement element) {
		this.element = element;
	}

	@Basic @Column(name="orderIndex")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		if (order!=null){
			this.order = order;
		}
	}
	
	
}
