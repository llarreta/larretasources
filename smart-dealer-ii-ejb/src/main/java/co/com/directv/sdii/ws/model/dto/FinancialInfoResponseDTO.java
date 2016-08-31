package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetFinancialInfoResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class FinancialInfoResponseDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4177872742308319356L;

	private GetFinancialInfoResult getFinancialInfoResult;
	private ResponseMetadataType responseMetadata;
	
	public FinancialInfoResponseDTO() {
	}
	public GetFinancialInfoResult getGetFinancialInfoResult() {
		return getFinancialInfoResult;
	}
	public void setGetFinancialInfoResult(
			GetFinancialInfoResult getFinancialInfoResult) {
		this.getFinancialInfoResult = getFinancialInfoResult;
	}
	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}
	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
