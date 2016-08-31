package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerCharacteristicsResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class CustomerCharacteristicsResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 565388755665653164L;
	
	private GetCustomerCharacteristicsResult getCustomerCharacteristicsResult;
	private ResponseMetadataType responseMetadata;

	public CustomerCharacteristicsResponseDTO() {
	}

	
	public GetCustomerCharacteristicsResult getGetCustomerCharacteristicsResult() {
		return getCustomerCharacteristicsResult;
	}

	public void setGetCustomerCharacteristicsResult(
			GetCustomerCharacteristicsResult getCustomerCharacteristicsResult) {
		this.getCustomerCharacteristicsResult = getCustomerCharacteristicsResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
