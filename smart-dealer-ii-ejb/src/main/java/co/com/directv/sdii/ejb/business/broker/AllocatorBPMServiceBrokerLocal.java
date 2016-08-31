/**
 * Creado 21/08/2010 17:15:23
 */
package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Define las operaciones para el la invocación de los procesos de negocio
 * de asignación
 * 
 * Fecha de Creación: 21/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AllocatorBPMServiceBrokerLocal {

	/**
	 * Metodo: Inicia el proceso de negocio de asignación de una work order
	 * @param dealers lista con la información de los dealers
	 * @param workorder información de la work order
	 * @return Información del dealer que ha sido seleccionado para la prestación de los servicios de
	 * la work order
	 * @throws BusinessException En caso de error al realizar la selección del dealer
	 * @author jjimenezh
	 */
	public DealerVO startWorkorderAllocation(List<DealerVO> dealers, WorkOrderVO workorder)throws BusinessException;
	
}
