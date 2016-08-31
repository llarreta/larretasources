/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.model.pojo.collection.WOActiveAndSuspendByCountryIdPaginationResponse;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWorkOrdersActiveAndSuspendByCountryIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWorkOrdersActiveAndSuspendByCountryId", mappedName="ejb/CMDWorkOrdersActiveAndSuspendByCountryId")
public class CMDWorkOrdersActiveAndSuspendByCountryId  extends BaseCommand implements ICommand,CMDWorkOrdersActiveAndSuspendByCountryIdLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;

	public CMDWorkOrdersActiveAndSuspendByCountryId(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WOActiveAndSuspendByCountryIdPaginationResponse r;
			HashMap<String,String> map = getParams(args);
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			r = coreWOFacade.getWorkOrdersActiveAndSuspendByCountryId(countryId, null);
			return (List<T>) r.getCollectionVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(WOActiveAndSuspendByCountryIdPaginationResponse r){
		List<String> list = new ArrayList<String>();
		for(WorkOrderVO c : r.getCollectionVO()){
			list.add(c.toString());
		}
		return list;
	}

	@Override
	public List<String> getFieldList() {
		fieldList.add(ApplicationTextEnum.WO_CODE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.WO_STATUS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SCHEDULE_DATE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CLIENT_IBS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.NAME.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SURNAMES.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CREW.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}
}
