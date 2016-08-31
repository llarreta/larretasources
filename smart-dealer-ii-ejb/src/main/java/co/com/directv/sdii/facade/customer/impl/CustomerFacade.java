package co.com.directv.sdii.facade.customer.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.customer.CustomerBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.customer.CustomerFacadeLocal;
import co.com.directv.sdii.model.dto.CustomerInfoAggregatedDTO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomerResourcesResponseDTO;
import co.com.directv.sdii.ws.model.dto.CustomersByIdentificationResponseDTO;

/**
 * Session Bean implementation class CustomerFacade
 */
@Stateless(name="CustomerFacadeLocal",mappedName="ejb/CustomerFacadeLocal")
public class CustomerFacade implements CustomerFacadeLocal {

	@EJB(name="CustomerBusinessBeanLocal", beanInterface=CustomerBusinessBeanLocal.class)
	private CustomerBusinessBeanLocal customerBusinessBean;
	
    /**
     * Default constructor. 
     */
    public CustomerFacade() {
    }

//	@Override
//	public CustomerAddressesResponseDTO getCustomerAddressesFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerAddressesFromIBS(customerKey, sourceId);
//	}
//
//	@Override
//	public CustomerCharacteristicsResponseDTO getCustomerCharacteristicsFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerCharacteristicsFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomerInfoAggregatedDTO getCustomerInfoFromIBS(String customerKey,
			String sourceId) throws BusinessException {
		return customerBusinessBean.getCustomerInfoFromIBS(customerKey, sourceId);
	}

//	@Override
//	public CustomerInquiriesResponseDTO getCustomerInquiriesFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerInquiriesFromIBS(customerKey, sourceId);
//	}

//	@Override
//	public CustomerProductsResponseDTO getCustomerProductsFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerProductsFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomerResourcesResponseDTO getCustomerResourcesFromIBS(
			String customerKey, String sourceId) throws BusinessException {
		return customerBusinessBean.getCustomerResourcesFromIBS(customerKey, sourceId);
	}

//	@Override
//	public CustomerWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(
//			String customerKey, String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerWorkOrdersFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustomersByIdentificationResponseDTO getCustomersByIdentificationFromIBS(
			String customerKey, String sourceId) throws BusinessException {
		return customerBusinessBean.getCustomersByIdentificationFromIBS(customerKey, sourceId);
	}

//	/*
//	 * (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.customer.CustomerFacadeLocal#getCustomerIBSCodes(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public List<String> getCustomerIBSCodes(String customerKey, String sourceId)
//			throws BusinessException {
//		return customerBusinessBean.getCustomerIBSCodes(customerKey, sourceId);
//	}
//	
//	@Override
//	public FinancialInfoResponseDTO getFinancialInfoFromIBS(Long customerCode,
//			String accountNumber, String sourceId) throws BusinessException {
//		return customerBusinessBean.getFinancialInfoFromIBS(customerCode, accountNumber, sourceId);
//	}
//
//	@Override
//	public InvoiceResponseDTO getInvoiceFromIBS(Integer customerKey,
//			String sourceId) throws BusinessException {
//		return customerBusinessBean.getInvoiceFromIBS(customerKey, sourceId);
//	}

	@Override
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromManageWorkForceWSIBS(
			String customerKey, String sourceId) throws BusinessException {
		return customerBusinessBean.getCustomerWorkOrdersFromManageWorkForceWSIBS(customerKey, sourceId);
	}
//
//	@Override
//	public CustomerVO getCustomerInfoFromIBSAsHSP(String customerKey,
//			String sourceId) throws BusinessException {
//		return customerBusinessBean.getCustomerInfoFromIBSAsHSP(customerKey, sourceId);
//	}
//	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.customer.CustomerFacadeLocal#getCustomerByDocTypeIdDocNumberAndIbsCode(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public List<CustomerVO> getCustomerByDocTypeIdDocNumberAndIbsCode(Long documentTypeId,
	  		String documentNumber, 
	  		String ibsCode,
	  		Long countryId) throws BusinessException {
		return customerBusinessBean.getCustomerByDocTypeIdDocNumberAndIbsCode(documentTypeId,
																		      documentNumber, 
																		  	  ibsCode,
																		  	  countryId);
	}

}
