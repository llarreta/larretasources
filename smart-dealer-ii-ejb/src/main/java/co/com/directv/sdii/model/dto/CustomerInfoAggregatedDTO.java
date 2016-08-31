package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;


/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInfoAggregatedDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	private CustomerInfoDTO customerInfoDTO;
	private List<CustomerProductDTO> customerProductDTO;
	private List<CustomerInquiriesDTO> customerInquiriesDTO;
	private List<CustomerFinancialDTO> customerFinancialDTO;
	private List<CustomerCharacteristicDTO> customerCharacteristicsDTO;
	private List<CustomerWorkOrderByStatusDTO> customerWorkOrdersByStatusDTO;
	private boolean warning;
	private String errorCode;
	private String errorMessage;
		
	public CustomerInfoAggregatedDTO() {
		super();
	}
	
	public CustomerInfoAggregatedDTO(CustomerInfoDTO customerInfoDTO,
			List<CustomerProductDTO> customerProductDTO,
			List<CustomerInquiriesDTO> customerInquiriesDTO,
			List<CustomerFinancialDTO> customerFinancialDTO,
			List<CustomerCharacteristicDTO> customerCharacteristicsDTO,
			List<CustomerWorkOrderByStatusDTO> customerWorkOrdersByStatusDTO) {
		super();
		this.customerInfoDTO = customerInfoDTO;
		this.customerProductDTO = customerProductDTO;
		this.customerInquiriesDTO = customerInquiriesDTO;
		this.customerFinancialDTO = customerFinancialDTO;
		this.customerCharacteristicsDTO = customerCharacteristicsDTO;
		this.customerWorkOrdersByStatusDTO = customerWorkOrdersByStatusDTO;
	}
	
	public CustomerInfoDTO getCustomerInfoDTO() {
		return customerInfoDTO;
	}
	public void setCustomerInfoDTO(CustomerInfoDTO customerInfoDTO) {
		this.customerInfoDTO = customerInfoDTO;
	}
	public List<CustomerProductDTO> getCustomerProductDTO() {
		return customerProductDTO;
	}
	public void setCustomerProductDTO(List<CustomerProductDTO> customerProductDTO) {
		this.customerProductDTO = customerProductDTO;
	}
	public List<CustomerInquiriesDTO> getCustomerInquiriesDTO() {
		return customerInquiriesDTO;
	}
	public void setCustomerInquiriesDTO(
			List<CustomerInquiriesDTO> customerInquiriesDTO) {
		this.customerInquiriesDTO = customerInquiriesDTO;
	}
	public List<CustomerFinancialDTO> getCustomerFinancialDTO() {
		return customerFinancialDTO;
	}
	public void setCustomerFinancialDTO(List<CustomerFinancialDTO> customerFinancialDTO) {
		this.customerFinancialDTO = customerFinancialDTO;
	}
	public List<CustomerCharacteristicDTO> getCustomerCharacteristicsDTO() {
		return customerCharacteristicsDTO;
	}
	public void setCustomerCharacteristicsDTO(
			List<CustomerCharacteristicDTO> customerCharacteristicsDTO) {
		this.customerCharacteristicsDTO = customerCharacteristicsDTO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<CustomerWorkOrderByStatusDTO> getCustomerWorkOrdersByStatusDTO() {
		return customerWorkOrdersByStatusDTO;
	}

	public void setCustomerWorkOrdersByStatusDTO(
			List<CustomerWorkOrderByStatusDTO> customerWorkOrdersByStatusDTO) {
		this.customerWorkOrdersByStatusDTO = customerWorkOrdersByStatusDTO;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
