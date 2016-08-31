/**
 * 
 */
package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;
import co.com.directv.sdii.model.dto.IBSSerElemMovementDTO;


/**
 * Interface que define las operaciones de los web services
 * que exponen los servicios de Inventarios de IBS.
 * 
 * Fecha de Creación: 11/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.impl.IbsInventoryServiceBrokerImpl
 */
@Local
public interface IbsInventoryServiceBrokerLocal {
	
	/**
	 * Metodo: Reporta a ibs el movimiento de elementos no serializados
	 * @param notSerElMovDto objeto que encapsula la información de un objeto no serializado
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento no serializado
	 * @author jjimenezh
	 */
	public void moveResourceFromDealerToCustomer(IBSMoveResourceBetweenCustAndDealerDTO serElMovDto) throws BusinessException;
	
	/**
	 * Metodo: Reporta a ibs el movimiento de elementos no serializados
	 * @param notSerElMovDto objeto que encapsula la información de un objeto no serializado
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento no serializado
	 * @author jjimenezh
	 */
	public void moveResourceFromCustomerToDealer(IBSMoveResourceBetweenCustAndDealerDTO serElMovDto) throws BusinessException;
	
	
	/**
	 * Metodo: Reporta a ibs el movimiento de elementos serializados
	 * @param serElMovDto objeto que encapsula la información de un objeto no serializado
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento no serializado
	 * @author jjimenezh
	 */
	public boolean moveResourceToStockHandler(IBSSerElemMovementDTO serElMovDto) throws BusinessException;
	
	/**
	 * Metodo: Actualiza el estado de un elemento serializado a IBS
	 * @param serElMovDto objeto que encapsula la informaci�n de un objeto no serializado
	 * @return True si se realiza la invocaci�n exitosamente
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento serializado
	 */
	public boolean updateResourceStatus(IBSSerElemMovementDTO serElMovDto) throws BusinessException; 
}
