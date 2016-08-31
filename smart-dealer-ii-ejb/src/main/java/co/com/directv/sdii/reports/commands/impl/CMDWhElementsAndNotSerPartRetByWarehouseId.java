/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.NotSerializedAjustmentVO;
import co.com.directv.sdii.model.dto.collection.NotSerializedAjustmentCollDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWhElementsAndNotSerPartRetByWarehouseIdLocal;
import co.com.directv.sdii.reports.dto.NotSerializedAjustmentDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWhElementsAndNotSerPartRetByWarehouseId", mappedName="ejb/CMDWhElementsAndNotSerPartRetByWarehouseId")
public class CMDWhElementsAndNotSerPartRetByWarehouseId extends BaseCommand  implements CMDWhElementsAndNotSerPartRetByWarehouseIdLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementFacadeBeanLocal elementFacadeBeanLocal;

	public CMDWhElementsAndNotSerPartRetByWarehouseId(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			NotSerializedAjustmentCollDTO r;
			HashMap<String,String> map = getParams(args);
			Long warehouseId = null;
			String strWarehouseId = map.get("warehouseId"); 
			if (strWarehouseId != null && !strWarehouseId.isEmpty())
				warehouseId = Long.parseLong(strWarehouseId);

			r = elementFacadeBeanLocal.getWhElementsAndNotSerPartRetByWarehouseId(warehouseId, null);
			
			List<NotSerializedAjustmentDTO> response = new ArrayList<NotSerializedAjustmentDTO>();
			if( r.getCollection() != null ){
				for(NotSerializedAjustmentVO vo : r.getCollection()){
					if( vo.getElement() != null && vo.getElement().getNotSerialized() != null ){
						NotSerializedAjustmentDTO dto = new NotSerializedAjustmentDTO();
						dto.setElementTypeCode( vo.getElement().getNotSerialized().getElement().getElementType().getTypeElementCode() );
						//dto.setElementMeasureUnitName( vo.getElement().getNotSerialized().getElement().getMeasureUnit().getUnitName() );
						dto.setElementMeasureUnitName( vo.getElement().getNotSerialized().getElement().getElementType().getMeasureUnit().getUnitName() );
						dto.setElementStatusName( vo.getElement().getNotSerialized().getElement().getElementStatus().getElementStatusName() );
						dto.setElementLote( vo.getElement().getNotSerialized().getElement().getLote() != null ? vo.getElement().getNotSerialized().getElement().getLote() : "" );
						dto.setElementInitialQuantity( vo.getElement().getInitialQuantity() );
						dto.setElementActualQuantity( vo.getElement().getActualQuantity() );
						dto.setEntryDate( UtilsBusiness.formatDate( vo.getElement().getMovementDate(),UtilsBusiness.DATE_FORMAT) );
						response.add(dto);
					}
				}
			}
			return (List<T>) response;
		}catch(Throwable e){
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
