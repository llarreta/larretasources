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
import co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllMeasureUnitsLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllMeasureUnits", mappedName="ejb/CMDAllMeasureUnits")
public class CMDAllMeasureUnits  extends BaseCommand implements ICommand,CMDAllMeasureUnitsLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private MeasureUnitFacadeBeanLocal measureUFacadeBeanLocal;
	
	public CMDAllMeasureUnits(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			MeasureUnitResponse r;
			HashMap<String,String> map = getParams(args);
			String measureUCode = map.get("measureUCode") == null || map.get("measureUCode").isEmpty() ? null : map.get("measureUCode");
			r = measureUFacadeBeanLocal.getMeasureUnitsByAllStatusPage(measureUCode, null);
			return (List<T>) r.getMeasureUnitVO();
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
