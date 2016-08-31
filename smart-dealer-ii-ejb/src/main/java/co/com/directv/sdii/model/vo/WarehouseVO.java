/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Warehouse;

/**
 * Objeto que encapsula la información de un Warehouse
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Warehouse    
 */
public class WarehouseVO extends Warehouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8832280856939718854L;
	
	//gfandino-01/06/2011 Se adiciona por la forma como se muestra el nombre de la bodega INV104 v2.2
	//Código Depot + Nombre compañía principal o sucursal + Nombre responsable de la Cuadrilla (si aplica)+Tipo de Bodega.
	private String warehouseName;
	
	private String crewResponsable;
	
	private String dealerDepotPlusName;
	private String dealerBranchDepotPlusName;
	
	public String getWarehouseName() {
		return warehouseName;
	}


	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public void setCrewResponsable(String crewResponsable) {
		this.crewResponsable = crewResponsable;
	}


	public String getCrewResponsable() {
		return crewResponsable;
	}


	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getWhCode());
		buff.append("|");
		buff.append(getWarehouseType() == null ? "" : getWarehouseType().getWhTypeName());
		buff.append("|");
		buff.append(getWhResponsible() == null ? "" : getWhResponsible());
		buff.append("|");
		buff.append(getResponsibleEmail() == null ? "" : getResponsibleEmail());
		buff.append("|");
		buff.append(getWhAddress() == null ? "" : getWhAddress());
		buff.append("|");
		buff.append(getCountry() == null ? "" : getCountry().getCountryName());
		buff.append("|");
		buff.append(getDealerId() == null ? "" : getDealerId().getDealerCode());
		buff.append("|");
		buff.append(getCrewId() == null ? "" : getCrewId().getId());
		buff.append("|");
		buff.append(getCustomerId() == null ? "" : getCustomerId().getCustomerCode());
		return buff.toString();
	}

	/**
	 * @return the dealerDepotPlusName
	 */
	public String getDealerDepotPlusName() {
		return dealerDepotPlusName;
	}

	/**
	 * @param dealerDepotPlusName the dealerDepotPlusName to set
	 */
	public void setDealerDepotPlusName(String dealerDepotPlusName) {
		this.dealerDepotPlusName = dealerDepotPlusName;
	}
	
	/**
	 * @return the dealerBranchDepotPlusName
	 */
	public String getDealerBranchDepotPlusName() {
		return dealerBranchDepotPlusName;
	}
	
	/**
	 * @param dealerBranchDepotPlusName the dealerBranchDepotPlusName to set
	 */
	public void setDealerBranchDepotPlusName(String dealerBranchDepotPlusName) {
		this.dealerBranchDepotPlusName = dealerBranchDepotPlusName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
