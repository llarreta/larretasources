package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerAddressesResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerAddressesResponseDTO  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 958057518695866623L;

	private GetCustomerAddressesResult getCustomerAddressesResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerAddressesResponseDTO() {
	}

	public GetCustomerAddressesResult getGetCustomerAddressesResult() {
		return getCustomerAddressesResult;
	}

	public void setGetCustomerAddressesResult(
			GetCustomerAddressesResult getCustomerAddressesResult) {
		this.getCustomerAddressesResult = getCustomerAddressesResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
