/**
 * 
 */
package co.com.directv.sdii.ws.customer;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

/**
 * Interfaz que define los métodos utilizados en el modulo de atención al
 * cliente. Estos métodos se comportan como un proxy que invoca servicios hacia
 * el ESB. No se implementa ninguna lógica de negocio adiconal al llamado del WS.
 * 
 * @author jvelezmu
 *
 */

@WebService(name="CustomerServices",targetNamespace="http://customerhsp.business.ws.sdii.directv.com.co/")
public interface ICustomerWS {

	// jvelez 07-02-2011 Métodos utilizados en el módulo de Servicio al Cliente
	
	/**
	 * Método que obtiene la información básica del cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getCustomerInfoFromIBS", action="getCustomerInfoFromIBS")
	public CustomerInfoAggregatedDTO getCustomerInfoFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene la información básica del cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerInfoFromIBSAsHSP", action="getCustomerInfoFromIBSAsHSP")
//	public CustomerVO getCustomerInfoFromIBSAsHSP(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	
//	/**
//	 * Método que obtiene el histórico de los contactos de un cliente registrados en IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerInquiriesFromIBS", action="getCustomerInquiriesFromIBS")
//	public CustomerInquiriesResponseDTO getCustomerInquiriesFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
	
//	/**
//	 * Método que obtiene los KeyWords de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerCharacteristicsFromIBS", action="getCustomerCharacteristicsFromIBS")
//	public CustomerCharacteristicsResponseDTO getCustomerCharacteristicsFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene las direcciones de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerAddressesFromIBS", action="getCustomerAddressesFromIBS")
//	public CustomerAddressesResponseDTO getCustomerAddressesFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	/**
//	 * Método que obtiene las WorkOrders de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerWorkOrdersFromIBS", action="getCustomerWorkOrdersFromIBS")
//	public CustomerWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	
//	/**
//	 * Método que obtiene las WorkOrders de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerWorkOrdersFromManageWorkForceWSIBS", action="getCustomerWorkOrdersFromManageWorkForceWSIBS")
//	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
//	
//	
//	/**
//	 * Método que obtiene los productos de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getCustomerProductsFromIBS", action="getCustomerProductsFromIBS")
//	public CustomerProductsResponseDTO getCustomerProductsFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
	
	/**
	 * Método que obtiene los elementos de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getCustomerResourcesFromIBS", action="getCustomerResourcesFromIBS")
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
	
//	/**
//	 * Método que obtiene la información financiera de un cliente desde IBS.
//	 * 
//	 * @param customerKey
//	 * @param sourceId
//	 * @return
//	 * @throws BusinessException
//	 */
//	@WebMethod(operationName="getInvoiceFromIBS", action="getInvoiceFromIBS")
//	public InvoiceResponseDTO getInvoiceFromIBS(@WebParam(name="customerKey")Integer customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
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
//	@WebMethod(operationName="getFinancialInfoFromIBS", action="getFinancialInfoFromIBS")
//	public FinancialInfoResponseDTO getFinancialInfoFromIBS(@WebParam(name="customerCode")Long customerCode, @WebParam(name="accountNumber")String accountNumber, @WebParam(name="sourceId")String sourceId) throws BusinessException;
	
	/**
	 * Método que obtiene los Códigos IBS de un cliente desde IBS.
	 * 
	 * @param customerKey
	 * @param sourceId
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getCustomersByIdentificationFromIBS", action="getCustomersByIdentificationFromIBS")
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId) throws BusinessException;
	
//	/**
//	 * Metodo: Obtener los códigos de IBS asociados a un cliente
//	 * @param customerKey número del documento del cliente
//	 * @param sourceId código del país del clietne
//	 * @return listado de cadenas con los códigos IBS del cliente
//	 * @throws BusinessException
//	 * @author wjimenez
//	 */
//	@WebMethod(operationName="getCustomerIBSCodes", action="getCustomerIBSCodes")
//	public List<String> getCustomerIBSCodes(@WebParam(name="customerKey")String customerKey, @WebParam(name="sourceId")String sourceId)
//			throws BusinessException;
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
	@WebMethod(operationName="getCustomerByDocTypeIdDocNumberAndIbsCode", action="getCustomerByDocTypeIdDocNumberAndIbsCode")
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(@WebParam(name="documentTypeId") Long documentTypeId,
																	@WebParam(name="documentNumber") String documentNumber, 
																	@WebParam(name="ibsCode") String ibsCode,
																	@WebParam(name="countryId") Long countryId) throws BusinessException;
}
