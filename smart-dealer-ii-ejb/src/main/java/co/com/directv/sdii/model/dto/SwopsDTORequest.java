package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * DTO encargado de enviar la informacion necesaria para obtener los SWOPS de un cliente 
 * 
 * Fecha de Creación: 14/02/2012
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class SwopsDTORequest extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -856553695222916449L;
	
	private Date createDateTime;
	
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

}
