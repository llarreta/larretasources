package co.com.directv.sdii.model.pojo;


public class ShippingOrderIbsElementModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3014567718299181906L;
	
	private Long id;
	private String modelCode;
	private String modelName;
	private Country country;
	
	public ShippingOrderIbsElementModel() {
		super();
	}
	
	public ShippingOrderIbsElementModel(Long id, String modelCode,
			String modelName, Country country) {
		super();
		this.id = id;
		this.modelCode = modelCode;
		this.modelName = modelName;
		this.country = country;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}