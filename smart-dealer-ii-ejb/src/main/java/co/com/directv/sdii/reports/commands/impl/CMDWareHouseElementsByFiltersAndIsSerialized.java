package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWareHouseElementsByFiltersAndIsSerializedLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.reports.dto.WarehouseElementDTO;

@Stateless(mappedName = "ejb/CMDWareHouseElementsByFiltersAndIsSerialized")
public class CMDWareHouseElementsByFiltersAndIsSerialized  extends BaseCommand  implements ICommand,CMDWareHouseElementsByFiltersAndIsSerializedLocal {
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal;

	@Override
	//Modificado para Requerimiento: CC057
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			HashMap<String,String> map = getParams(args);
			Long countryId = map.get("countryId")!=null && !map.get("countryId").isEmpty() ? Long.parseLong(map.get("countryId").toString()) : null;
			Long dealerId = map.get("dealerId")!=null && !map.get("dealerId").isEmpty() ? Long.parseLong(map.get("dealerId").toString()) : null;
			Long branchDealerId = map.get("branchDealerId")!=null && !map.get("branchDealerId").isEmpty() ? Long.parseLong(map.get("branchDealerId").toString()) : null;
			Long warehouseTypeId = map.get("warehouseTypeId")!=null && !map.get("warehouseTypeId").isEmpty() ? Long.parseLong(map.get("warehouseTypeId").toString()) : null;
			Long wareHouseId = map.get("wareHouseId")!=null && !map.get("wareHouseId").isEmpty() ? Long.parseLong(map.get("wareHouseId").toString()) : null;
			Long elementTypeId = map.get("elementTypeId")!=null && !map.get("elementTypeId").isEmpty() ? Long.parseLong(map.get("elementTypeId").toString()) : null;
			String serialNumber = map.get("serialNumber")!=null && !map.get("serialNumber").isEmpty() ? map.get("serialNumber").toString() : null;
			
			//Carga el dto para realizar la consulta
			FilterSerializedElementDTO filterSerializedElement = new FilterSerializedElementDTO();
			filterSerializedElement.setCountryId(countryId);
			filterSerializedElement.setDealerId(dealerId);
			filterSerializedElement.setBranchId(branchDealerId);
			filterSerializedElement.setWarehouseTypeId(warehouseTypeId);
			filterSerializedElement.setWarehouseId(wareHouseId);
			filterSerializedElement.setElementTypeId(elementTypeId);
			filterSerializedElement.setSerialCode(serialNumber);
			
			WareHouseElementResponse serviceResponse = warehouseElementFacadeBeanLocal.getWarehouseElementsByFiltersAndIsSerializedLast(filterSerializedElement, null);
			List<WarehouseElementDTO> responseList = new ArrayList<WarehouseElementDTO>();
			if(serviceResponse.getWareHouseElementsVO()!= null){
				for(WarehouseElementVO vo : serviceResponse.getWareHouseElementsVO()){
					WarehouseElementDTO dto = new WarehouseElementDTO();

					if(vo.getWarehouseId().getDealerId().getDealer()!=null){
						dto.setDealerName(vo.getWarehouseId().getDealerId().getDealer().getDealerName());
						dto.setBranchName(vo.getWarehouseId().getDealerId().getDealerName());
					}else{
						dto.setDealerName(vo.getWarehouseId().getDealerId().getDealerName());
					}
					dto.setWhCode(vo.getWarehouseName());
					dto.setModelName(vo.getSerialized().getElement().getElementType().getElementModel().getModelName());
					dto.setTypeElementId(vo.getSerialized().getElement().getId());
					dto.setTypeElementCode(vo.getSerialized().getElement().getElementType().getTypeElementCode());
					dto.setTypeElementName(vo.getSerialized().getElement().getElementType().getTypeElementName());
					dto.setSerialCode(vo.getSerialized().getSerialCode() != null ? vo.getSerialized().getSerialCode() : null);
					dto.setRid(vo.getSerialized().getIrd() != null ? vo.getSerialized().getIrd() : null);
					dto.setLinkedSerial((vo.getSerialized().getSerialized() != null && vo.getSerialized().getSerialized().getSerialCode() != null ) ? vo.getSerialized().getSerialized().getSerialCode() : null);
					responseList.add(dto);
				}
			}
			return (List<T>) responseList;
		} catch(Throwable e){
			throw this.manageException(e);
		}
	}

	@Override
	public List<String> getFieldList() {
		return null;
	}

	@Override
	public void setFieldList(List<String> fieldList) {
		
	}

}
