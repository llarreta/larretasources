package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * CC54
 * DTO workorders pendientes por falta de materiales.
 * 
 * Fecha de Creación: 23/10/2012
 * @author carlopez
 * @version 1.0
 * 
 * @see     
 */
public class WoPendingLackMaterialsDTO implements Serializable{
	

	private static final long serialVersionUID = 8169733426337045591L;
	private String depotCode;
	private String dealerName;
	private Date statusDate;
	private String ibsCustomerCode;
	private String ibsWoCode;
	private String woTypeName;
	private String woReasonName;
	private String woDescription;
	
	public WoPendingLackMaterialsDTO() {
		super();
	}
	
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getIbsCustomerCode() {
		return ibsCustomerCode;
	}
	public void setIbsCustomerCode(String ibsCustomerCode) {
		this.ibsCustomerCode = ibsCustomerCode;
	}

	public String getIbsWoCode() {
		return ibsWoCode;
	}

	public void setIbsWoCode(String ibsWoCode) {
		this.ibsWoCode = ibsWoCode;
	}

	public String getWoTypeName() {
		return woTypeName;
	}
	public void setWoTypeName(String woTypeName) {
		this.woTypeName = woTypeName;
	}
	public String getWoReasonName() {
		return woReasonName;
	}
	public void setWoReasonName(String woReasonName) {
		this.woReasonName = woReasonName;
	}
	public String getWoDescription() {
		return woDescription;
	}
	public void setWoDescription(String woDescription) {
		this.woDescription = woDescription;
	}
	
	
	
	

}
