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
import co.com.directv.sdii.model.pojo.collection.WOByCustomerQBEPaginationResponse;
import co.com.directv.sdii.model.vo.WoAssignmentVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWorkOrdersByCustomerQBELocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWorkOrdersByCustomerQBE", mappedName="ejb/CMDWorkOrdersByCustomerQBE")
public class CMDWorkOrdersByCustomerQBE  extends BaseCommand implements ICommand,CMDWorkOrdersByCustomerQBELocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private CoreWOFacadeLocal coreWOFacade;

	public CMDWorkOrdersByCustomerQBE(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WOByCustomerQBEPaginationResponse r;
			HashMap<String,String> map = getParams(args);

			Long dealerId = null;
			String strDealerId = map.get("dealerId"); 
			if (strDealerId != null && !strDealerId.isEmpty())
				dealerId = Long.parseLong(strDealerId);

			String ibsCode = map.get("ibsCode");
			String officePhone = map.get("officePhone");
			String homePhone = map.get("homePhone");
			String faxPhone = map.get("faxPhone");
			String cellPhone = map.get("cellPhone");
			String idNumber = map.get("idNumber");
			String name = map.get("name");
			String lastName = map.get("lastName");
			r = coreWOFacade.getWorkOrdersByDealerCustomerQBE(dealerId, ibsCode, officePhone, homePhone, faxPhone, cellPhone, idNumber, name, lastName, null);
			return (List<T>) r.getCollectionVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(WOByCustomerQBEPaginationResponse r){
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
