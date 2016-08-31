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
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDNotSerializedElementInReferenceLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDNotSerializedElementInReference", mappedName="ejb/CMDNotSerializedElementInReference")
public class CMDNotSerializedElementInReference extends BaseCommand  implements ICommand,CMDNotSerializedElementInReferenceLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ReferenceFacadeBeanLocal referenceFacadeBeanLocal;
	
	public CMDNotSerializedElementInReference(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			NotSerializedElementInReferencePaginationResponse r;
			ReferenceVO ref;
			HashMap<String,String> map = getParams(args);
			
			Long referenceId = null;
			String strReferenceId = map.get("referenceId"); 
			if (strReferenceId != null && !strReferenceId.isEmpty())
				referenceId = Long.parseLong(strReferenceId);

			ref = referenceFacadeBeanLocal.getReferenceByID(referenceId);
			r =referenceFacadeBeanLocal.getNotSerializedElementInReference(ref, null);
			return (List<T>) r.getCollectionVO();
		} catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	private List<String> getDataList(NotSerializedElementInReferencePaginationResponse r){
		List<String> list = new ArrayList<String>();
		for(NotSerializedVO c : r.getCollectionVO()){
			list.add(c.toString());
		}
		return list;
	}

	@Override
	public List<String> getFieldList() {
		fieldList.add(ApplicationTextEnum.ID.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BATCH.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BRAND.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CLASS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.TYPE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.UNIT_MEASURE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SUPPLIER.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}
}
