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
import co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllMovementTypesLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllMovementTypes", mappedName="ejb/CMDAllMovementTypes")
public class CMDAllMovementTypes  extends BaseCommand implements ICommand,CMDAllMovementTypesLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private MovementTypeFacadeBeanLocal movTypeFacadeBeanLocal;
	
	public CMDAllMovementTypes(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			MovementTypeResponse r;
			HashMap<String,String> map = getParams(args);
			String movTypeCode = map.get("movTypeCode") == null || map.get("movTypeCode").isEmpty() ? null : map.get("movTypeCode");
			r = movTypeFacadeBeanLocal.getMovementTypesAllStatusPage(movTypeCode, null);
			return (List<T>) r.getMovTypeVO();
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
