package co.com.directv.sdii.model.dto;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * DTO para empaquetar la informacion para los elementos del ajuste.
 * 
 * Fecha de Creación: 23/03/2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
//Modificado para Requerimiento: CC057
public class AdjustmenElementsResponseDTO {

	private Long adjustmentElementId;	
    private boolean isAdjust;
    private boolean isSerialized;
    private Double actualQuantity;
	private String authorizedUser;
	private Date authorizationDate;    
    private String elementModel;
    private String elementClass;
    private String serialCode;
    private String serialLinked;
    private String statusName;
    private String typeElement;
    private String typeElementLynked;
    private String nameWarehouseSource;
    private String nameWarehouseTarget;
    private String typeElementCode;
    private String typeElementName;
    
    
    
	public String getTypeElementCode() {
		return typeElementCode;
	}

	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}

	public String getTypeElementName() {
		return typeElementName;
	}

	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}

	public void setAdjust(boolean isAdjust) {
		this.isAdjust = isAdjust;
	}
	
	public boolean isAdjust() {
		return isAdjust;
	}
	
	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}

	public boolean isSerialized() {
		return isSerialized;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialLinked(String serialLinked) {
		this.serialLinked = serialLinked;
	}

	public String getSerialLinked() {
		return serialLinked;
	}

	public void setTypeElement(String typeElement) {
		this.typeElement = typeElement;
	}

	public String getTypeElement() {
		return typeElement;
	}

	public void setElementModel(String elementModel) {
		this.elementModel = elementModel;
	}

	public String getElementModel() {
		return elementModel;
	}

	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}

	public String getElementClass() {
		return elementClass;
	}

	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public Double getActualQuantity() {
		return actualQuantity;
	}

	public void setTypeElementLynked(String typeElementLynked) {
		this.typeElementLynked = typeElementLynked;
	}

	public String getTypeElementLynked() {
		return typeElementLynked;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setAuthorizedUser(String authorizedUser) {
		this.authorizedUser = authorizedUser;
	}

	public String getAuthorizedUser() {
		return authorizedUser;
	}

	public void setAuthorizationDate(Date authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAdjustmentElementId(Long adjustmentElementId) {
		this.adjustmentElementId = adjustmentElementId;
	}

	public Long getAdjustmentElementId() {
		return adjustmentElementId;
	}

	public String getNameWarehouseSource() {
		return nameWarehouseSource;
	}

	public void setNameWarehouseSource(String nameWarehouseSource) {
		this.nameWarehouseSource = nameWarehouseSource;
	}

	public String getNameWarehouseTarget() {
		return nameWarehouseTarget;
	}

	public void setNameWarehouseTarget(String nameWarehouseTarget) {
		this.nameWarehouseTarget = nameWarehouseTarget;
	}
    
}
