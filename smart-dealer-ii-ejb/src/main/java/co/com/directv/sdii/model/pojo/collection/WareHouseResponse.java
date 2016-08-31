package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Transporta Warehouse entre la capa de DAO, Business y Services. 
 * 
 * Fecha de Creación: 4/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WareHouseResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655965608586718690L;

	private List<Warehouse> wareHouse;
	private List<WarehouseVO> wareHouseVO;

	public List<Warehouse> getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(List<Warehouse> wareHouse) {
		this.wareHouse = wareHouse;
	}

	public List<WarehouseVO> getWareHouseVO() {
		return wareHouseVO;
	}

	public void setWareHouseVO(List<WarehouseVO> wareHouseVO) {
		this.wareHouseVO = wareHouseVO;
	}	
}
