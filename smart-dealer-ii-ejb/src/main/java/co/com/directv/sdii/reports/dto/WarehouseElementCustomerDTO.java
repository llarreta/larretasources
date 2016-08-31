package co.com.directv.sdii.reports.dto;


public class WarehouseElementCustomerDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319436798176021359L;

	private String wareHouseName;
	
	private String modelName;
	
	private String elementTypeName;
	
	private String serial;
	
	private String rid;

	private String serialLinked;
	
	private Double quantity;

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getElementTypeName() {
		return elementTypeName;
	}

	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getSerialLinked() {
		return serialLinked;
	}

	public void setSerialLinked(String serialLinked) {
		this.serialLinked = serialLinked;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	
}
