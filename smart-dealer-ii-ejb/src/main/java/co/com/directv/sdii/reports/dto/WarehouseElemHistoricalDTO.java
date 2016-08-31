package co.com.directv.sdii.reports.dto;

import java.util.Date;

public class WarehouseElemHistoricalDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -287938234836970431L;

	private Long historicalCode;
	private String elementModelName;
	private String serialCode;
	private Date exitDate;
	private String importLogPurchaseOrder;
	private String movementTypeName;
	private Long importLogId;
	
	public Long getHistoricalCode() {
		return historicalCode;
	}
	public void setHistoricalCode(Long historicalCode) {
		this.historicalCode = historicalCode;
	}
	public String getElementModelName() {
		return elementModelName;
	}
	public void setElementModelName(String elementModelName) {
		this.elementModelName = elementModelName;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public Date getExitDate() {
		return exitDate;
	}
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}
	public String getImportLogPurchaseOrder() {
		return importLogPurchaseOrder;
	}
	public void setImportLogPurchaseOrder(String importLogPurchaseOrder) {
		this.importLogPurchaseOrder = importLogPurchaseOrder;
	}
	public String getMovementTypeName() {
		return movementTypeName;
	}
	public void setMovementTypeName(String movementTypeName) {
		this.movementTypeName = movementTypeName;
	}
	public Long getImportLogId() {
		return importLogId;
	}
	public void setImportLogId(Long importLogId) {
		this.importLogId = importLogId;
	}
	
}
