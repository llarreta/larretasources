package co.com.directv.sdii.reports.dto;

//Modificado para Requerimiento: CC057
public class WarehouseElemStockDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6478339591690528219L;
	
	private Long principalDealerCode;
	private Long branchDealerCode;
	private String whCode;
	private Long elementId;
	private String typeElementName;
	private Double minQuantity;
	private Double maxQuantity;
	private Double reorderQuantity;
	private String comments;
	private String principalDealer;
	private String branchDealer;
	private String location;
	private String typeElementCode;
	
	public String getTypeElementCode() {
		return typeElementCode;
	}
	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}
	public Long getPrincipalDealerCode() {
		return principalDealerCode;
	}
	public void setPrincipalDealerCode(Long principalDealerCode) {
		this.principalDealerCode = principalDealerCode;
	}
	public Long getBranchDealerCode() {
		return branchDealerCode;
	}
	public void setBranchDealerCode(Long branchDealerCode) {
		this.branchDealerCode = branchDealerCode;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public Long getElementId() {
		return elementId;
	}
	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}
	public String getTypeElementName() {
		return typeElementName;
	}
	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}
	public Double getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(Double minQuantity) {
		this.minQuantity = minQuantity;
	}
	public Double getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(Double maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public Double getReorderQuantity() {
		return reorderQuantity;
	}
	public void setReorderQuantity(Double reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setPrincipalDealer(String principalDealer) {
		this.principalDealer = principalDealer;
	}
	public String getPrincipalDealer() {
		return principalDealer;
	}
	public void setBranchDealer(String branchDealer) {
		this.branchDealer = branchDealer;
	}
	public String getBranchDealer() {
		return branchDealer;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}

}
