package co.com.directv.sdii.ejb.business.file.data;

import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;

public class LoadMassiveSerializedAdjusmentData {

	private FileRecordDTO fileRecord;
	private String serialElement;
	private String codeWarehouseSource;
	private String codeWarehouseTarget;
	private String serialElementLink;
	private boolean bError;
	private String sError;
	private WarehouseElement warehouseElement;
	private Warehouse warehouseTargetVO;
	
	public LoadMassiveSerializedAdjusmentData(FileRecordDTO fileRecord,int POS_SERIAL_ELEMENT, int POS_COD_UBICACION_ORIGEN, int POS_COD_UBICACION_DESTINO, int POS_SERIAL_LINK_ELEMENT){
		this.fileRecord = fileRecord;
		this.serialElement = this.fileRecord.getRowData()[POS_SERIAL_ELEMENT];
		this.codeWarehouseSource = this.fileRecord.getRowData()[POS_COD_UBICACION_ORIGEN];
		this.codeWarehouseTarget = this.fileRecord.getRowData()[POS_COD_UBICACION_DESTINO];
		this.serialElementLink = this.fileRecord.getRowData()[POS_SERIAL_LINK_ELEMENT];
		this.bError = false;
	}

	public FileRecordDTO getFileRecord() {
		return fileRecord;
	}

	public void setFileRecord(FileRecordDTO fileRecord) {
		this.fileRecord = fileRecord;
	}

	public String getSerialElement() {
		return serialElement;
	}

	public void setSerialElement(String serialElement) {
		this.serialElement = serialElement;
	}

	public String getCodeWarehouseSource() {
		return codeWarehouseSource;
	}

	public void setCodeWarehouseSource(String codeWarehouseSource) {
		this.codeWarehouseSource = codeWarehouseSource;
	}

	public String getCodeWarehouseTarget() {
		return codeWarehouseTarget;
	}

	public void setCodeWarehouseTarget(String codeWarehouseTarget) {
		this.codeWarehouseTarget = codeWarehouseTarget;
	}

	public String getSerialElementLink() {
		return serialElementLink;
	}

	public void setSerialElementLink(String serialElementLink) {
		this.serialElementLink = serialElementLink;
	}

	public boolean isError() {
		return bError;
	}

	public void setError(boolean bError) {
		this.bError = bError;
	}

	public WarehouseElement getWarehouseElement() {
		return warehouseElement;
	}

	public void setWarehouseElement(WarehouseElement warehouseElement) {
		this.warehouseElement = warehouseElement;
	}

	public String getsError() {
		return sError;
	}

	public void setsError(String sError) {
		this.sError = sError;
	}

	public Warehouse getWarehouseTargetVO() {
		return warehouseTargetVO;
	}

	public void setWarehouseTargetVO(Warehouse warehouseTargetVO) {
		this.warehouseTargetVO = warehouseTargetVO;
	}
	
}
