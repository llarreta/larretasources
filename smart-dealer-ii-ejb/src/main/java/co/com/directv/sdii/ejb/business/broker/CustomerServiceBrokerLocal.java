/**
 * Creado 20/10/2010 10:28:40
 */
package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerResourcesDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

/**
 * Define las operaciones del broker de servicios para el WS de clientes expuesto por DTV IBS
 * 
 * Fecha de Creación: 20/10/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.impl.CustomerServiceBrokerImpl
 */
@Local
public interface CustomerServiceBrokerLocal {

//	public String getCustomerCharacteristicFromIBS(String characteristicName, String customerCode, String countryCode) throws BusinessException;
	
//	/**
//	 * Metodo: Pobla la información de un cliente con los datos obtenidos desde el servicio
//	 * de IBs
//	 * @param customer Datos del cliente encapsulados en un objeto que retorna el servicio de ibs
//	 * @param customerKey identificador del cliente obtenido del servicio de ibs
//	 * @param sdiiCustomer objeto del dominio de SDII en donde se encapsularán los datos
//	 * provenientes de IBS
//	 * @param sdiiWorkOrder Work order de SDII asociada al cliente
//	 * @throws BusinessException En caso de error al tratar de obtener la información del cliente o 
//	 * que alguna de las propiedades del objeto de IBS cliente viene nula y es necesaria para SDII
//	 * @author jjimenezh
//	 * 
//	 */
//	public void populateCustomerFromIBSCust(String customerCode, Country country, Customer sdiiCustomer, String woCountryCode, String woPostalCodeCode, WorkOrder sdiiWorkOrder) throws BusinessException;
	
//	/**
//	 * 
//	 * Metodo: Pobla la informacion de un edificio con los datos obtenidos desde el servicio de IBS
//	 * @param buildingCode codigo del edificio que se va a buscar en IBS y crear o actualziar en SDII
//	 * @param countryCode Codigo del pais para invocar el servicio de IBS
//	 * @param Building edificio que se va a crear o a actualizar
//	 * @throws BusinessException <tipo> <descripcion>
//	 * @author jnova
//	 */
//	public void populateBuildingFromIbsCust(String buildingCode, String countryCode,Building sdiiBuilding) throws BusinessException;
	
	// jvelez 07-02-2011 Métodos utilizados en el módulo de Servicio al Cliente
	
//	/**
//	 * Método que obtiene la información básica del cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerInfoResponseDTO getCustomerInfoFromIBS(String customerKey, String sourceId) throws BusinessException;
	
//	/**
//	 * Método que obtiene el histórico de los contactos de un cliente registrados en IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerInquiriesResponseDTO getCustomerInquiriesFromIBS(String customerKey, String sourceId) throws BusinessException;
	
//	/**
//	 * Método que obtiene los KeyWords de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerCharacteristicsResponseDTO getCustomerCharacteristicsFromIBS(String customerKey, String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene las direcciones de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerAddressesResponseDTO getCustomerAddressesFromIBS(String customerKey, String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene las WorkOrders de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene los productos de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public CustomerProductsResponseDTO getCustomerProductsFromIBS(String customerKey, String sourceId) throws BusinessException;
	
	/**
	 * Método que obtiene los elementos de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(String customerKey, String sourceId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene los elementos de un cliente desde IBS y los mapea a un objeto de negocio
	 * @param customerKey
	 * @param sourceId
	 * @param activeStatusId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<CustomerResourcesDTO> getActiveCustomerResourcesFromIBSIntoSDModel(String customerKey, String sourceId) throws BusinessException;
	
//	/**
//	 * Método que obtiene la información financiera de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public InvoiceResponseDTO getInvoiceFromIBS(Integer customerKey, String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene la información financiera de un cliente desde IBS.
//	 * 
//	 * @param customerCode
//	 * @param accountNumber
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	public FinancialInfoResponseDTO getFinancialInfoFromIBS(Long customerCode, String accountNumber, String sourceId) throws BusinessException;
	
	/**
	 * Método que obtiene los Códigos IBS de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(String customerKey, String sourceId) throws BusinessException;

//	/**
//	 * Metodo: Convierte la información de un cliente a un objeto de HSP
//	 * @param customer información del cliente a ser convertida
//	 * @param sourceId código del país en formato ISO2
//	 * @return información del cliente
//	 * @throws BusinessException En caso de error al realizar la conversión
//	 * @author jjimenezh
//	 */
//	public CustomerVO convertCustomerInfo2HSPFormat(com.directvla.schema.businessdomain.customer.v1_0.Customer customer, String sourceId)throws BusinessException;

}
