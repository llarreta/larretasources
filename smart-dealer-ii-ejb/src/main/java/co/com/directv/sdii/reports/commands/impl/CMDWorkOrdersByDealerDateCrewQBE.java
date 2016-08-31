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
import co.com.directv.sdii.model.pojo.collection.WOByDealerDateCrewQBEPaginationResponse;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWorkOrdersByDealerDateCrewQBELocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWorkOrdersByDealerDateCrewQBE", mappedName="ejb/CMDWorkOrdersByDealerDateCrewQBE")
public class CMDWorkOrdersByDealerDateCrewQBE extends BaseCommand  implements ICommand,CMDWorkOrdersByDealerDateCrewQBELocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;

	public CMDWorkOrdersByDealerDateCrewQBE(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WOByDealerDateCrewQBEPaginationResponse r;
			HashMap<String,String> map = getParams(args);
			Long dealerId = map.get("dealerId") == null || map.get("dealerId").isEmpty() ? null : Long.parseLong(map.get("dealerId"));
			Long dateId = map.get("dateId") == null || map.get("dateId").isEmpty() ? null : Long.parseLong(map.get("dateId"));
			Long crewId = map.get("crewId") == null || map.get("crewId").isEmpty() ? null : Long.parseLong(map.get("crewId"));

			r = coreWOFacade.getWorkOrdersByDealerDateCrewQBE(dealerId, dateId, crewId, null);
			return (List<T>) r.getCollectionVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(WOByDealerDateCrewQBEPaginationResponse r){
		List<String> list = new ArrayList<String>();
		for(WoAssignmentVO c : r.getCollectionVO()){
			list.add(c.toXLSString());
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
