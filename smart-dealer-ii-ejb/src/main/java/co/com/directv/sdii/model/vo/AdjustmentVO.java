/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.Warehouse;

/**
 * Objeto que encapsula la información de un Adjustment
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Adjustment    
 */
public class AdjustmentVO extends Adjustment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 443270262898081201L;
	
	private Long warehouseSourceID;
	
	private Long warehouseTargetID;
	
	private String warehouseSourceWhCode;
	
	private String warehouseTargetWhCode;
	
	private String ibsCodeCustomerSource;
	
	private String ibsCodeCustomer;
	
	private WarehouseVO wareHouseSource;
	
	private WarehouseVO wareHouseTarget;
	
	public AdjustmentVO(){
		
	}
	
	public AdjustmentVO(Adjustment father, Long warehouseSourceID, Long warehouseTargetID, String warehouseSourceWhCode, String warehouseTargetWhCode,
			Warehouse warehouseSourceRef, Warehouse wareHouseTargetRef){
		setId(father.getId());
		setTransferReason(father.getTransferReason());
		setAdjustmentStatus(father.getAdjustmentStatus());
		setComent(father.getComent());
		setCreationUser(father.getCreationUser());
		setAuthorized(father.getAuthorized());
		setCreationDate(father.getCreationDate());
		setCountry(father.getCountry());
		setIsSerialized(father.getIsSerialized());
		this.warehouseSourceID=warehouseSourceID;
		this.warehouseTargetID=warehouseTargetID;
		this.warehouseSourceWhCode=warehouseSourceWhCode;
		this.warehouseTargetWhCode=warehouseTargetWhCode;
		try {
			wareHouseSource=UtilsBusiness.copyObject(WarehouseVO.class, warehouseSourceRef);
			wareHouseTarget=UtilsBusiness.copyObject(WarehouseVO.class, wareHouseTargetRef);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
	}

	//Usado para crear con multiplesdestinos
	public AdjustmentVO(Adjustment father, String warehouseTargetWhCode, Long warehouseTargetID){
		setId(father.getId());
		setTransferReason(father.getTransferReason());
		setAdjustmentStatus(father.getAdjustmentStatus());
		setComent(father.getComent());
		setCreationUser(father.getCreationUser());
		setAuthorized(father.getAuthorized());
		setCreationDate(father.getCreationDate());
		setCountry(father.getCountry());
		setIsSerialized(father.getIsSerialized());
		this.warehouseSourceID=0L;
		this.warehouseTargetID=warehouseTargetID;
		this.warehouseSourceWhCode="multiple";
		this.warehouseTargetWhCode=warehouseTargetWhCode;
		
	}
	
	//Usado para crear con multiplesdestinos y multiples origenes
	public AdjustmentVO(Adjustment father){
		setId(father.getId());
		setTransferReason(father.getTransferReason());
		setAdjustmentStatus(father.getAdjustmentStatus());
		setComent(father.getComent());
		setCreationUser(father.getCreationUser());
		setAuthorized(father.getAuthorized());
		setCreationDate(father.getCreationDate());
		setCountry(father.getCountry());
		setIsSerialized(father.getIsSerialized());
		this.warehouseSourceID=0L;
		this.warehouseTargetID=0L;
		this.warehouseSourceWhCode="multiple";
		this.warehouseTargetWhCode="multiple";
		
	}
	
	//Usado para crear con multiplesdestinos
	public AdjustmentVO(Adjustment father, Long warehouseSourceID, String warehouseSourceWhCode ){
		setId(father.getId());
		setTransferReason(father.getTransferReason());
		setAdjustmentStatus(father.getAdjustmentStatus());
		setComent(father.getComent());
		setCreationUser(father.getCreationUser());
		setAuthorized(father.getAuthorized());
		setCreationDate(father.getCreationDate());
		setCountry(father.getCountry());
		setIsSerialized(father.getIsSerialized());
		this.warehouseSourceID=warehouseSourceID;
		this.warehouseTargetID=0L;
		this.warehouseSourceWhCode=warehouseSourceWhCode;
		this.warehouseTargetWhCode="multiple";
		
	}
	
	public Long getWarehouseSourceID() {
		return warehouseSourceID;
	}

	public void setWarehouseSourceID(Long warehouseSourceID) {
		this.warehouseSourceID = warehouseSourceID;
	}

	public Long getWarehouseTargetID() {
		return warehouseTargetID;
	}

	public void setWarehouseTargetID(Long warehouseTargetID) {
		this.warehouseTargetID = warehouseTargetID;
	}

	public String getIbsCodeCustomer() {
		return ibsCodeCustomer;
	}

	public void setIbsCodeCustomer(String ibsCodeCustomer) {
		this.ibsCodeCustomer = ibsCodeCustomer;
	}

	public String getWarehouseSourceWhCode() {
		return warehouseSourceWhCode;
	}

	public void setWarehouseSourceWhCode(String warehouseSourceWhCode) {
		this.warehouseSourceWhCode = warehouseSourceWhCode;
	}

	public String getWarehouseTargetWhCode() {
		return warehouseTargetWhCode;
	}

	public void setWarehouseTargetWhCode(String warehouseTargetWhCode) {
		this.warehouseTargetWhCode = warehouseTargetWhCode;
	}

	public String getIbsCodeCustomerSource() {
		return ibsCodeCustomerSource;
	}

	public void setIbsCodeCustomerSource(String ibsCodeCustomerSource) {
		this.ibsCodeCustomerSource = ibsCodeCustomerSource;
	}

	public WarehouseVO getWareHouseSource() {
		return wareHouseSource;
	}

	public void setWareHouseSource(WarehouseVO wareHouseSource) {
		this.wareHouseSource = wareHouseSource;
	}

	public WarehouseVO getWareHouseTarget() {
		return wareHouseTarget;
	}

	public void setWareHouseTarget(WarehouseVO wareHouseTarget) {
		this.wareHouseTarget = wareHouseTarget;
	}
	
}
