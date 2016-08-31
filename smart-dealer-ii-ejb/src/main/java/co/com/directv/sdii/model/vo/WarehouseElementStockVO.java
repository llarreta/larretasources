/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.WarehouseElementStock;

/**
 * Objeto que encapsula la información de un WarehouseElementStock
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseElementStock    
 */
public class WarehouseElementStockVO extends WarehouseElementStock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373146728267705551L;
	
	private String wareHouseName;
	private String dealerDepotPlusName;
	private String dealerBranchDepotPlusName;
	
	/**
	 * Getter & Setters
	 */
	
	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}



	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getElementType() == null ? "" : getElementType().getTypeElementName());
		buff.append("|");
		buff.append(getMaxQuantity() == null ? "" : getMaxQuantity());
		buff.append("|");
		buff.append(getMinQuantity() == null ? "" : getMinQuantity());
		buff.append("|");
		buff.append(getReorderQuantity() == null ? "" : getReorderQuantity());
		buff.append("|");
		buff.append(getWarehouse() == null ? "" : getWarehouse().getWhCode());
		buff.append("|");
		buff.append(getComments() == null ? "" : getComments());
		return buff.toString();
	}

	public String getDealerDepotPlusName() {
		return dealerDepotPlusName;
	}

	public void setDealerDepotPlusName(String dealerDepotPlusName) {
		this.dealerDepotPlusName = dealerDepotPlusName;
	}

	public String getDealerBranchDepotPlusName() {
		return dealerBranchDepotPlusName;
	}

	public void setDealerBranchDepotPlusName(String dealerBranchDepotPlusName) {
		this.dealerBranchDepotPlusName = dealerBranchDepotPlusName;
	}
	
}
