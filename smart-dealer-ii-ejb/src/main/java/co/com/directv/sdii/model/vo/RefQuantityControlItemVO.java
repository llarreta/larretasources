package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.RefQuantityControlItem;

public class RefQuantityControlItemVO extends RefQuantityControlItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3556954159432301498L;
	
	private Long idReference;
	private String whSourceName;
	private String whTargetName;
	private String isPrepaidRef;

	public Long getIdReference() {
		return idReference;
	}

	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	public String getWhSourceName() {
		return whSourceName;
	}

	public void setWhSourceName(String whSourceName) {
		this.whSourceName = whSourceName;
	}

	public String getWhTargetName() {
		return whTargetName;
	}

	public void setWhTargetName(String whTargetName) {
		this.whTargetName = whTargetName;
	}

	public String getIsPrepaidRef() {
		return isPrepaidRef;
	}

	public void setIsPrepaidRef(String isPrepaidRef) {
		this.isPrepaidRef = isPrepaidRef;
	}
	
	
	
	
	
	
	

}
