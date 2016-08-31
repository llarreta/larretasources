/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.model.vo.WhElementSearchFilterVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDWarehouseElementsBySearchFilterLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.WarehouseElementDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDWarehouseElementsBySearchFilter", mappedName="ejb/CMDWarehouseElementsBySearchFilter")
public class CMDWarehouseElementsBySearchFilter  extends BaseCommand implements ICommand,CMDWarehouseElementsBySearchFilterLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBeanLocal; 

	public CMDWarehouseElementsBySearchFilter(){
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
			WhElementSearchFilterVO obj;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			obj = new WhElementSearchFilterVO();
			
			Long branchDealerId = map.get("branchDealerId") == null || map.get("branchDealerId").isEmpty() ? null : Long.parseLong(map.get("branchDealerId"));
			Long crewId = map.get("crewId") == null || map.get("crewId").isEmpty() ? null : Long.parseLong(map.get("crewId"));
			Long dealerId = map.get("dealerId") == null || map.get("dealerId").isEmpty() ? null : Long.parseLong(map.get("dealerId"));
			Long elementModelId = map.get("elementModelId") == null || map.get("elementModelId").isEmpty() ? null : Long.parseLong(map.get("elementModelId"));
			Long elementTypeId = map.get("elementTypeId") == null || map.get("elementTypeId").isEmpty() ? null : Long.parseLong(map.get("elementTypeId"));
			Long wareHouseId = map.get("wareHouseId") == null || map.get("wareHouseId").isEmpty() ? null : Long.parseLong(map.get("wareHouseId"));
			Long warehouseTypeId = map.get("warehouseTypeId") == null || map.get("warehouseTypeId").isEmpty() ? null : Long.parseLong(map.get("warehouseTypeId"));
			String elementTypeCode = map.get("elementTypeCode") == null || map.get("elementTypeCode").isEmpty() ? null : map.get("elementTypeCode");
			String serialElement = map.get("serialElement") == null || map.get("serialElement").isEmpty() ? null : map.get("serialElement");
			Date finalDate = null;
			Date initialDate = null;

			try{
				finalDate = map.get("finalDate") == null || map.get("finalDate").isEmpty() ? null : df.parse(map.get("finalDate"));
				initialDate = map.get("initialDate") == null || map.get("initialDate").isEmpty() ? null : df.parse(map.get("initialDate"));
			} catch (Exception e){
				throw new BusinessException("== Error convirtiendo la Fecha ==", e);
			}

			obj.setBranchDealerId(branchDealerId);
			obj.setCrewId(crewId);
			obj.setDealerId(dealerId);
			obj.setElementModelId(elementModelId);
			obj.setElementTypeCode(elementTypeCode);
			obj.setElementTypeId(elementTypeId);
			obj.setFinalDate(finalDate);
			obj.setInitialDate(initialDate);
			obj.setSerialElement(serialElement);
			obj.setWareHouseId(wareHouseId);
			obj.setWarehouseTypeId(warehouseTypeId);
			
			r = warehouseElementFacadeBeanLocal.getWarehouseElementsBySearchFilter(obj, null);
			
			List<WarehouseElementDTO> responseList = new ArrayList<WarehouseElementDTO>();
			if( r.getWareHouseElementsVO() != null){
				for( WarehouseElementVO vo : r.getWareHouseElementsVO() ){
					WarehouseElementDTO dto = new WarehouseElementDTO();
					if( vo.getSerialized() != null ){
						if( vo.getWarehouseId() != null ){
							if( vo.getWarehouseId().getDealerId() != null && vo.getWarehouseId().getDealerId().getDealerName() != null )
								dto.setDealerName( vo.getWarehouseId().getDealerId().getDealerName() );
							else
								dto.setDealerName( "" );
						}
						dto.setWhCode( vo.getWarehouseId().getWhCode() );
						dto.setTypeElementName( vo.getSerialized().getElement().getElementType().getTypeElementName() );
						dto.setModelName( vo.getSerialized().getElement().getElementType().getElementModel().getModelName() );
						dto.setSerialCode( vo.getSerialized().getSerialCode() );
						if( vo.getSerialized().getIrd() != null )
							dto.setRid( vo.getSerialized().getIrd() );
						else
							dto.setRid("");
						if( vo.getSerialized().getSerialized() != null && vo.getSerialized().getSerialized().getSerialCode() != null )
							dto.setLinkedSerial( vo.getSerialized().getSerialized().getSerialCode() );
						else
							dto.setLinkedSerial( "" );
						responseList.add(dto);
					}
				}
			}
			return (List<T>) responseList;
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
