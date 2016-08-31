package co.com.directv.sdii.ejb.business.stock;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.EnvelopeEncapsulateResponse;
import co.com.directv.sdii.model.dto.InventoryDTO;
import co.com.directv.sdii.model.dto.RequiredServiceElementDTO;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;

/**
 * 
 * Interfaz de negocio que expone las funciones necesarias para el módulo de CORE 
 * 
 * Fecha de Creación: Nov 3, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoreStockBusinessLocal {

	/**
	 * Metodo: Retorna un listado de elmentos 
	 * Serializados o no serializados segun el 
	 * tipo de Servicio.
	 * @param inventoryDTO
	 * @return RequiredServiceElementDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public RequiredServiceElementDTO getResourcesByServiceType(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un elemento
	 * serializado.
	 * @param inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public ElementDTO getSerializedResource(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * Metodo: Realizar el registro de los
	 * elementos no serializados utilizados en la 
	 * Atención y/o Finalización de la WorkOrder.
	 * @param inventoryDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public EnvelopeEncapsulateResponse registerNotSerializedResources (InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * Metodo: Realizar el registro de los
	 * elementos serializados utilizados en la 
	 * Atención y/o Finalización de la WorkOrder.
	 * @param inventoryDTO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public EnvelopeEncapsulateResponse registerSerializedResources (InventoryDTO inventoryDTO) throws BusinessException;
	
	
	/**
	 * Metodo encargado de registrar elementos serializados en una ubucacion del cliente 
	 * @param attentionElements
	 * @param inventoryDTO
	 * @param customerWarehouse
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void registerSerializedResourcesForInstallation(WOAttentionElementsRequestDTO attentionElements , InventoryDTO inventoryDTO , Warehouse customerWarehouse ) throws BusinessException;
	
	
	/**
	 * Realiza la logica para realizar el flujo alternativo del CU INV 145  
	 * Retirar elementos de la ubicación del cliente
	 * @param attentionElements
	 * @param inventoryDTO
	 * @param customerWarehouse
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void registerSerializedResourcesForRecovery( WOAttentionElementsRequestDTO attentionElements , InventoryDTO inventoryDTO , Warehouse customerWarehouse) throws BusinessException;

	/**
	 * Operación encargada de verificar la existencia del deco en la ubicaciones de stock de la compañia
	 * @param serial
	 * @param dealerCode
	 * @param dealerId
	 * @param whTypeCodes
	 * @param throwsException
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws BusinessException
	 */
	public WarehouseElement getWarehouseElementWhBySerialAndDealerCodeOrId( String serial , Long dealerCode , Long dealerId , List<String> whTypeCodes, boolean throwsException )throws DAOServiceException, DAOSQLException, BusinessException;
	
}
