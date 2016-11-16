package co.com.directv.sdii.ejb.business.file;

import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ElementTypeVO;

public class BasicProcessorAuxData {
	
	private FileRecordDTO fileRecord;
	private BusinessException beException;
	private String rnNumber;
	private String elementTypeCode;
	private String serialCode;
	private String serialCodeLinked;
	private String quantityStr;
	private ElementTypeVO elementTypeVO;
	private boolean bError;
	
	public BasicProcessorAuxData(FileRecordDTO fileRecordDTO){
		this.fileRecord = fileRecordDTO;
		this.beException = null;
	}
	
	public void rellenaDatos(int POS_NUM_RN, int POS_ELEMENT_TYPE_CODE, int POS_SERIAL, int POS_LINKED_SERIAL, int POS_QUANTITY){
		this.rnNumber = fileRecord.getRowData()[POS_NUM_RN];
		this.elementTypeCode = fileRecord.getRowData()[POS_ELEMENT_TYPE_CODE];
		this.serialCode = fileRecord.getRowData()[POS_SERIAL];
		this.serialCodeLinked = fileRecord.getRowData()[POS_LINKED_SERIAL];
		this.quantityStr = fileRecord.getRowData()[POS_QUANTITY];
		
		if(this.rnNumber == null){
			this.rnNumber="";
		}
		if(this.elementTypeCode == null){
			this.elementTypeCode="";
		}
		if(this.serialCode == null){
			this.serialCode="";
		}
		if(this.serialCodeLinked == null){
			this.serialCodeLinked="";
		}
		if(this.quantityStr == null){
			this.quantityStr="";
		}
		
	}

	public FileRecordDTO getFileRecordDTO() {
		return fileRecord;
	}

	public void setFileRecordDTO(FileRecordDTO fileRecordDTO) {
		this.fileRecord = fileRecordDTO;
	}

	public BusinessException getBeException() {
		return beException;
	}

	public void setBeException(BusinessException beException) {
		this.beException = beException;
		this.bError = true;
	}

	public String getRnNumber() {
		return rnNumber;
	}

	public void setRnNumber(String rnNumber) {
		this.rnNumber = rnNumber;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getSerialCodeLinked() {
		return serialCodeLinked;
	}

	public void setSerialCodeLinked(String serialCodeLinked) {
		this.serialCodeLinked = serialCodeLinked;
	}

	public String getQuantityStr() {
		return quantityStr;
	}

	public void setQuantityStr(String quantityStr) {
		this.quantityStr = quantityStr;
	}

	public ElementTypeVO getElementTypeVO() {
		return elementTypeVO;
	}

	public void setElementTypeVO(ElementTypeVO elementTypeVO) {
		this.elementTypeVO = elementTypeVO;
	}

	public boolean isError() {
		return this.bError;
	}
	
}
