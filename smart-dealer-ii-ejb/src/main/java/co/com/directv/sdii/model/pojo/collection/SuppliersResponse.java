package co.com.directv.sdii.model.pojo.collection;

import java.io.Serializable;
import java.util.List;

import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.vo.SupplierVO;

public class SuppliersResponse extends CollectionBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821949264290448704L;

	private List<Supplier> suppliers;
	private List<SupplierVO> suppliersVO;
	
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	public List<SupplierVO> getSuppliersVO() {
		return suppliersVO;
	}
	public void setSuppliersVO(List<SupplierVO> suppliersVO) {
		this.suppliersVO = suppliersVO;
	}	
}
