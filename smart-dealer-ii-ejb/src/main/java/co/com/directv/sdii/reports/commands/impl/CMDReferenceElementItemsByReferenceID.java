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
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDReferenceElementItemsByReferenceIDLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDReferenceElementItemsByReferenceID", mappedName="ejb/CMDReferenceElementItemsByReferenceID")
public class CMDReferenceElementItemsByReferenceID extends BaseCommand  implements ICommand,CMDReferenceElementItemsByReferenceIDLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ReferenceElementItemFacadeBeanLocal referenceElementItemFacadeBeanLocal;
	
	public CMDReferenceElementItemsByReferenceID(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ReferenceElementItemsResponse r;
			HashMap<String,String> map = getParams(args);
			Long referenceId = null;
			String strReferenceId = map.get("referenceId"); 
			if (strReferenceId != null && !strReferenceId.isEmpty())
				referenceId = Long.parseLong(strReferenceId);
			r = referenceElementItemFacadeBeanLocal.getReferenceElementItemsByReferenceID(referenceId, null);
			return (List<T>) r.getReferenceElementItemsVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}

	private List<String> getDataList(ReferenceElementItemsResponse r){
		List<String> list = new ArrayList<String>();
		for(ReferenceElementItemVO c : r.getReferenceElementItemsVO()){
			list.add(c.toString());
		}
		return list;
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
