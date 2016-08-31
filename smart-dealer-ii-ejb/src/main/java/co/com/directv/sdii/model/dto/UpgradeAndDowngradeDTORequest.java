package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * DTO encargado de enviar la informacion necesaria para obtener los servicios de upgrade y downgrade de un cliente 
 * 
 * Fecha de Creación: 22/6/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class UpgradeAndDowngradeDTORequest extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -856553695222916449L;
	
	private Date createDateTime;
	
	private String customerKey;

	public UpgradeAndDowngradeDTORequest() {
		super();
	}

	public UpgradeAndDowngradeDTORequest(Date createDateTime, String customerKey) {
		super();
		this.createDateTime = createDateTime;
		this.customerKey = customerKey;
	}
	
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
