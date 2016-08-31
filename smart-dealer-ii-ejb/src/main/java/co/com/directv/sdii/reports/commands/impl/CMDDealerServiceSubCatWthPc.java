/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDDealerServiceSubCatWthPcLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.DealerServiceSubCategoryWithPcDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDDealerServiceSubCatWthPc", mappedName="ejb/CMDDealerServiceSubCatWthPc")
public class CMDDealerServiceSubCatWthPc  extends BaseCommand implements ICommand,CMDDealerServiceSubCatWthPcLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private DealerServiceSubCategoryWithPcFacadeBeanLocal dealerServiceSubCategoryWithPcFacadeBeanLocal;
	
	public CMDDealerServiceSubCatWthPc(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			List<DealerServiceSubCategoryWithPcDTO> r = new ArrayList<DealerServiceSubCategoryWithPcDTO>();
			HashMap<String,String> map = getParams(args);
			String strImportLogId = map.get("countryId"); 
			r = dealerServiceSubCategoryWithPcFacadeBeanLocal.getDealerServSubCatPcByCountryAndActive(Long.valueOf(strImportLogId));
			return (List<T>) r;
		} catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	@Override
	public List<String> getFieldList() {
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}
}
