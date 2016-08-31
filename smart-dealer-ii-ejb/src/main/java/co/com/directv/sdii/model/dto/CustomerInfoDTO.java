package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.directv.sdii.model.vo.CustomerVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInfoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	private CustomerVO customerVO;
	private String customerStatus;
	private List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS;
	private CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTOS;
	private CustomerInfoAddressDTO customerInfoAddressDTO;
	private CustomerInfoAddressDTO customerInfoBillingAddressDTO;
	private CustomerInfoAddressDTO customerInfoShippingAddressDTO;
	private CustomerInfoAddressDTO customerInfoBankAddressDTO;
	
	private Date birthDate;

	private String dialect;
	
	public CustomerInfoDTO(
			CustomerVO customerVO,
			String customerStatus,
			List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS,
			CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTOS,
			CustomerInfoAddressDTO customerInfoAddressDTO,
			CustomerInfoAddressDTO customerInfoBillingAddressDTO,
			CustomerInfoAddressDTO customerInfoShippingAddressDTO,
			CustomerInfoAddressDTO customerInfoBankAddressDTO,
			Date birthDate, String dialect) {
		super();
		this.customerVO = customerVO;
		this.customerStatus = customerStatus;
		this.customerInfoIndividualINamedDTOS = customerInfoIndividualINamedDTOS;
		this.customerInfoIndividualIdentifiedDTOS = customerInfoIndividualIdentifiedDTOS;
		this.customerInfoAddressDTO = customerInfoAddressDTO;
		this.customerInfoBillingAddressDTO = customerInfoBillingAddressDTO;
		this.customerInfoShippingAddressDTO = customerInfoShippingAddressDTO;
		this.customerInfoBankAddressDTO = customerInfoBankAddressDTO;
		this.birthDate = birthDate;
		this.dialect = dialect;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public CustomerInfoDTO(
			CustomerVO customerVO,
			String customerStatus,
			List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS,
			CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTOS,
			CustomerInfoAddressDTO customerInfoAddressDTO,
			CustomerInfoAddressDTO customerInfoBillingAddressDTO,
			CustomerInfoAddressDTO customerInfoShippingAddressDTO,
			CustomerInfoAddressDTO customerInfoBankAddressDTO,
			Date birthDate) {
		super();
		this.customerVO = customerVO;
		this.customerStatus = customerStatus;
		this.customerInfoIndividualINamedDTOS = customerInfoIndividualINamedDTOS;
		this.customerInfoIndividualIdentifiedDTOS = customerInfoIndividualIdentifiedDTOS;
		this.customerInfoAddressDTO = customerInfoAddressDTO;
		this.customerInfoBillingAddressDTO = customerInfoBillingAddressDTO;
		this.customerInfoShippingAddressDTO = customerInfoShippingAddressDTO;
		this.customerInfoBankAddressDTO = customerInfoBankAddressDTO;
		this.birthDate = birthDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public CustomerInfoDTO() {
		super();
	}
	
	public CustomerInfoDTO(
			CustomerVO customerVO,
			String customerStatus,
			List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS,
			CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTOS,
			CustomerInfoAddressDTO customerInfoAddressDTO,
			CustomerInfoAddressDTO customerInfoBillingAddressDTO,
			CustomerInfoAddressDTO customerInfoShippingAddressDTO,
			CustomerInfoAddressDTO customerInfoBankAddressDTO) {
		super();
		this.customerVO = customerVO;
		this.customerStatus = customerStatus;
		this.customerInfoIndividualINamedDTOS = customerInfoIndividualINamedDTOS;
		this.customerInfoIndividualIdentifiedDTOS = customerInfoIndividualIdentifiedDTOS;
		this.customerInfoAddressDTO = customerInfoAddressDTO;
		this.customerInfoBillingAddressDTO = customerInfoBillingAddressDTO;
		this.customerInfoShippingAddressDTO = customerInfoShippingAddressDTO;
		this.customerInfoShippingAddressDTO = customerInfoBankAddressDTO;
	}
	
	public CustomerVO getCustomerVO() {
		return customerVO;
	}
	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}
	public List<CustomerInfoIndividualINamedDTO> getCustomerInfoIndividualINamedDTOS() {
		return customerInfoIndividualINamedDTOS;
	}
	public void setCustomerInfoIndividualINamedDTOS(
			List<CustomerInfoIndividualINamedDTO> customerInfoIndividualINamedDTOS) {
		this.customerInfoIndividualINamedDTOS = customerInfoIndividualINamedDTOS;
	}
	public CustomerInfoIndividualIdentifiedDTO getCustomerInfoIndividualIdentifiedDTOS() {
		return customerInfoIndividualIdentifiedDTOS;
	}
	public void setCustomerInfoIndividualIdentifiedDTOS(
			CustomerInfoIndividualIdentifiedDTO customerInfoIndividualIdentifiedDTOS) {
		this.customerInfoIndividualIdentifiedDTOS = customerInfoIndividualIdentifiedDTOS;
	}
	public CustomerInfoAddressDTO getCustomerInfoAddressDTO() {
		return customerInfoAddressDTO;
	}
	public void setCustomerInfoAddressDTO(
			CustomerInfoAddressDTO customerInfoAddressDTO) {
		this.customerInfoAddressDTO = customerInfoAddressDTO;
	}
	public CustomerInfoAddressDTO getCustomerInfoBillingAddressDTO() {
		return customerInfoBillingAddressDTO;
	}
	public void setCustomerInfoBillingAddressDTO(
			CustomerInfoAddressDTO customerInfoBillingAddressDTO) {
		this.customerInfoBillingAddressDTO = customerInfoBillingAddressDTO;
	}
	public CustomerInfoAddressDTO getCustomerInfoShippingAddressDTO() {
		return customerInfoShippingAddressDTO;
	}
	public void setCustomerInfoShippingAddressDTO(
			CustomerInfoAddressDTO customerInfoShippingAddressDTO) {
		this.customerInfoShippingAddressDTO = customerInfoShippingAddressDTO;
	}
	
	public CustomerInfoAddressDTO getCustomerInfoBankAddressDTO() {
		return customerInfoBankAddressDTO;
	}

	public void setCustomerInfoBankAddressDTO(
			CustomerInfoAddressDTO customerInfoBankAddressDTO) {
		this.customerInfoBankAddressDTO = customerInfoBankAddressDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	
	

}
