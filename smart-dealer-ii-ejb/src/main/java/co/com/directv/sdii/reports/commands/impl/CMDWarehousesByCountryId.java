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
import co.com.directv.sdii.reports.commands.CMDWarehousesByCountryIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehousesByCountryId", mappedName="ejb/CMDWarehousesByCountryId")
public class CMDWarehousesByCountryId extends BaseCommand  implements ICommand,CMDWarehousesByCountryIdLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseFacadeBeanLocal warehouseFacadeBeanLocal;

	public CMDWarehousesByCountryId(){
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
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			r = warehouseFacadeBeanLocal.getWarehousesByCountryId(countryId, null);
			List<WarehouseDTO> response = new ArrayList<WarehouseDTO>();
			if(  r.getWareHouseVO() != null ){
				for( WarehouseVO vo : r.getWareHouseVO()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhCode( vo.getWhCode() );
					dto.setDepotCode( vo.getDealerId() == null ? "" : ( vo.getDealerId().getDealer() ==null ? "" : ( vo.getDealerId().getDealer().getDepotCode() == null ? "" : vo.getDealerId().getDealer().getDepotCode() ) ) );
					dto.setBranchDealerName( vo.getDealerId() == null ? "" : ( vo.getDealerId().getDepotCode() == null ? "" : vo.getDealerId().getDepotCode() ) );
					dto.setCrewInfo( vo.getCrewResponsable() != null ?  vo.getCrewResponsable() : "" );
					dto.setWhType( vo.getWarehouseType().getWhTypeName() );
					dto.setWhResponsable( vo.getWhResponsible() != null ? vo.getWhResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					response.add(dto);
				}
			}
			return (List<T>)response;
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
