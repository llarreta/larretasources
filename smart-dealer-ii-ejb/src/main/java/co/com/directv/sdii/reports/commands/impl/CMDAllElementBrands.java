package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementBrandFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllElementBrandsLocal;
import co.com.directv.sdii.reports.commands.ICommand;

@Stateless(name="CMDAllElementBrands")
public class CMDAllElementBrands extends BaseCommand implements ICommand,CMDAllElementBrandsLocal{
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementBrandFacadeBeanLocal elementBrandFacadeBeanLocal;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementBrandResponse r;
			HashMap<String,String> map = getParams(args);
			String elementBrandCode = map.get("elementBrandCode") == null || map.get("elementBrandCode").isEmpty() ? null : map.get("elementBrandCode");
			r = elementBrandFacadeBeanLocal.getElementBrandByAllStatuPage(elementBrandCode,null);
			return (List<T>) r.getElementBrandVO();
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
