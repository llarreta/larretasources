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
import co.com.directv.sdii.model.dto.WareHouseElementHistoricalDTO;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementHistoricalResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWareHouseElementHistoricalForSerializedElementLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseElemHistoricalDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWareHouseElementHistoricalForSerializedElement", mappedName="ejb/CMDWareHouseElementHistoricalForSerializedElement")
public class CMDWareHouseElementHistoricalForSerializedElement extends BaseCommand  implements ICommand,CMDWareHouseElementHistoricalForSerializedElementLocal {
	
	private List<String> fieldList = new ArrayList<String>();

	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal; 
	
	public CMDWareHouseElementHistoricalForSerializedElement(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WareHouseElementHistoricalResponse r;
			HashMap<String,String> map = getParams(args);
			Long serializedId = null;
			String strSerializedId = map.get("serializedId"); 
			if (strSerializedId != null && !strSerializedId.isEmpty())
				serializedId = Long.parseLong(strSerializedId);

			r = warehouseElementFacadeBeanLocal.getWareHouseElementHistoricalForSerializedElement(serializedId, null);
			
			List<WarehouseElemHistoricalDTO> response = new ArrayList<WarehouseElemHistoricalDTO>();
			
			if( r.getWareHouseHistorical() != null ){
				for( WareHouseElementHistoricalDTO vo : r.getWareHouseHistorical() ){
					WarehouseElemHistoricalDTO dto = new WarehouseElemHistoricalDTO();
					if( vo.getWareHouseElement() != null ){
						if( vo.getWareHouseElement().getSerialized() != null ){
							if( vo.getWareHouseElement().getSerialized().getElement() != null ){
								dto.setHistoricalCode( vo.getWareHouseElement().getId() );
								dto.setElementModelName( vo.getWareHouseElement().getSerialized().getElement().getElementType().getElementModel().getModelName() );
							} else{
								dto.setElementModelName("");
							}
							dto.setSerialCode( vo.getWareHouseElement().getSerialized().getSerialCode() );
						}else {
							dto.setSerialCode("");
						}
						dto.setExitDate( UtilsBusiness.formatDate( vo.getWareHouseElement().getMovementDate(),UtilsBusiness.DATE_FORMAT)  );
						dto.setImportLogPurchaseOrder( vo.getWareHouseElement().getImportLogPurchaseOrder() );
						dto.setMovementTypeName( vo.getWareHouseElement().getMovementType().getMovTypeName() );
					} else {
						dto.setImportLogPurchaseOrder("");
						dto.setMovementTypeName("");
					}
					dto.setImportLogId( vo.getImportLogId() );
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
