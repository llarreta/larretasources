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
import co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDDealerBuildsLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.DealerBuildingDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDDealerBuilds", mappedName="ejb/CMDDealerBuilds")
public class CMDDealerBuilds  extends BaseCommand implements ICommand,CMDDealerBuildsLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private DealerBuldingFacadeBeanLocal dealerBuldingFacadeBeanLocal;
	
	public CMDDealerBuilds(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			List<DealerBuildingDTO> r = new ArrayList<DealerBuildingDTO>();
			HashMap<String,String> map = getParams(args);
			String strImportLogId = map.get("countryId"); 
			r = dealerBuldingFacadeBeanLocal.getDealerBuldingsByCountryAndActive(Long.valueOf(strImportLogId));
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
