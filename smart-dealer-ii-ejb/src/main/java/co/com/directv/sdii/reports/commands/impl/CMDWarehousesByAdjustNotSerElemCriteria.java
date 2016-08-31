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
import co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehousesByAdjustNotSerElemCriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehousesByAdjustNotSerElemCriteria", mappedName="ejb/CMDWarehousesByAdjustNotSerElemCriteria")
public class CMDWarehousesByAdjustNotSerElemCriteria extends BaseCommand  implements ICommand,CMDWarehousesByAdjustNotSerElemCriteriaLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseFacadeBeanLocal warehouseFacadeBeanLocal;
	
	public CMDWarehousesByAdjustNotSerElemCriteria(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WareHouseResponse r;
			HashMap<String,String> map = getParams(args);

			Long branchId = map.get("branchId") == null || map.get("branchId").isEmpty() ? null : Long.parseLong(map.get("branchId"));
			Long crewId = map.get("crewId") == null || map.get("crewId").isEmpty() ? null : Long.parseLong(map.get("crewId"));
			Long dealerId = map.get("dealerId") == null || map.get("dealerId").isEmpty() ? null : Long.parseLong(map.get("dealerId"));
			Long wareHouseId = map.get("wareHouseId") == null || map.get("wareHouseId").isEmpty() ? null : Long.parseLong(map.get("wareHouseId"));
			Long wareHouseTypeId = map.get("wareHouseTypeId") == null || map.get("wareHouseTypeId").isEmpty() ? null : Long.parseLong(map.get("wareHouseTypeId"));

			r = warehouseFacadeBeanLocal.getWarehousesByAdjustNotSerElemCriteria(wareHouseId, dealerId, branchId, crewId, wareHouseTypeId, null);
			List<WarehouseDTO> response = new ArrayList<WarehouseDTO>();
			if( r.getWareHouseVO() != null ){
				for( WarehouseVO vo : r.getWareHouseVO() ){
					WarehouseDTO dto = new WarehouseDTO();
					if( vo.getDealerId() != null ){
						dto.setPrincipalDealerName( vo.getDealerId().getDealer() != null ? vo.getDealerId().getDealer().getDealerName() : "" );
						dto.setBranchDealerName( vo.getDealerId().getDealerName() );
					} else {
						dto.setPrincipalDealerName( "" );
						dto.setBranchDealerName( "" );
					}
					dto.setWhCode( vo.getWhCode() );
					response.add(dto);
				}
			}
			return (List<T>) response;
		} catch( Throwable e ){
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
