package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "screenElement")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ScreenElement extends ar.com.larreta.commons.domain.Entity {

	private Integer order = 0;
	private String styleClass;
	private String tooltip;
	private String watermark;
	private ScreenElement parent;

	@Basic @Column(name="orderIndex")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		if (order!=null){
			this.order = order;
		}
	}

	
	@Transient
	public String getIdValue(){
		return getSimpleName() + getId();
	}
	
	@ManyToOne (fetch=FetchType.EAGER, targetEntity=ScreenElement.class)
	@JoinColumn (name="idParent")
	//@Transient
	public ScreenElement getParent() {
		return parent;
	}

	public void setParent(ScreenElement parent) {
		this.parent = parent;
	}

	@Transient
	public Boolean getIsWatermark(){
		return !StringUtils.isEmpty(watermark);
	}
	
	@Basic
	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	@Transient
	public Boolean getIsTooltip(){
		return !StringUtils.isEmpty(tooltip);
	}
	
	@Basic
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Transient
	public String getSimpleName(){
		return getClass().getSimpleName();
	}

	@Basic
	public String getStyleClass() {
		return styleClass;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
}
