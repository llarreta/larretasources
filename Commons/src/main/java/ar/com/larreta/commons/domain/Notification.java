package ar.com.larreta.commons.domain;


public abstract class Notification extends ParametricEntity {
	
	private String header;
	private Boolean withConfirm = Boolean.TRUE;

	public Boolean getWithConfirm() {
		return withConfirm;
	}

	public void setWithConfirm(Boolean withConfirm) {
		this.withConfirm = withConfirm;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	public abstract void execute();
	

}
