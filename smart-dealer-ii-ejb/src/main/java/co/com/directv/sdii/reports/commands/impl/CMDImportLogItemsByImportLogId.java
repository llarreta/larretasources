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
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImportLogItemsByImportLogIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogItemDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDImportLogItemsByImportLogId", mappedName="ejb/CMDImportLogItemsByImportLogId")
public class CMDImportLogItemsByImportLogId extends BaseCommand  implements ICommand,CMDImportLogItemsByImportLogIdLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogItemFacadeBeanLocal importLogItemFacadeBeanLocal;
	
	public CMDImportLogItemsByImportLogId(){
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
			r = importLogItemFacadeBeanLocal.getImportLogItemsByImportLogId(importLogId, null);
			List<ImportLogItemDTO> response = new ArrayList<ImportLogItemDTO>();
			if( r.getImportLogItemsVO() != null ){
				for(ImportLogItemVO vo : r.getImportLogItemsVO()){
					ImportLogItemDTO dto = new ImportLogItemDTO();
					dto.setAmountExpected( vo.getAmountExpected() != null ? vo.getAmountExpected() : 0D );
					dto.setConfirmedQuantity( vo.getConfirmedQuantity() != null ? vo.getConfirmedQuantity() : 0D ) ;
					dto.setElementTypeName( ( vo.getElement() != null && vo.getElement().getElementType() != null ) ? vo.getElement().getElementType().getTypeElementName() : "" );
					response.add( dto );
				}
			}
			return (List<T>)response ;
		} catch (Throwable e){
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
