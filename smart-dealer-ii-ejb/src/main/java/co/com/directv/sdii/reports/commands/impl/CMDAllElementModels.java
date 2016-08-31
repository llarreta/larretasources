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
import co.com.directv.sdii.facade.stock.ElementModelFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllElementModelsLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllElementModels", mappedName="ejb/CMDAllElementModels")
public class CMDAllElementModels  extends BaseCommand implements ICommand,CMDAllElementModelsLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementModelFacadeBeanLocal elementModelFacadeBeanLocal;
	
	public CMDAllElementModels(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementModelResponse r;
			HashMap<String,String> map = getParams(args);
			String elementModelCode = map.get("elementModelCode") == null || map.get("elementModelCode").isEmpty() ? null : map.get("elementModelCode");
			Long idElementClass = map.get("idElementClass") == null || map.get("idElementClass").isEmpty() ? -1 : Long.valueOf(map.get("idElementClass"));
			r = elementModelFacadeBeanLocal.getElementModelsByElementClassIdAndAllStatusPage(idElementClass, elementModelCode, null);
			return (List<T>) r.getElementModelVO();
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
