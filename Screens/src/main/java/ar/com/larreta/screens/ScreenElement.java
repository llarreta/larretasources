package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.domain.UserState;

@Entity
@Table(name = "screenElement")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ScreenElement extends ar.com.larreta.commons.domain.Entity {

	private String styleClass;
	private String tooltip;
	private String watermark;
	private ScreenElement parent;
	
	@Transient
	public String getIdValue(){
		return getSimpleName() + getId();
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=UserState.class)
	@JoinColumn (name="idParent")
	public ScreenElement getParent() {
		return parent;
	}

	public void setParent(ScreenElement parent) {
		this.parent = parent;
	}

	@Transient
	public Boolean getIsWatermark(){
		return StringUtils.isEmpty(watermark);
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
		return StringUtils.isEmpty(tooltip);
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
