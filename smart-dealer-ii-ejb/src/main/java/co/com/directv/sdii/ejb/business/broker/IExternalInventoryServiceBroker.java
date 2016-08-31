package co.com.directv.sdii.ejb.business.broker;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;

/**
 * 
 * Interfaz que define las operaciones del sistema
 * de inventarios externo. 
 * 
 * Fecha de Creación: 21/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public interface IExternalInventoryServiceBroker {

	/**
	 * 
	 * Metodo: Retorna la informacion de un elemento
	 * serializado obtenido de un sistema de 
	 * inventarios externo.
	 * 
	 * @param  InventoryDTO inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ElementDTO getSerializedResource(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * Metodo: Retorna la informacion de un elemento
	 * no serializado obtenido de un sistema de 
	 * inventarios externo.
	 * 
	 * @param  InventoryDTO inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ElementDTO getNotSerializedResource(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna un listado de elmentos 
	 * Serializdos o no serializados segun el 
	 * tipo de Servicio.
	 * 
	 * @param  InventoryDTO inventoryDTO
	 * @return RequiredServiceElementDTO 
	 * @throws BusinessException
	 * @author jalopez
	 */
	public RequiredServiceElementDTO getResourcesByServiceType(InventoryDTO inventoryDTO) throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Invoca la operacion de inventarios del
	 * sistema externo para realizar el registro de los
	 * elementos serializados utilizados en la 
	 * Atención y/o Finalización de la WorkOrder.
	 * @param  InventoryDTO inventoryDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse registerSerializedResources (InventoryDTO inventoryDTO) throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Invoca la operacion de inventarios del
	 * sistema externo para realizar el registro de los
	 * elementos no serializadosutilizados en la 
	 * Atención y/o Finalización de la WorkOrder.
	 *  
	 * @param  InventoryDTO inventoryDTO
	 * @return EnvelopeEncapsulateResponse
	 * @throws BusinessException
	 * @author jalopez
	 */
	public EnvelopeEncapsulateResponse registerNotSerializedResources (InventoryDTO inventoryDTO) throws BusinessException;
	
}
