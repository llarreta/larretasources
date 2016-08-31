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
import co.com.directv.sdii.facade.stock.WarehouseFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseResponse;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehousesByComplexFilterLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehousesByComplexFilter", mappedName="ejb/CMDWarehousesByComplexFilter")
public class CMDWarehousesByComplexFilter extends BaseCommand  implements ICommand,CMDWarehousesByComplexFilterLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseFacadeBeanLocal warehouseFacadeBeanLocal;

	public CMDWarehousesByComplexFilter(){
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
			Long wareHouseId = map.get("wareHouseId") == null || map.get("wareHouseId").isEmpty() ? null : Long.parseLong(map.get("wareHouseId"));
			Long companyId = map.get("companyId") == null || map.get("companyId").isEmpty() ? null : Long.parseLong(map.get("companyId"));
			Long branchId = map.get("branchId") == null || map.get("branchId").isEmpty() ? null : Long.parseLong(map.get("branchId"));
			Long crewId = map.get("crewId") == null || map.get("crewId").isEmpty() ? null : Long.parseLong(map.get("crewId"));
			Long wareHouseTypeId = map.get("wareHouseTypeId") == null || map.get("wareHouseTypeId").isEmpty() ? null : Long.parseLong(map.get("wareHouseTypeId"));

			WarehouseVO warehouseVO = new WarehouseVO();
			DealerVO dealerVO = new DealerVO();
			DealerVO branchVO = new DealerVO();
			CrewVO crewVO = new CrewVO();
			WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();

			warehouseVO.setId(wareHouseId);
			dealerVO.setId(companyId);
			branchVO.setId(branchId);
			crewVO.setId(crewId);
			warehouseTypeVO.setId(wareHouseTypeId);
			
			r = warehouseFacadeBeanLocal.getWarehousesByComplexFilter(warehouseVO, dealerVO, branchVO, crewVO, warehouseTypeVO, null);
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
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	private List<String> getDataList(WareHouseResponse r){
		List<String> list = new ArrayList<String>();
		for(WarehouseVO c : r.getWareHouseVO()){
			list.add(c.toXLSString());
		}
		return list;
	}

	@Override
	public List<String> getFieldList() {
		fieldList.add(ApplicationTextEnum.ID.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.WAREHOUSE_CODE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.WAREHOUSE_TYPE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.PERSON_RESPONSIBLE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.MAIL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.ADDRESS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.COUNTRY.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.DEALER_CODE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CREW_ID.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.IBS_CUSTOMER_CODE.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}

}
