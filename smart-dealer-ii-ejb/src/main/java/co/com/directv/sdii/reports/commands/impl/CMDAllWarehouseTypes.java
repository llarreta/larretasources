package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllWarehouseTypesLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(name="CMDAllWarehouseTypes", mappedName="ejb/CMDAllWarehouseTypes")
public class CMDAllWarehouseTypes extends BaseCommand implements ICommand,CMDAllWarehouseTypesLocal{
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseTypeFacadeBeanLocal whTypeFacadeBeanLocal;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WareheouseTypeResponse r;
			HashMap<String,String> map = getParams(args);
			String whTypeCode = map.get("whTypeCode") == null || map.get("whTypeCode").isEmpty() ? null : map.get("whTypeCode");
			r = whTypeFacadeBeanLocal.getAllWarehouseTypes(whTypeCode,null);
			return (List<T>) r.getWhTypeVO();
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
