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
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetWarehouseElementsSummariesByFilterLocal;
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
@Stateless(name = "CMDGetWarehouseElementsSummariesByFilters", mappedName = "ejb/CMDGetWarehouseElementsSummariesByFilters")
public class CMDGetWarehouseElementsSummariesByFilters extends BaseCommand implements
		ICommand,CMDGetWarehouseElementsSummariesByFilterLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<QuantityWarehouseElementsDTO> response=null;
    
	//@EJB
	//private WarehouseElementFacadeBeanLocal whElementFacade;
    
	@EJB
	private WarehouseElementFacadeBeanLocal warehouseElementFacadeBean;

	
	public CMDGetWarehouseElementsSummariesByFilters() {
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
			Long branchDealerId=null; 
			Long crewId=null;
			Long warehouseTypeId=null; 
			Long elementModelId=null;
			Long elementTypeId=null;
			Date initialDate=null;
			Date finalDate=null;
			
			String temp=map.get("wareHouseId"); //
			if(temp!=null && temp!=""){
				wareHouseId = Long.parseLong(temp); 
			}
			

			temp=map.get("branchDealerId"); //
			if(temp!=null && temp!=""){
				branchDealerId = Long.parseLong(temp); 
			}
			
			temp=map.get("crewId");//
			if(temp!=null && temp!=""){
				crewId = Long.parseLong(temp); 
			}
			
			temp=map.get("warehouseTypeId"); //
			if(temp!=null && temp!=""){
				warehouseTypeId = Long.parseLong(temp); 
			}
			
			temp=map.get("elementModelId");//
			if(temp!=null && temp!=""){
				elementModelId = Long.parseLong(temp); 
			}
			
			temp=map.get("elementTypeId");//
			if(temp!=null && temp!=""){
				elementTypeId = Long.parseLong(temp); 
			}

			temp=map.get("initialDate"); //movementDateIn
			if(temp!=null && temp!=""){
				initialDate = new Date(temp); 
			}
			
			temp=map.get("finalDate"); //movementDateOut
			if(temp!=null && temp!=""){
				finalDate = new Date(temp); 
			}
			

			
		QuantityWarehouseElementsDTO quantityWarehouseElementsDTO = new QuantityWarehouseElementsDTO();

			
		    quantityWarehouseElementsDTO.setWhId(wareHouseId);
		    quantityWarehouseElementsDTO.setDealerIdBranch(branchDealerId);
		    quantityWarehouseElementsDTO.setCrewId(crewId);
		    quantityWarehouseElementsDTO.setWhTypeId(warehouseTypeId);
		    quantityWarehouseElementsDTO.setWhId(wareHouseId);
		    quantityWarehouseElementsDTO.setElementModelId(elementModelId);
		    quantityWarehouseElementsDTO.setTypeElementId(elementTypeId);
		    quantityWarehouseElementsDTO.setMovementDateIn(initialDate);
		    quantityWarehouseElementsDTO.setMovementDateOut(finalDate);
		    RequestCollectionInfoDTO rci = new RequestCollectionInfoDTO();

			
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
				returnValue= warehouseElementFacadeBean.getQuantityWarehouseElementsDetailsByFilters(quantityWarehouseElementsDTO,rci);
			}else{
				returnValue= warehouseElementFacadeBean.getQuantityWarehouseElementsDetailsByFilters(quantityWarehouseElementsDTO,null);
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
