/**
 * Creado 10/09/2010 11:42:14
 */
package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Define las operaciones para la invocación del servicio web que inicia el proceso de
 * atención de la work order
 * 
 * Fecha de Creación: 10/09/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AttentionBPMServiceBrokerLocal {

	
	/**
	 * Metodo: Atiende una work order invocando el proceso de negocio
	 * @param workorderVo work order
	 * @param workorderServices lista con los servicios de la work order
	 * @param comments
	 * @param workOrderFinished
	 * @throws BusinessException 
	 * @author jjimenezh
	 */
	public void attendWorkOrder(WorkOrderVO workorderVo, List<WorkOrderServiceVO> workorderServices, String comments, Boolean workOrderFinished) throws BusinessException;
	
}
