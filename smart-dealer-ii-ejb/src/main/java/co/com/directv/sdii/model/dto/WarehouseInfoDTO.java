package co.com.directv.sdii.model.dto;

import java.io.Serializable;


/**
 * DTo que encapsula las ubicaciones de dealers instaladores
 * @author clopez
 *
 */

public class WarehouseInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String warehouseCode;
	private String warehouseName;

	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
}
