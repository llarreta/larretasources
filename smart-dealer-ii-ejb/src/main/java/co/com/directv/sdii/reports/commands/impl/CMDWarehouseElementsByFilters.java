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
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehouseElementsByFiltersLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehouseElementsByFilters", mappedName="ejb/CMDWarehouseElementsByFilters")
public class CMDWarehouseElementsByFilters extends BaseCommand  implements ICommand,CMDWarehouseElementsByFiltersLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal; 

	public CMDWarehouseElementsByFilters(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WareHouseElementResponse r;
			HashMap<String,String> map = getParams(args);
			
			Long wareHouseId = map.get("wareHouseId") == null || map.get("wareHouseId").isEmpty() ? null : Long.parseLong(map.get("wareHouseId"));
			Long dealerId = map.get("dealerId") == null || map.get("dealerId").isEmpty() ? null : Long.parseLong(map.get("dealerId"));
			Long branchDealerId = map.get("branchDealerId") == null || map.get("branchDealerId").isEmpty() ? null : Long.parseLong(map.get("branchDealerId"));
			Long crewId = map.get("crewId") == null || map.get("crewId").isEmpty() ? null : Long.parseLong(map.get("crewId"));
			Long warehouseTypeId = map.get("warehouseTypeId") == null || map.get("warehouseTypeId").isEmpty() ? null : Long.parseLong(map.get("warehouseTypeId"));
			Long elementModelId = map.get("elementModelId") == null || map.get("elementModelId").isEmpty() ? null : Long.parseLong(map.get("elementModelId"));
			
			String elementTypeCode = map.get("elementTypeCode");
			r = warehouseElementFacadeBeanLocal.getWarehouseElementsByFilters(wareHouseId, dealerId, branchDealerId, crewId, warehouseTypeId, elementTypeCode, elementModelId, null);
			return (List<T>) r.getWareHouseElementsVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(WareHouseElementResponse r){
		List<String> list = new ArrayList<String>();
		for(WarehouseElementVO c : r.getWareHouseElementsVO()){
			list.add(c.toXLSString());
		}
		return list;
	}

	@Override
	public List<String> getFieldList() {
		fieldList.add(ApplicationTextEnum.ID.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.IRD.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SERIAL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BRAND.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CLASS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.TYPE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.REGISTRATION_DATE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.INITIAL_QUANTITY.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.ACTUAL_QUANTITY.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CAUSE_ADJUSTMENT.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.DATE_ENTRY.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.DEPARTURE_DATE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.MOVEMENT_TYPE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.STATE_REGISTRY.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}
}
