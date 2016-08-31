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
import co.com.directv.sdii.facade.assign.DealerDetailFacadeBeanLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDDealerDetailsLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.DealerDetailDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDDealerDetails", mappedName="ejb/CMDDealerDetails")
public class CMDDealerDetails  extends BaseCommand implements ICommand,CMDDealerDetailsLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private DealerDetailFacadeBeanLocal dealerDetailFacadeBeanLocal;
	
	public CMDDealerDetails(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			List<DealerDetailDTO> r = new ArrayList<DealerDetailDTO>();
			HashMap<String,String> map = getParams(args);
			String strImportLogId = map.get("countryId"); 
			r = dealerDetailFacadeBeanLocal.getAllDealerDetailsByCountry(Long.valueOf(strImportLogId));
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
