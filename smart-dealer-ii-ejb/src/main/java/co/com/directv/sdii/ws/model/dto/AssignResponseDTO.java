/**
 * Creado 14/06/2011 09:36:43
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Clase que encapsula la respuesta del WS de asignación
 * 
 * Fecha de Creación: 14/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439934044664556665L;
	
	private DealerVO dealer;
	
	private boolean haveTrace;

	public boolean isHaveTrace() {
		return haveTrace;
	}

	public void setHaveTrace(boolean haveTrace) {
		this.haveTrace = haveTrace;
	}

	private String traceAssignmentInformation;

	public DealerVO getDealer() {
		return dealer;
	}

	public void setDealer(DealerVO dealer) {
		this.dealer = dealer;
	}

	public String getTraceAssignmentInformation() {
		return traceAssignmentInformation;
	}

	public void setTraceAssignmentInformation(String traceAssignmentInformation) {
		this.traceAssignmentInformation = traceAssignmentInformation;
	}
}
