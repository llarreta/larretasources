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
import co.com.directv.sdii.model.dto.WarehouseInfoDetailDTO;
import co.com.directv.sdii.model.dto.WarehouseInfoResponseDetailDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllWarehousesByCountryIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllWarehousesByCountryId", mappedName="ejb/CMDAllWarehousesByCountryId")
public class CMDAllWarehousesByCountryId extends BaseCommand  implements ICommand,CMDAllWarehousesByCountryIdLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseFacadeBeanLocal warehouseFacadeBeanLocal;

	public CMDAllWarehousesByCountryId(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			WarehouseInfoResponseDetailDTO r;
			HashMap<String,String> map = getParams(args);
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			String whCode = map.get("whCode") == null || map.get("whCode").isEmpty() ? null : map.get("whCode");
			
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			r = warehouseFacadeBeanLocal.getAllWarehousesByCountryId(countryId, whCode,null);
			List<WarehouseDTO> response = new ArrayList<WarehouseDTO>();

			/*if(  r.getWareHouseVO() != null ){
				for( WarehouseVO vo : r.getWareHouseVO()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWhCode() );
					dto.setDepotCode( vo.getDealerId() == null ? "" : ( vo.getDealerId().getDealer() ==null ? "" : ( vo.getDealerId().getDealer().getDepotCode() == null ? "" : vo.getDealerId().getDealer().getDepotCode() ) ) );
					String branchDealerName = null;
					String principalDealerName = null;
					// caso que la bodega pertenezca a una principal
					if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
						branchDealerName = vo.getDealerId().getDealerName();
					//caso que sea una sucursal
					}else if( vo.getDealerId() != null && vo.getDealerId().getDealer() != null ){
						branchDealerName = vo.getDealerId().getDealer().getDealerName();
						principalDealerName = vo.getDealerId().getDealerName();
					}
					dto.setBranchDealerName( branchDealerName );
					dto.setPrincipalDealerName( principalDealerName );
					dto.setCrewInfo( vo.getCrewResponsable() != null ?  vo.getCrewResponsable() : "" );
					dto.setWhType( vo.getWarehouseType().getWhTypeName() );
					dto.setWhResponsable( vo.getWhResponsible() != null ? vo.getWhResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
					response.add(dto);
				}
			}*/
			if (!r.getWarehouseInfoDetailDTOList().isEmpty()){
				for( WarehouseInfoDetailDTO vo : r.getWarehouseInfoDetailDTOList()){
					WarehouseDTO dto = new WarehouseDTO();
					dto.setWhName(vo.getWarehouseName());
					dto.setWhCode( vo.getWarehouseCode() );
					if(vo.getBranchDealerCode()!=null)
						dto.setDepotCode(vo.getBranchDealerCode());
					else 
						dto.setDepotCode("");
					String branchDealerName = null;
					String principalDealerName = null;
					// caso que la bodega pertenezca a una principal
					//if( vo.getDealerId() != null && vo.getDealerId().getDealer() == null ){
					if(vo.getBranchDealerName()==null){	
						branchDealerName = vo.getDealerName();
					//caso que sea una sucursal
					//}else if( vo.getDealerId() != null && vo.getDealerId().getDealer() != null ){
					}else if( vo.getBranchDealerName() != null ){
						branchDealerName = vo.getBranchDealerName();
						principalDealerName = vo.getDealerName();
					}
					dto.setBranchDealerName( branchDealerName );
					dto.setPrincipalDealerName( principalDealerName );
					dto.setCrewInfo( vo.getCrewName() != null ?  vo.getCrewName() : "" );
					dto.setWhType( vo.getWarehouseType());
					dto.setWhResponsable( vo.getResponsible() != null ? vo.getResponsible() : null );
					dto.setWhResponsableMail( vo.getResponsibleEmail() != null ? vo.getResponsibleEmail() : "" );
					dto.setWhAddress( vo.getWhAddress() != null ? vo.getWhAddress() : "" );
					dto.setStatusWh(vo.getIsActive() != null ? vo.getIsActive() : "") ;
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
