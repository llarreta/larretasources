package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerProductsResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerProductsResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1945020536164346944L;

	private GetCustomerProductsResult getCustomerProductsResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerProductsResponseDTO() {
	}

	public GetCustomerProductsResult getGetCustomerProductsResult() {
		return getCustomerProductsResult;
	}

	public void setGetCustomerProductsResult(
			GetCustomerProductsResult getCustomerProductsResult) {
		this.getCustomerProductsResult = getCustomerProductsResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
