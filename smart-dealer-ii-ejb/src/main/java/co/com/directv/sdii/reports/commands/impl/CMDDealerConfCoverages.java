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
import co.com.directv.sdii.facade.assign.DealerConfCoverageFacadeBeanLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDDealerConfCoveragesLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;

/**
 * @author ssanabri
 *
 */
@Stateless(name="CMDDealerConfCoverages", mappedName="ejb/CMDDealerConfCoverages")
public class CMDDealerConfCoverages  extends BaseCommand implements ICommand,CMDDealerConfCoveragesLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private DealerConfCoverageFacadeBeanLocal dealerConfCoverageFacadeBeanLocal;
	
	public CMDDealerConfCoverages(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			List<DealerConfCoverageDTO> r = new ArrayList<DealerConfCoverageDTO>();
			HashMap<String,String> map = getParams(args);
			String strCountryId = map.get("countryId"); 
			r = dealerConfCoverageFacadeBeanLocal.getAllDealerConfCoverageByCountryAndActiveStatus(Long.valueOf(strCountryId));
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
