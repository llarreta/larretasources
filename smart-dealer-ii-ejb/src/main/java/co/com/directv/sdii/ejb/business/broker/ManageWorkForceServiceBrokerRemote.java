/**
 * Creado 18/02/2011 15:24:00
 */
package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Remote;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;

/**
 * Desacopla la comunicación con el ESB en la invocación de operaciones 
 * al WS de ManageWorkForce
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Remote
public interface ManageWorkForceServiceBrokerRemote {

	
	/**
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param ibsWorkOrderStatusCode
	 * @param countryCode
	 * @return CustWorkOrdersResponseDTO
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String ibsWorkOrderStatusCode, String countryCode) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @return CustWorkOrdersResponseDTO
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String countryCode) throws BusinessException;	

	/**
	 * Metodo: <Descripcion>
	 * @param assignRequestDTO
	 * @param countryId
	 * @return AssignRequestDTO
	 * @throws BusinessException
     */
	public AssignRequestDTO getInfoCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO, Long countryId) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param workOrderCode
	 * @param countryCode
	 * @return AssignRequestDTO
	 * @throws BusinessException
     */
	public AssignRequestDTO getCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Invoca la operacion de negocio de IBS
	 * para completar la finalizacion de la WorkOrder.
	 * @param attentionRequestDTO WoAttentionsRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void completeWorkOrder(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException;
}
