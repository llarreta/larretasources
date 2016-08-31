package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.vo.WarehouseElementVO;

public class WareHouseElementResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834345624597584227L;
	
	private List<WarehouseElement> wareHouseElements;
	private List<WarehouseElementVO> wareHouseElementsVO;
	
	/**Lista utilizada para la consulta de remisiones ya que solo se pueden devovler List<Object[]>**/
	private List<Object[]> warehouseElementObjects;
	
	public List<WarehouseElement> getWareHouseElements() {
		return wareHouseElements;
	}
	public void setWareHouseElements(List<WarehouseElement> wareHouseElements) {
		this.wareHouseElements = wareHouseElements;
	}
	public List<WarehouseElementVO> getWareHouseElementsVO() {
		return wareHouseElementsVO;
	}
	public void setWareHouseElementsVO(List<WarehouseElementVO> wareHouseElementsVO) {
		this.wareHouseElementsVO = wareHouseElementsVO;
	}
	public List<Object[]> getWarehouseElementObjects() {
		return warehouseElementObjects;
	}
	@XmlTransient
	public void setWarehouseElementObjects(List<Object[]> warehouseElementObjects) {
		this.warehouseElementObjects = warehouseElementObjects;
	}
	

}
