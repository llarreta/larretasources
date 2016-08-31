package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomersByIdentificationResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomersByIdentificationResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5021868616881264100L;

	private GetCustomersByIdentificationResult getCustomersByIdentificationResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomersByIdentificationResponseDTO() {
	}

	public GetCustomersByIdentificationResult getGetCustomersByIdentificationResult() {
		return getCustomersByIdentificationResult;
	}

	public void setGetCustomersByIdentificationResult(
			GetCustomersByIdentificationResult getCustomersByIdentificationResult) {
		this.getCustomersByIdentificationResult = getCustomersByIdentificationResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
