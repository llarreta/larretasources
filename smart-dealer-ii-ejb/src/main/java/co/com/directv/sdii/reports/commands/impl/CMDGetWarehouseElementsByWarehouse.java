/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetWarehouseElementsByWarehouseLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Clase que genera un List<MovedElementSerializedDTO> invocando el metodo
 * getMovedWareHouseElementSerializedByLinkedOrSerialCode de la clase
 * WarehouseElementFacadeBeanLocal.
 * 
 * Fecha de Creación: 2/09/2011
 * 
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDGetWarehouseElementsByWarehouse", mappedName = "ejb/CMDGetWarehouseElementsByWarehouse")
public class CMDGetWarehouseElementsByWarehouse extends BaseCommand implements
		ICommand,CMDGetWarehouseElementsByWarehouseLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<QuantityWarehouseElementsDTO> response=null;

	@EJB
	private WarehouseElementFacadeBeanLocal whElementFacade;
	
	public CMDGetWarehouseElementsByWarehouse() {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try {
			response=(List<QuantityWarehouseElementsDTO>) executeResponse(args).getQuantityWarehouseElementsDTO();			
			return (List<T>) response;
		} catch (Throwable e) {
			throw this.manageException(e);
		}
	}

	public QuantityWarehouseElementResponse executeResponse(String args)throws BusinessException{
		try {
			QuantityWarehouseElementResponse returnValue;
			HashMap<String, String> map = getParams(args);
			Long wareHouseId=null; 
			Long dealerId=null;
			Long branchDealerId=null; 
			Long crewId=null;
			Long warehouseTypeId=null; 
			Long elementModelId=null;
			Long elementTypeId=null;
			String elementTypeCode=null;
			String serialElement=null;
			Date initialDate=null;
			Date finalDate=null;
			Long userId=null;
			
			String temp=map.get("wareHouseId"); 
			if(temp!=null && temp!=""){
				wareHouseId = Long.parseLong(temp); 
			}
			
			temp=map.get("dealerId");
			if(temp!=null && temp!=""){
				dealerId = Long.parseLong(temp); 
			}
			
			temp=map.get("branchDealerId"); 
			if(temp!=null && temp!=""){
				branchDealerId = Long.parseLong(temp); 
			}
			
			temp=map.get("crewId");
			if(temp!=null && temp!=""){
				crewId = Long.parseLong(temp); 
			}
			
			temp=map.get("warehouseTypeId"); 
			if(temp!=null && temp!=""){
				warehouseTypeId = Long.parseLong(temp); 
			}
			
			temp=map.get("elementModelId");
			if(temp!=null && temp!=""){
				elementModelId = Long.parseLong(temp); 
			}
			
			temp=map.get("elementTypeId");
			if(temp!=null && temp!=""){
				elementTypeId = Long.parseLong(temp); 
			}
			
			temp=map.get("elementTypeCode");
			if(temp!=null && temp!=""){
				elementTypeCode = temp; 
			}
			
			temp=map.get("serialElement");
			if(temp!=null && temp!=""){
				serialElement = temp; 
			}
			
			temp=map.get("initialDate");
			if(temp!=null && temp!=""){
				initialDate = new Date(temp); 
			}
			
			temp=map.get("finalDate");
			if(temp!=null && temp!=""){
				finalDate = new Date(temp); 
			}
			
			temp=map.get("userId");
			if(temp!=null && temp!=""){
				userId = Long.parseLong(temp); 
			}
			
			WhElementSearchFilter whElementSearchFilter=new WhElementSearchFilter();
			whElementSearchFilter.setWareHouseId(wareHouseId); 
			whElementSearchFilter.setDealerId(dealerId);
			whElementSearchFilter.setBranchDealerId(branchDealerId); 
			whElementSearchFilter.setCrewId(crewId);
			whElementSearchFilter.setWarehouseTypeId(warehouseTypeId); 
			whElementSearchFilter.setElementModelId(elementModelId);
			whElementSearchFilter.setElementTypeId(elementTypeId);
			whElementSearchFilter.setElementTypeCode(elementTypeCode);
			whElementSearchFilter.setSerialElement(serialElement);
			whElementSearchFilter.setInitialDate(initialDate);
			whElementSearchFilter.setFinalDate(finalDate);
			whElementSearchFilter.setUserId(userId);
			
			RequestCollectionInfo rci = new RequestCollectionInfo();
			
			temp=map.get("pageIndex");
			int pageIndex = -1;
			if(temp!=null && temp!=""){
				pageIndex = Integer.parseInt(temp); 
			}			
			rci.setPageIndex(pageIndex);

			temp=map.get("pageSize");
			int pageSize = -1;
			if(temp!=null && temp!=""){
				pageSize = Integer.parseInt(temp); 
			}			
			rci.setPageSize(pageSize);
			
			if(pageSize != -1 && pageIndex != -1){
				returnValue = whElementFacade.getWarehouseElementsByWarehouse(whElementSearchFilter,rci);
			}else{
				returnValue = whElementFacade.getWarehouseElementsByWarehouse(whElementSearchFilter,null);
			}

			return  returnValue;
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
