package co.com.directv.sdii.ejb.business.broker;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.IBSSerElemMovementDTO;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;

@Local
public interface ResourceProvisioningBrokerLocal {
	
	/***
	 * Este metodo es el encargado de notificar el cambio de estado de un recurso que ha cambiado en HSP+ a IBS.
	 * @param movCmdQueueVO dado que HSP+ maneja la tabla MOV_CMD_QUEUE para guardar los eventos de inventarios que deben ser reportados a IBS
	 * 		este parametro tiene la informacion del registro que se informara a IBS
	 * @return Este retorno indica si se logro invocar la operacion con exito
	 * @throws BusinessException posibles excepciones de invocacion del servicio son encapsuladas en una excepcion de negocio de HSP+
	 * @author Aharker
	 */
	public boolean updateResourceStatus(MovCmdQueueVO movCmdQueueVO) throws BusinessException ;
	
}
