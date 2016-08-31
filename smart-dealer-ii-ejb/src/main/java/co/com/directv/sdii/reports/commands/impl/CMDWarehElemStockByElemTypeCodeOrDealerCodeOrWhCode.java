/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCodeLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseElemStockDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode", mappedName="ejb/CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode")
public class CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode extends BaseCommand  implements ICommand,CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCodeLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseElementStockBusinessBeanLocal warehouseElementStockBusinessBeanLocal; 
	
	public CMDWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	//Modificado para Requerimiento: CC057
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WarehouseElementStockDTO r;
			HashMap<String,String> map = getParams(args);
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			String elementTypeCode = map.get("elementTypeCode");
			Long dealerId = new Long( map.get("dealerId") );
			String warehouseCode = map.get("warehouseCode");
			Long dealerBranchId = new Long( map.get("dealerBranchId") );
			
			r = warehouseElementStockBusinessBeanLocal.getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(elementTypeCode, dealerId, warehouseCode, countryId, null, dealerBranchId);
			List<WarehouseElemStockDTO> response = new ArrayList<WarehouseElemStockDTO>();
			if( r.getWhElementsStock() != null ){
				for( WarehouseElementStockVO vo : r.getWhElementsStock() ){
					WarehouseElemStockDTO dto = new WarehouseElemStockDTO();
					if( vo.getWarehouse().getDealerId() != null && vo.getWarehouse().getDealerId().getDealer() != null ){ 
						dto.setPrincipalDealerCode( vo.getWarehouse().getDealerId().getDealer().getDealerCode() );
					}
					dto.setBranchDealerCode( vo.getWarehouse().getDealerId().getDealerCode() );
					
					dto.setPrincipalDealer( vo.getDealerDepotPlusName() );
					dto.setBranchDealer( vo.getDealerBranchDepotPlusName() );
					
					dto.setWhCode( vo.getWarehouse().getWhCode() );
					dto.setElementId( vo.getElementType().getId() );
					dto.setTypeElementName( vo.getElementType().getTypeElementName() );
					dto.setMinQuantity( vo.getMinQuantity() );
					dto.setMaxQuantity( vo.getMaxQuantity() );
					dto.setReorderQuantity( vo.getReorderQuantity() );
					dto.setComments( vo.getComments() != null ? vo.getComments() : "" );
					dto.setLocation(vo.getWareHouseName());
					dto.setTypeElementCode(vo.getElementType().getTypeElementCode());
					response.add(dto);
				}
			}
			return (List<T>) response;
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
