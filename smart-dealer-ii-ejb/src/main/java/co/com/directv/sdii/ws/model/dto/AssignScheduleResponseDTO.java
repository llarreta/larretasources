/**
 * Creado 10/06/2011 16:28:03
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import co.com.directv.sdii.assign.schedule.dto.WorkLoad;

/**
 * Encapsula la respuesta de la consulta de la agenda disponible para un conjunto de fechas
 * 
 * Fecha de Creación: 10/06/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class AssignScheduleResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3055879169385941236L;
	private WorkLoad workLoad;

	public WorkLoad getWorkLoad() {
		return workLoad;
	}

	public void setWorkLoad(WorkLoad workLoad) {
		this.workLoad = workLoad;
	}
	
	
	
	
}
