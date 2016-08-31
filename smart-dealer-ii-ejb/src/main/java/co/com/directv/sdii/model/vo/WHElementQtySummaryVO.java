/**
 * Creado 20/08/2010 14:30:36
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * Encapsula la información resumida de las cantidades en bodega de un elemento 
 * 
 * Fecha de Creación: 20/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WHElementQtySummaryVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6205753102763919777L;

	private Long elementTypeId;
	
	private String elementTypeCode;
	
	private String elementTypeName;
	
	private Long warehouseId;
	
	private String warehouseCode;
	
	private Long dealerId;
	
	private String dealerCode;
	
	private String dealerName;
	
	private Long dealerCountryId;
	
	private String dealerCountryCode;
	
	private Long branchId;
	
	private String branchCode;
	
	private String branchName;
	
	private Long branchCountryId;
	
	private String branchCountryCode;
	
	private String measureUnitCode;
	
	private String measureUnitName;
	
	private Double initialQuantity = 0D;
	
	private Double entriesQuantity = 0D;
	
	private Double outputsQuantity = 0D;
	
	private Double actualQuantity = 0D;

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public String getElementTypeName() {
		return elementTypeName;
	}

	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Long getDealerCountryId() {
		return dealerCountryId;
	}

	public void setDealerCountryId(Long dealerCountryId) {
		this.dealerCountryId = dealerCountryId;
	}

	public String getDealerCountryCode() {
		return dealerCountryCode;
	}

	public void setDealerCountryCode(String dealerCountryCode) {
		this.dealerCountryCode = dealerCountryCode;
	}

	public String getMeasureUnitCode() {
		return measureUnitCode;
	}

	public void setMeasureUnitCode(String measureUnitCode) {
		this.measureUnitCode = measureUnitCode;
	}

	public String getMeasureUnitName() {
		return measureUnitName;
	}

	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}

	public Double getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(Double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public Double getEntriesQuantity() {
		return entriesQuantity;
	}

	public void setEntriesQuantity(Double entriesQuantity) {
		this.entriesQuantity = entriesQuantity;
	}

	public Double getOutputsQuantity() {
		return outputsQuantity;
	}

	public void setOutputsQuantity(Double outputsQuantity) {
		this.outputsQuantity = outputsQuantity;
	}

	public Double getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getBranchCountryId() {
		return branchCountryId;
	}

	public void setBranchCountryId(Long branchCountryId) {
		this.branchCountryId = branchCountryId;
	}

	public String getBranchCountryCode() {
		return branchCountryCode;
	}

	public void setBranchCountryCode(String branchCountryCode) {
		this.branchCountryCode = branchCountryCode;
	}
}
