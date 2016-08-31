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
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWhElementsByCriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseElementDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWhElementsByCriteria", mappedName="ejb/CMDWhElementsByCriteria")
public class CMDWhElementsByCriteria extends BaseCommand  implements ICommand,CMDWhElementsByCriteriaLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal; 

	public CMDWhElementsByCriteria(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WareHouseElementResponse r;
			HashMap<String,String> map = getParams(args);

			Long warehouseId = null;
			String strWarehouseId = map.get("warehouseId"); 
			if (strWarehouseId != null && !strWarehouseId.isEmpty())
				warehouseId = Long.parseLong(strWarehouseId);

			String isSerialized = map.get("isSerialized");

			Long elementId = null;
			String strElementId = map.get("elementId"); 
			if (strElementId != null && !strElementId.isEmpty())
				elementId = Long.parseLong(strElementId);

			r = warehouseElementFacadeBeanLocal.getWhElementsByCriteria(warehouseId, isSerialized, elementId, null);
			List<WarehouseElementDTO> response = new ArrayList<WarehouseElementDTO>();
			if( r.getWareHouseElementsVO() != null ){
				for( WarehouseElementVO vo : r.getWareHouseElementsVO() ){
					WarehouseElementDTO dto = new WarehouseElementDTO();
					if( vo.getSerialized() != null ){
						dto.setSerialized( true );
						dto.setTypeElementCode( vo.getSerialized().getElement().getElementType().getTypeElementCode() );
						dto.setModelName( vo.getSerialized().getElement().getElementType().getElementModel().getModelName() );
						dto.setSerialCode( vo.getSerialized().getSerialCode() != null ? vo.getSerialized().getSerialCode() : "" );
						dto.setRid( vo.getSerialized().getIrd() != null ? vo.getSerialized().getIrd() : "" );
						dto.setLinkedSerial( ( vo.getSerialized().getSerialized() != null && vo.getSerialized().getSerialized().getSerialCode() != null) ? vo.getSerialized().getSerialized().getSerialCode() : "");
						dto.setElementStatusName( vo.getSerialized().getElement().getElementStatus().getElementStatusName() );
						dto.setRegistrationDate( UtilsBusiness.formatDate( vo.getSerialized().getRegistrationDate(),UtilsBusiness.DATE_FORMAT) );
					} else if( vo.getNotSerialized() != null ){
						dto.setSerialized( false );
						dto.setTypeElementCode( vo.getNotSerialized().getElement().getElementType().getTypeElementCode() );
						//dto.setUnitName( vo.getNotSerialized().getElement().getMeasureUnit().getUnitName() );
						dto.setUnitName( vo.getNotSerialized().getElement().getElementType().getMeasureUnit().getUnitName() );
						dto.setActualQuantity( vo.getActualQuantity() );
						dto.setLote( vo.getNotSerialized().getElement().getLote() );
						dto.setElementStatusName( vo.getNotSerialized().getElement().getElementStatus().getElementStatusName() );
						dto.setRegistrationDate( UtilsBusiness.formatDate( vo.getNotSerialized().getRegistrationDate(),UtilsBusiness.DATE_FORMAT) );
						dto.setAdjustmentQuantity( vo.getAjustmentQauntity() );
					}
					response.add(dto);
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
