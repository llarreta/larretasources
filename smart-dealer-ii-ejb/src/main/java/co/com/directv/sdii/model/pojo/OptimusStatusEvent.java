package co.com.directv.sdii.model.pojo;

import java.sql.Blob;
import java.util.Date;

public class OptimusStatusEvent implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3365121724205538115L;
	
	private Long id;
	private String woCode;
	private Date creationDate;
	private String type;
	private Blob reqXml;
	private String status;
	private String logDescription;
	
	/** default constructor **/
	public OptimusStatusEvent(){
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Blob getReqXml() {
		return reqXml;
	}
	public void setReqXml(Blob reqXml) {
		this.reqXml = reqXml;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogDescription() {
		return logDescription;
	}
	public void setLogDescription(String logDescription) {
		this.logDescription = logDescription;
	}
	
}
