package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.SerializedFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImportLogItemsByImportLogIdAndIsSerializedLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogItemDTO;



@Stateless(name="CMDImportLogItemsByImportLogIdAndIsSerialized", mappedName="ejb/CMDImportLogItemsByImportLogIdAndIsSerialized")
public class CMDImportLogItemsByImportLogIdAndIsSerialized extends BaseCommand  implements ICommand,CMDImportLogItemsByImportLogIdAndIsSerializedLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogItemFacadeBeanLocal importLogItemFacadeBeanLocal;
	
	@EJB
	private SerializedFacadeBeanLocal serializedFacadeBeanLocal;
	
	public CMDImportLogItemsByImportLogIdAndIsSerialized(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	//Modificado para Requerimiento: CC057
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ImportLogItemResponse r;
			HashMap<String,String> map = getParams(args);
			Long importLogId = null;
			boolean isSerialized = false;
			String strImportLogId = map.get("importLogId");
			String strIsSerialized = map.get("isSerialized");
			String serialCode = map.get("serialCode");
			if (strImportLogId != null && !strImportLogId.isEmpty())
				importLogId = Long.parseLong(strImportLogId);
			if (strIsSerialized != null && !strIsSerialized.isEmpty())
				isSerialized = strIsSerialized.toString().equals("true");
			String strStatusCode = map.get("statusCode");
			ImportLogElementsFilterDTO filter = new ImportLogElementsFilterDTO();
			filter.setImportLogID(importLogId);
			filter.setSerialized(isSerialized);
			filter.setSerialCode(serialCode);
			filter.setItemStatus(strStatusCode);
			r = importLogItemFacadeBeanLocal.getImportLogItemsByImportLogIdAndIsSerialized(filter, null);
			List<ImportLogItemDTO> response = new ArrayList<ImportLogItemDTO>();
			if( r.getImportLogItemsVO() != null ){
				for(ImportLogItemVO vo : r.getImportLogItemsVO()){
					ImportLogItemDTO dto = new ImportLogItemDTO();
					Serialized ser = null;
					if (vo.getElement().getIsSerialized().equals(ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue())){
						 ser = serializedFacadeBeanLocal.getSerializedByID(vo.getElement().getId());
						
					}
					dto.setAmountExpected( vo.getAmountExpected() != null ? vo.getAmountExpected() : 0D );
					dto.setConfirmedQuantity( vo.getConfirmedQuantity() != null ? vo.getConfirmedQuantity() : 0D ) ;
					dto.setElementTypeName( ( vo.getElement() != null && vo.getElement().getElementType() != null ) ? vo.getElement().getElementType().getTypeElementName() : "" );
					dto.setSerial(ser != null && ser.getSerialCode() != null ? ser.getSerialCode() : ""  );
					dto.setSerialLinked(ser != null && ser.getSerialized() != null && ser.getSerialized().getSerialCode() != null ? ser.getSerialized().getSerialCode() : "" );
					dto.setElementStatus(vo.getItemStatus() != null && vo.getItemStatus().getStatusName() != null ? vo.getItemStatus().getStatusName() : "");
					dto.setMeasureUnit(vo.getElement() != null && vo.getElement().getElementType() != null && vo.getElement().getElementType().getMeasureUnit() != null && vo.getElement().getElementType().getMeasureUnit().getUnitName() != null ? vo.getElement().getElementType().getMeasureUnit().getUnitName() : "" );
					dto.setElementTypeCode((vo.getElement() != null && vo.getElement().getElementType() != null ) ? vo.getElement().getElementType().getTypeElementCode() : "" );
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
