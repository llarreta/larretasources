package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.model.pojo.ServiceType;

/**
 * 
 * ServiceType Value Object
 * 
 * Fecha de Creaci�n: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceType
 */
public class ServiceTypeVO extends ServiceType implements Serializable {

	private static final long serialVersionUID = -8941455025822014490L;

	private List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc = null; //no lo usa para el respose "getDealerServiceSubCategoriesTreeResponse"
	
	private List<DealerServiceSubCategoryVO> dealerServiceSubCategories = null;

	public List<DealerServiceSubCategoryWithPcVO> getDealerServiceSubCategoriesWithPc() {
		return dealerServiceSubCategoriesWithPc;
	}

	public void setDealerServiceSubCategoriesWithPc(
			List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc) {
		this.dealerServiceSubCategoriesWithPc = dealerServiceSubCategoriesWithPc;
	}

	public List<DealerServiceSubCategoryVO> getDealerServiceSubCategories() {
		if (dealerServiceSubCategories == null){
			dealerServiceSubCategories = new ArrayList<DealerServiceSubCategoryVO>();
		}
		return this.dealerServiceSubCategories;
	}
	


	public void setDealerServiceSubCategories(
			List<DealerServiceSubCategoryVO> dealerServiceSubCategories) {
		this.dealerServiceSubCategories = dealerServiceSubCategories;
	}

}
