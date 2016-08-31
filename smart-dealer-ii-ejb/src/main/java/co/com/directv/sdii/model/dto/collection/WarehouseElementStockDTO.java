package co.com.directv.sdii.model.dto.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.collection.CollectionBase;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;

/**
 * 
 * Transporta WarehouseElemetStock entre la capa de Business
 * y de Servicios.
 * 
 * Fecha de Creación: 2/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WarehouseElementStockDTO extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 936659678724287414L;

	private List<WarehouseElementStockVO> whElementsStock;

	public List<WarehouseElementStockVO> getWhElementsStock() {
		return whElementsStock;
	}

	public void setWhElementsStock(List<WarehouseElementStockVO> whElementsStock) {
		this.whElementsStock = whElementsStock;
	}
	
	
}
