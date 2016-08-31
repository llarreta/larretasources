/**
 * Creado 11/02/2016
 */
package co.com.directv.sdii.ejb.business.core.tray;

import javax.ejb.Local;
import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Interfaz que define métodos helper para el proceso de atenciones de una WorkOrder.
 * 
 * @author Jorge Ariel Silva
 * @version 1.0
 * 
 */
@Local
public interface ReportHelperBusinessBeanLocal {

	
	/**
	 * Metodo: Persiste el error generado en el 
	 * proceso de Atención y/o Finalización
	 * @param workOrderId
	 * @param woAttentionStatusCode
	 * @param messageCode
	 * @param message
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void reportWoAttentionStatus(Long workOrderId, String woAttentionStatusCode, String messageCode, String message, Long ... userId) throws BusinessException;
	
}
