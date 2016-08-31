package co.com.directv.sdii.reports.dto;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * Dto para retornar información del invnetario agrupado por tipo de elementos
 * @author waguilera
 *
 */
public class InventoryElementGroupDTO implements java.io.Serializable{

	
	private static final long serialVersionUID = 1300370510673117471L;
	
	private String elementcode;
	
	private String elementName;
	
	private String warehouseTypeName;
	
	private String dealerName;
	
	private String warehouseName;
	
	private Double quantity;
	
	private String isSer;
	
	private Date movementDate;
	
	public String getElementcode() {
		return elementcode;
	}
	public void setElementcode(String elementcode) {
		this.elementcode = elementcode;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getWarehouseTypeName() {
		return warehouseTypeName;
	}
	public void setWarehouseTypeName(String warehouseTypeName) {
		this.warehouseTypeName = warehouseTypeName;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public String getIsSer() {
		return isSer;
	}
	public void setIsSer(String isSer) {
		this.isSer = isSer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getMovementDate() {
		return movementDate;
	}
	
	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

}
