package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.model.vo.WarehouseElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCodeLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Clase que genera un List<WarehouseElementVO> invocando el metodo
 * getSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode de la clase
 * WarehouseElementFacadeBeanLocal.
 * 
 * Fecha de Creación: 2/09/2011
 * 
 * @author wjimenez
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode", mappedName = "ejb/CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode")
public class CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode extends BaseCommand implements
		ICommand,CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCodeLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<WarehouseElementVO> response=null;

	@EJB
	private WarehouseElementFacadeBeanLocal whElementFacade;
	
	public CMDGetSerializedElementsLastByDealerOrCrewOrWhOrWhTypeSerialCode() {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {

		try {
			
			HashMap<String, String> map = getParams(args);
			WareHouseRequestDTO wareHouseRequestDTO = new WareHouseRequestDTO();
			
			Long whId=null; 
			Long dealerId=null;
			Long crewId=null;
			String customerCode=null;
			Long whTypeId=null; 
			Long typeElementId=null;
			String serial=null;
			Long countryId=null;
			
			String temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_ID.getCodeEntity()); 
			if(temp!=null && temp!=""){
				whId = Long.parseLong(temp); 
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_DEALER_ID.getCodeEntity());
			if(temp!=null && temp!=""){
				dealerId = Long.parseLong(temp); 
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_CREW_ID.getCodeEntity());
			if(temp!=null && temp!=""){
				crewId = Long.parseLong(temp); 
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_TYPE_ID.getCodeEntity());
			if(temp!=null && temp!=""){
				whTypeId = Long.parseLong(temp); 
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_TYPE_ID.getCodeEntity());
			if(temp!=null && temp!=""){
				typeElementId = Long.parseLong(temp);  
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_SERIAL.getCodeEntity());
			if(temp!=null && temp!=""){
				serial = temp; 
			}
			
			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_COUNTRY_ID.getCodeEntity());
			if(temp!=null && temp!=""){
				countryId = Long.parseLong(temp);  
			}

			temp=map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_CUSTOMER_CODE.getCodeEntity());
			if(temp!=null && temp!=""){
				customerCode = temp;  
			}
			
			wareHouseRequestDTO.setCountryId(countryId);
			wareHouseRequestDTO.setDealerId(dealerId);
			wareHouseRequestDTO.setCrewId(crewId);
			wareHouseRequestDTO.setCustomerCode(customerCode);
			wareHouseRequestDTO.setSerialCode(serial);
			wareHouseRequestDTO.setWarehouseId(whId);
			wareHouseRequestDTO.setWarehouseTypeId(whTypeId);
			wareHouseRequestDTO.setElementTypeId(typeElementId);
			wareHouseRequestDTO.setRequestCollInfo(null);
			WareHouseElementResponse whElementResponse = whElementFacade.getSerializedElementsLastByCriteria(wareHouseRequestDTO);
			response = whElementResponse.getWareHouseElementsVO();
			
			return (List<T>) response;
		} catch (Throwable e) {
			throw this.manageException(e);
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#getFieldList()
	 */
	@Override
	public List<String> getFieldList() {
		return fieldList;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#setFieldList(java.util.List)
	 */
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}

}
