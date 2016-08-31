package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.WarehouseElementStock;

/**
 * 
 * Transporta WarehouseElemetStock entre los componentes
 * DAO y Business.
 * 
 * Fecha de Creación: 2/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class WarehouseElemetStockResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6453220939949116216L;
	
	private List<WarehouseElementStock> whElementsStock;

	public List<WarehouseElementStock> getWhElementsStock() {
		return whElementsStock;
	}

	public void setWhElementsStock(List<WarehouseElementStock> whElementsStock) {
		this.whElementsStock = whElementsStock;
	}
}
