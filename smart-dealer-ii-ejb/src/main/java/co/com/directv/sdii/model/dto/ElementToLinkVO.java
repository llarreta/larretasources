package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementStatusVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;

public class ElementToLinkVO implements Serializable {

	
	private static final long serialVersionUID = 867939556901529248L;
	
	private Long elementID;
	private ElementTypeVO elementType;
	private ElementModelVO elementModel;
	private String elementSerial;
	private String rid;
	private String linkedElementSerialCode;
	private ElementStatusVO elementStatus;
	private Date elementEntryDate;
	public Long getElementID() {
		return elementID;
	}
	public void setElementID(Long elementID) {
		this.elementID = elementID;
	}
	public ElementTypeVO getElementType() {
		return elementType;
	}
	public void setElementType(ElementTypeVO elementType) {
		this.elementType = elementType;
	}
	public ElementModelVO getElementModel() {
		return elementModel;
	}
	public void setElementModel(ElementModelVO elementModel) {
		this.elementModel = elementModel;
	}
	public String getElementSerial() {
		return elementSerial;
	}
	public void setElementSerial(String elementSerial) {
		this.elementSerial = elementSerial;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getLinkedElementSerialCode() {
		return linkedElementSerialCode;
	}
	public void setLinkedElementSerialCode(String linkedElementSerialCode) {
		this.linkedElementSerialCode = linkedElementSerialCode;
	}
	public ElementStatusVO getElementStatus() {
		return elementStatus;
	}
	public void setElementStatus(ElementStatusVO elementStatus) {
		this.elementStatus = elementStatus;
	}
	public Date getElementEntryDate() {
		return elementEntryDate;
	}
	public void setElementEntryDate(Date elementEntryDate) {
		this.elementEntryDate = elementEntryDate;
	}

}
