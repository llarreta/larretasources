package co.com.directv.sdii.ws.customer.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.customer.CustomerFacadeLocal;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.ws.customer.ICustomerWS;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

/**
 * Clase que implementa los métodos utilizados en el modulo de atención al
 * cliente. Estos métodos se comportan como un proxy que invoca servicios hacia
 * el ESB. No se implementa ninguna lógica de negocio adiconal al llamado del WS.
 * 
 * @author jvelezmu
 *
 */

@MTOM
@WebService(serviceName="CustomerServices",
		endpointInterface="co.com.directv.sdii.ws.customer.ICustomerWS",
		targetNamespace="http://customerhsp.business.ws.sdii.directv.com.co/",
		portName="CustomerPort")
@Stateless()
public class CustomerWS implements ICustomerWS{

	@EJB
	private CustomerFacadeLocal customerFacade;
	
//	@Override
//	public CustomerAddressesResponseDTO getCustomerAddressesFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerAddressesFromIBS(customerKey, sourceId);
//	}
//
//	@Override
//	public CustomerCharacteristicsResponseDTO getCustomerCharacteristicsFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerCharacteristicsFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomerInfoAggregatedDTO getCustomerInfoFromIBS(String customerKey,
			String sourceId) throws BusinessException {
		return customerFacade.getCustomerInfoFromIBS(customerKey, sourceId);
	}

//	@Override
//	public CustomerInquiriesResponseDTO getCustomerInquiriesFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerInquiriesFromIBS(customerKey, sourceId);
//	}

//	@Override
//	public CustomerProductsResponseDTO getCustomerProductsFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerProductsFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(
			String customerKey, String sourceId) throws BusinessException {
		return customerFacade.getCustomerResourcesFromIBS(customerKey, sourceId);
	}

//	@Override
//	public CustomerWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerWorkOrdersFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(
			String customerKey, String sourceId) throws BusinessException {
		return customerFacade.getCustomersByIdentificationFromIBS(customerKey, sourceId);
	}

//	/*
//	 * (non-Javadoc)
//	 * @see co.com.directv.sdii.ws.customer.ICustomerWS#getCustomerIBSCodes(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public List<String> getCustomerIBSCodes(String customerKey, String sourceId)
//			throws BusinessException {
//		return customerFacade.getCustomerIBSCodes(customerKey, sourceId);
//	}
//	
//	@Override
//	public FinancialInfoResponseDTO getFinancialInfoFromIBS(Long customerCode,
//			String accountNumber, String sourceId) throws BusinessException {
//		return customerFacade.getFinancialInfoFromIBS(customerCode, accountNumber, sourceId);
//	}
//
//	@Override
//	public InvoiceResponseDTO getInvoiceFromIBS(Integer customerKey,
//			String sourceId) throws BusinessException {
//		return customerFacade.getInvoiceFromIBS(customerKey, sourceId);
//	}
//
//	@Override
//	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerFacade.getCustomerWorkOrdersFromManageWorkForceWSIBS(customerKey, sourceId);
//	}
//
//	@Override
//	public CustomerVO getCustomerInfoFromIBSAsHSP(String customerKey,
//			String sourceId) throws BusinessException {
//		return customerFacade.getCustomerInfoFromIBSAsHSP(customerKey, sourceId);
//	}
//	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.customer.ICustomerWS#getCustomerByDocTypeIdDocNumberAndIbsCode(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
	  		String documentNumber, 
	  		String ibsCode,
	  		Long countryId) throws BusinessException {
		return customerFacade.getCustomerByDocTypeIdDocNumberAndIbsCode(documentTypeId,
																		      documentNumber, 
																		  	  ibsCode,
																		  	  countryId);
	}
	
}