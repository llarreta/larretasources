package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.WarehouseType;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;

public class WareheouseTypeResponse extends CollectionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3433542713020923695L;
	
	private List<WarehouseTypeVO> whTypeVO;
	private List<WarehouseType> whTypePojo;
	
	public List<WarehouseTypeVO> getWhTypeVO() {
		return whTypeVO;
	}
	public void setWhTypeVO(List<WarehouseTypeVO> whTypeVO) {
		this.whTypeVO = whTypeVO;
	}
	public List<WarehouseType> getWhTypePojo() {
		return whTypePojo;
	}
	public void setWhTypePojo(List<WarehouseType> whTypePojo) {
		this.whTypePojo = whTypePojo;
	}
	
	
	

}
