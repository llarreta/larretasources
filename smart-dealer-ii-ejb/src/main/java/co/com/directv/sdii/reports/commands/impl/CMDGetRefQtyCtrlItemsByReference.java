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
import co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetRefQtyCtrlItemsByReferenceLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDGetRefQtyCtrlItemsByReference", mappedName="ejb/CMDGetRefQtyCtrlItemsByReference")
public class CMDGetRefQtyCtrlItemsByReference extends BaseCommand implements ICommand,CMDGetRefQtyCtrlItemsByReferenceLocal{

	public CMDGetRefQtyCtrlItemsByReference(){
	}
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private RefQuantityControlItemFacadeBeanLocal ejbRefQtyCtrl;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			HashMap<String,String> map = getParams(args);
			Long refId = null;
			String strRefId = map.get("refId"); 
			if (strRefId != null && !strRefId.isEmpty())
				refId = Long.parseLong(strRefId);
			RefQuantityControlItemVO obj = new RefQuantityControlItemVO();
			obj.setIdReference( refId );
			RefQuantityControlItemsResponse response = ejbRefQtyCtrl.getRefQtyCtrlItemsByReference(obj,null);
			if( response != null && response.getRefQuantityControlItemsVO() != null ) {
				return (List<T>) response.getRefQuantityControlItemsVO();
			}
			return new ArrayList<T>();
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
