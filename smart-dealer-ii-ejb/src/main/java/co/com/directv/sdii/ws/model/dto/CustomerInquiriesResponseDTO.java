package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerInquiriesResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerInquiriesResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7028119865004931786L;

	private GetCustomerInquiriesResult getCustomerInquiriesResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerInquiriesResponseDTO() {
	}

	public GetCustomerInquiriesResult getGetCustomerInquiriesResult() {
		return getCustomerInquiriesResult;
	}

	public void setGetCustomerInquiriesResult(
			GetCustomerInquiriesResult getCustomerInquiriesResult) {
		this.getCustomerInquiriesResult = getCustomerInquiriesResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
