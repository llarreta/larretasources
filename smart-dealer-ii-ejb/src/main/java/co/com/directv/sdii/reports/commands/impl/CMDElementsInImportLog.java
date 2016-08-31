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
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDElementsInImportLogLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ElementInImportLogDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDElementsInImportLog", mappedName="ejb/CMDElementsInImportLog")
public class CMDElementsInImportLog extends BaseCommand  implements ICommand,CMDElementsInImportLogLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	public CMDElementsInImportLog(){
		
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ImportLogItemResponse r;
			HashMap<String,String> map = getParams(args);
			Long importLogId = null;
			String strImportLogId = map.get("importLogId"); 
			if (strImportLogId != null && !strImportLogId.isEmpty())
				importLogId = Long.parseLong(strImportLogId);

			Boolean serialized = null;
			
			String strSerialized = map.get("serialized"); 
			if (strSerialized != null && !strSerialized.isEmpty())
				serialized = Boolean.parseBoolean(strSerialized);


			r = importLogFacadeBeanLocal.getElementsInImportLog(importLogId, serialized, null);
			
			List<ElementInImportLogDTO> response = new ArrayList<ElementInImportLogDTO>();
			
			if(r.getImportLogItemsVO() != null){
				for( ImportLogItemVO vo : r.getImportLogItemsVO() ){
					ElementInImportLogDTO dto = new ElementInImportLogDTO();
					dto.setElementCode( vo.getId() != null ? vo.getId() : null);
					dto.setAmountExpected( vo.getAmountExpected() != null ? vo.getAmountExpected() : 0D );
					dto.setConfirmedQuantity( vo.getConfirmedQuantity() != null ? vo.getConfirmedQuantity() : 0D);
					dto.setMeasureUnitName( ( vo.getMeasureUnit() != null && vo.getMeasureUnit().getUnitName() != null ) ? vo.getMeasureUnit().getUnitName() : null ) ;
					response.add(dto);
				}
			}
			
			return (List<T>) response;
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
