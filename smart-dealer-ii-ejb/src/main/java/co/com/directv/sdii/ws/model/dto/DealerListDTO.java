/**
 * Creado 30/08/2010 20:22:36
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.DealerVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 30/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class DealerListDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210899200122254066L;
	
	
	public DealerListDTO() {
		super();
	}

	public DealerListDTO(List<DealerVO> dealer) {
		super();
		this.dealer = dealer;
	}

	private List<DealerVO> dealer;

	public List<DealerVO> getDealer() {
		return dealer;
	}

	public void setDealer(List<DealerVO> dealer) {
		this.dealer = dealer;
	}
	
}
