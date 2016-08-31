package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerResourcesResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerResourcesResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7719505008366718958L;

	private GetCustomerResourcesResult getCustomerResourcesResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerResourcesResponseDTO() {
	}

	public GetCustomerResourcesResult getGetCustomerResourcesResult() {
		return getCustomerResourcesResult;
	}

	public void setGetCustomerResourcesResult(
			GetCustomerResourcesResult getCustomerResourcesResult) {
		this.getCustomerResourcesResult = getCustomerResourcesResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
