package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

//import com.directvla.contract.crm.customer.v1_0.GetCustomerWorkOrdersResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerWorkOrdersResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8984316785370266381L;

	//private GetCustomerWorkOrdersResult getCustomerWorkOrdersResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerWorkOrdersResponseDTO() {
	}

	/*public GetCustomerWorkOrdersResult getGetCustomerWorkOrdersResult() {
		return getCustomerWorkOrdersResult;
	}

	public void setGetCustomerWorkOrdersResult(
			GetCustomerWorkOrdersResult getCustomerWorkOrdersResult) {
		this.getCustomerWorkOrdersResult = getCustomerWorkOrdersResult;
	}*/

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
