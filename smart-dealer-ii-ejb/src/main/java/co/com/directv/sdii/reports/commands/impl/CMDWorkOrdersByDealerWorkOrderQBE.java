/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.model.pojo.collection.WOByDealerWorkOrderQBEPaginationResponse;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWorkOrdersByDealerWorkOrderQBELocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWorkOrdersByDealerWorkOrderQBE", mappedName="ejb/CMDWorkOrdersByDealerWorkOrderQBE")
public class CMDWorkOrdersByDealerWorkOrderQBE extends BaseCommand  implements ICommand,CMDWorkOrdersByDealerWorkOrderQBELocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;

	public CMDWorkOrdersByDealerWorkOrderQBE(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WOByDealerWorkOrderQBEPaginationResponse r;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String,String> map = getParams(args);
			Long dealerId = map.get("dealerId") == null || map.get("dealerId").isEmpty() ? null : Long.parseLong(map.get("dealerId"));
			String woCode = map.get("woCode");
			Long ServiceTypeId = map.get("ServiceTypeId") == null || map.get("ServiceTypeId").isEmpty() ? null : Long.parseLong(map.get("ServiceTypeId"));
			Long serviceCategoryId = map.get("serviceCategoryId") == null || map.get("serviceCategoryId").isEmpty() ? null : Long.parseLong(map.get("serviceCategoryId"));
			Long woStatusId = map.get("woStatusId") == null || map.get("woStatusId").isEmpty() ? null : Long.parseLong(map.get("woStatusId"));
			Long stateId = map.get("stateId") == null || map.get("stateId").isEmpty() ? null : Long.parseLong(map.get("stateId"));
			Long cityId = map.get("cityId") == null || map.get("cityId").isEmpty() ? null : Long.parseLong(map.get("cityId"));
			Long postalCodeId = map.get("postalCodeId") == null || map.get("postalCodeId").isEmpty() ? null : Long.parseLong(map.get("postalCodeId"));
			Date creationDate = null;
			Date programmingDate = null;
			
			try {
				creationDate = map.get("creationDate") == null || map.get("creationDate").isEmpty() ? null : df.parse(map.get("creationDate"));
				programmingDate = map.get("programmingDate") == null || map.get("programmingDate").isEmpty() ? null : df.parse(map.get("programmingDate"));
			} catch (Exception e) {
				throw new BusinessException("== Error convirtiendo la Fecha ==", e);
			}

			r = coreWOFacade.getWorkOrdersByDealerWorkOrderQBE(dealerId, woCode, ServiceTypeId, serviceCategoryId, woStatusId, stateId, cityId, postalCodeId, creationDate, programmingDate, null);
			return (List<T>) r.getCollectionVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(WOByDealerWorkOrderQBEPaginationResponse r){
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
