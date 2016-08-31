package co.com.directv.sdii.reports.dto;

//Modificado para Requerimiento: CC057
public class SerializedElementImportLogDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1300370510673117471L;
	
	private String elementType;
	
	private String modelName;
	
	private String className;
	
	private String brandName;
	
	private String measureUnitName;
	
	private String serialCode;
	
	private String rid;
	
	private String elementTypeLinked;
	
	private String serialLinked;
	
	private String elementTypeCode;
	
	

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMeasureUnitName() {
		return measureUnitName;
	}

	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getElementTypeLinked() {
		return elementTypeLinked;
	}

	public void setElementTypeLinked(String elementTypeLinked) {
		this.elementTypeLinked = elementTypeLinked;
	}

	public String getSerialLinked() {
		return serialLinked;
	}

	public void setSerialLinked(String serialLinked) {
		this.serialLinked = serialLinked;
	}
	
	
	
}
