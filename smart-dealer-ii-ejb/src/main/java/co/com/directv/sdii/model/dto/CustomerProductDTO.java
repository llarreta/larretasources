package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * Fecha de Creación: 29/06/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 2.0
 * @see
 */
public class CustomerProductDTO implements Serializable{

	private String productName;
	private String productState;
	
	/**
	 * atributo encargado de tomar el serial principal que viene del servicio de vista360 en ESB
	 * @author aharker
	 */
	private String provisionedDevicesRelated;
	/**
	 * atributo encargado de tomar el serial vinculado que viene del servicio de vista360 en ESB
	 * @author aharker
	 */
	private String provisionedDevices;
	
	/**
	 * atributo encargado de tomar el id de estado del producto del cliente que viene del servicio de vista360 en ESB
	 * @author aharker
	 */
	private String idProductStatus;

	public String getIdProductStatus() {
		return idProductStatus;
	}

	public void setIdProductStatus(String idProductStatus) {
		this.idProductStatus = idProductStatus;
	}

	public String getProvisionedDevicesRelated() {
		return provisionedDevicesRelated;
	}

	public void setProvisionedDevicesRelated(String provisionedDevicesRelated) {
		this.provisionedDevicesRelated = provisionedDevicesRelated;
	}

	public String getProvisionedDevices() {
		return provisionedDevices;
	}

	public void setProvisionedDevices(String provisionedDevices) {
		this.provisionedDevices = provisionedDevices;
	}

	private static final long serialVersionUID = 6287143018842312729L;

	public CustomerProductDTO() {
		super();
	}

	public CustomerProductDTO(String productName, String productState ) {
		super();
		this.productName = productName;
		this.productState = productState;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public CustomerProductDTO(String productName, String productState,
			String provisionedDevicesRelated, String provisionedDevices, String idProductStatus) {
		super();
		this.productName = productName;
		this.productState = productState;
		this.provisionedDevicesRelated = provisionedDevicesRelated;
		this.provisionedDevices = provisionedDevices;
		this.idProductStatus=idProductStatus;
	}
	
	
}
