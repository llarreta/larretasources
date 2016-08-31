/**
 * Creado 22/07/2010 11:38:32
 */
package co.com.directv.sdii.ejb.business.customer;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

/**
 * Interface que define las operaciones de negocio de las entidades cliente
 * 
 * Fecha de Creación: 22/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface CustomerBusinessBeanLocal {

	/**
	 * Metodo: Crea un cliente y sus de contacto
	 * @param customer objeto de tipo cliente con los medios de contacto
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimnezh
	 */
	public void createCustomer(CustomerVO customer)throws BusinessException;
	
	/**
	 * Metodo: Actualiza la información de un cliente borrando los medios de contacto existentes
	 * y creando unos nuevos con los que trae el objeto
	 * @param customer información del cliente
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateCustomer(CustomerVO customer)throws BusinessException;
	
	// jvelez 07-02-2011 Métodos utilizados en el módulo de Servicio al Cliente
	
	/**
	 * Método que obtiene la información básica del cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	public CustomerInfoAggregatedDTO getCustomerInfoFromIBS(String customerKey, String sourceId) throws BusinessException;
	
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

	/**
	 * Método que obtiene las WorkOrders de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(String customerKey, String sourceId) throws BusinessException;
//
//	public CustomerVO getCustomerInfoFromIBSAsHSP(String customerKey,
//			String sourceId)throws BusinessException;
//
//	/**
//	 * Metodo: Obtener los códigos de IBS asociados a un cliente
//	 * @param customerKey número del documento del cliente
//	 * @param sourceId código del país del clietne
//	 * @return listado de cadenas con los códigos IBS del cliente
//	 * @throws BusinessException
//	 * @author wjimenez
//	 */
//	public List<String> getCustomerIBSCodes(String customerKey, String sourceId)
//			throws BusinessException;
//
	/**
	 * Llama al servicio de cliente IBS para obtener los elementos que estan asociados a este
	 * @param customerCode
	 * @return CustomerVO
	 */
	public CustomerVO getCustomerResources(String customerCode, Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los customer por documentTypeId, documentNumber, ibsCode y countryId
	 * @param documentTypeId
	 * @param documentNumber
	 * @param ibsCode
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
															  		String documentNumber, 
															  		String ibsCode,
															  		Long countryId) throws BusinessException;
	
}
