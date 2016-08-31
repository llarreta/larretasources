package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Representa el objeto a enviar entre el cliente del web service y el server para encapsular
 * los datos del movimiento masivo entre bodegas
 * 
 * Fecha de Creacion: Aug 6, 2010
 * @author garciniegas <a href="mailto:garciniegas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */

public class MassiveMovementBetweenWareHouseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8470287988762362791L;
	private WarehouseVO wareHouseSource;
	private WarehouseVO wareHouseTarget;
	private List<ElementVO> listObjectToMove;
	
	private Reference reference;
	private boolean validateElementsToConfirm;
	private Long userId;
	private String isSerialized;
	private String processCodeIBS;
	
	public MassiveMovementBetweenWareHouseDTO() {
		
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

	public List<ElementVO> getListObjectToMove() {
		return listObjectToMove;
	}

	public void setListObjectToMove(List<ElementVO> listObjectToMove) {
		this.listObjectToMove = listObjectToMove;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public boolean isValidateElementsToConfirm() {
		return validateElementsToConfirm;
	}

	public void setValidateElementsToConfirm(boolean validateElementsToConfirm) {
		this.validateElementsToConfirm = validateElementsToConfirm;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIsSerialized() {
		return isSerialized;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}

	public String getProcessCodeIBS() {
		return processCodeIBS;
	}

	public void setProcessCodeIBS(String processCodeIBS) {
		this.processCodeIBS = processCodeIBS;
	}
	
	

}
