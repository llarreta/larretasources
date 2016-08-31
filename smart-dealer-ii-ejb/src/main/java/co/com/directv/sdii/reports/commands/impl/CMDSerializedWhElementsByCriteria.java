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
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDSerializedWhElementsByCriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseElementDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDSerializedWhElementsByCriteria", mappedName="ejb/CMDSerializedWhElementsByCriteria")
public class CMDSerializedWhElementsByCriteria extends BaseCommand  implements ICommand,CMDSerializedWhElementsByCriteriaLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal; 
	
	public CMDSerializedWhElementsByCriteria(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			SerializedWhElementsByCriteriaPaginationResponse r;
			HashMap<String,String> map = getParams(args);
			
			Long warehouseId = null;
			String strWarehouseId = map.get("warehouseId"); 
			if (strWarehouseId != null && !strWarehouseId.isEmpty())
				warehouseId = Long.parseLong(strWarehouseId);

			Long typeId = null;
			String strTypeId = map.get("typeId"); 
			if (strTypeId != null && !strTypeId.isEmpty())
				typeId = Long.parseLong(strTypeId);

			Long modelId = null;
			String strModelId = map.get("modelId"); 
			if (strModelId != null && !strModelId.isEmpty())
				modelId = Long.parseLong(strModelId);

			r = warehouseElementFacadeBeanLocal.getSerializedWhElementsByCriteria(warehouseId, typeId, modelId, null);
			
			List<WarehouseElementDTO> response = new ArrayList<WarehouseElementDTO>();
			if(  r.getCollectionVO() != null ){
				for(WarehouseElementVO vo :  r.getCollectionVO()){
					if( vo.getSerialized() != null ){
						WarehouseElementDTO dto = new WarehouseElementDTO();
						dto.setTypeElementId( vo.getSerialized().getElementId() );
						dto.setTypeElementCode( vo.getSerialized().getElement().getElementType().getTypeElementCode() );
						dto.setSerialCode( vo.getSerialized().getSerialCode() != null ? vo.getSerialized().getSerialCode() : "" );
						dto.setRid( vo.getSerialized().getIrd() != null ? vo.getSerialized().getIrd() : "" );
						dto.setLinkedSerial( ( vo.getSerialized().getSerialized() != null && vo.getSerialized().getSerialized().getSerialCode() != null ) ? vo.getSerialized().getSerialized().getSerialCode() : "" );
						dto.setElementStatusName( vo.getSerialized().getElement().getElementStatus().getElementStatusName() );
						dto.setRegistrationDate( UtilsBusiness.formatDate( vo.getSerialized().getRegistrationDate(),UtilsBusiness.DATE_FORMAT) );
						response.add(dto);
					}
					
				}
			}
			return (List<T>)response;
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
