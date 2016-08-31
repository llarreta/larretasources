package co.com.directv.sdii.ws.model.dto;

import java.io.Serializable;

import com.directvla.contract.crm.customer.v1_0.GetInvoiceResult;
import com.directvla.schema.util.commondatatypes.v1_0.ResponseMetadataType;

public class InvoiceResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -337598605050611165L;

	private GetInvoiceResult getInvoiceResult;
	private ResponseMetadataType responseMetadata;
	
	public InvoiceResponseDTO() {
	}

	public GetInvoiceResult getGetInvoiceResult() {
		return getInvoiceResult;
	}

	public void setGetInvoiceResult(GetInvoiceResult getInvoiceResult) {
		this.getInvoiceResult = getInvoiceResult;
	}

	public ResponseMetadataType getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadataType responseMetadata) {
		this.responseMetadata = responseMetadata;
	}

}
