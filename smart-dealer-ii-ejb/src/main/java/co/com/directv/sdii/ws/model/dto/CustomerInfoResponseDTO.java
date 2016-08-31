/**
 * 
 */
package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetCustomerInfoResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

/**
 * @author jvelezmu
 *
 */
public class CustomerInfoResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5309865499109126684L;

	private GetCustomerInfoResult getCustomerInfoResult;
	private ResponseMetadataType responseMetadata;
	
	public CustomerInfoResponseDTO() {
	}

	public GetCustomerInfoResult getGetCustomerInfoResult() {
		return getCustomerInfoResult;
	}

	public void setGetCustomerInfoResult(GetCustomerInfoResult getCustomerInfoResult) {
		this.getCustomerInfoResult = getCustomerInfoResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
