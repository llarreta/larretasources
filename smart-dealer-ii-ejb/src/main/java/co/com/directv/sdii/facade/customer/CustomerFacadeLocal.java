package co.com.directv.sdii.facade.customer;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

@Local
public interface CustomerFacadeLocal {

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
	 * Método que obtiene las WorkOrders de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(String customerKey, String sourceId) throws BusinessException;
//
//	/**
//	 * Metodo: <Descripcion>
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException <tipo> <descripcion>
//	 * @author
//	 */
//	public CustomerVO getCustomerInfoFromIBSAsHSP(String customerKey,
//			String sourceId) throws BusinessException;
//	
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
