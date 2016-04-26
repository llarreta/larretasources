package ar.com.larreta.screens;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;

public abstract class ScreenElement extends AppObjectImpl {

	private String id;
	private String styleClass;
	private String tooltip;
	private String watermark;
	
	//FIXME: Mejorar
	private static Integer index = 1;

	public Boolean getIsWatermark(){
		return StringUtils.isEmpty(watermark);
	}
	
	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	public Boolean getIsTooltip(){
		return StringUtils.isEmpty(tooltip);
	}
	
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getId() {
		//FIXME: Mejorar
		if (id==null){
			id = (index++).toString();
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSimpleName(){
		return getClass().getSimpleName();
	}

	public String getStyleClass() {
		return styleClass;
	}
	
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
}
