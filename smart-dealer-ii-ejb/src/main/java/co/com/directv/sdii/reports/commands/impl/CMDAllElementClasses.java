package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementClassFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllElementClassesLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(name="CMDAllElementClasses", mappedName="ejb/CMDAllElementClasses")
public class CMDAllElementClasses extends BaseCommand implements ICommand,CMDAllElementClassesLocal{
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementClassFacadeBeanLocal elementClassFacadeBeanLocal;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementClassResponse r;
			HashMap<String,String> map = getParams(args);
			String elementClassCode = map.get("elementClassCode") == null || map.get("elementClassCode").isEmpty() ? null : map.get("elementClassCode");
			r = elementClassFacadeBeanLocal.getElementClassByAllStatusPage(elementClassCode, null);
			return (List<T>) r.getElementClassVO();
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
