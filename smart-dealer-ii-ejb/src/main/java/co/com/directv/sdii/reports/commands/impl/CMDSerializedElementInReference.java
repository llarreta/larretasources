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
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDSerializedElementInReferenceLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDSerializedElementInReference", mappedName="ejb/CMDSerializedElementInReference")
public class CMDSerializedElementInReference extends BaseCommand  implements ICommand,CMDSerializedElementInReferenceLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ReferenceFacadeBeanLocal referenceFacadeBeanLocal; 
	
	public CMDSerializedElementInReference(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			SerializedElementInReferencePaginationResponse r;
			ReferenceVO ref;
			HashMap<String,String> map = getParams(args);
			
			Long referenceId = null;
			String strReferenceId = map.get("referenceId"); 
			if (strReferenceId != null && !strReferenceId.isEmpty())
				referenceId = Long.parseLong(strReferenceId);

			ref = referenceFacadeBeanLocal.getReferenceByID(referenceId);
			
			r = referenceFacadeBeanLocal.getSerializedElementInReference(ref, null);
			return (List<T>) r.getCollectionVO();
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	private List<String> getDataList(SerializedElementInReferencePaginationResponse r){
		List<String> list = new ArrayList<String>();
		for(SerializedVO c : r.getCollectionVO()){
			list.add(c.toXLSString());
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
		fieldList.add(ApplicationTextEnum.IRD.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SERIAL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.REGISTRATION_DATE.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}

}
