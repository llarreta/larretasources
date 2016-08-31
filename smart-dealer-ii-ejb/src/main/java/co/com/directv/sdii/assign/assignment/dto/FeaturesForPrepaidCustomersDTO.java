package co.com.directv.sdii.assign.assignment.dto;


public class FeaturesForPrepaidCustomersDTO implements java.io.Serializable{

	private static final long serialVersionUID = 2768368892803348327L;

	private boolean status;
	
	private String traceResult;
	
	public FeaturesForPrepaidCustomersDTO() {
		super();
	}

	public FeaturesForPrepaidCustomersDTO(boolean status,
			String traceResult) {
		super();
		this.status = status;
		this.traceResult = traceResult;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTraceResult() {
		return traceResult;
	}

	public void setTraceResult(String traceResult) {
		this.traceResult = traceResult;
	}
	
}