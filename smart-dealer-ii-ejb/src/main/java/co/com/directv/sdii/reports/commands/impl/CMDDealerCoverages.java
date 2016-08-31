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
import co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDDealerCoveragesLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.DealerCoverageDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDDealerCoverages", mappedName="ejb/CMDDealerCoverages")
public class CMDDealerCoverages  extends BaseCommand implements ICommand,CMDDealerCoveragesLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private DealerCoverageFacadeBeanLocal dealerCoverageFacadeBeanLocal;
	
	public CMDDealerCoverages(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			List<DealerCoverageDTO> r = new ArrayList<DealerCoverageDTO>();
			HashMap<String,String> map = getParams(args);
			String strCountryId = map.get("countryId"); 
			r = dealerCoverageFacadeBeanLocal.getAllDealerCoverageByCountryAndActiveStatus(Long.valueOf(strCountryId));
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
