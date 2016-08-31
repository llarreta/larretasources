/**
 * Creado 08/08/2011 15:07:17
 */
package co.com.directv.sdii.assign.assignment.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Encapsula la información de los resultados de evaluación del proceso
 * de asignación
 * 
 * Fecha de Creación: 08/08/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignmentResultDTO implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2347008017656543307L;

	private List<DealerVO> selectedDealers;
	
	private String traceAssignmentInformation;

	public List<DealerVO> getSelectedDealers() {
		return selectedDealers;
	}

	public void setSelectedDealers(List<DealerVO> selectedDealers) {
		this.selectedDealers = selectedDealers;
	}

	public String getTraceAssignmentInformation() {
		return traceAssignmentInformation;
	}

	public void setTraceAssignmentInformation(String traceAssignmentInformation) {
		this.traceAssignmentInformation = traceAssignmentInformation;
	}
	
}
