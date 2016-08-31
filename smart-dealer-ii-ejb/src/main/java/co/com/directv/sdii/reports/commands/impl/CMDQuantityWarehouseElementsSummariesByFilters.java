/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDQuantityWarehouseElementsSummariesByFiltersLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Clase que genera un List<QuantityWarehouseElementsDTO> invocando el metodo
 * getMovedWareHouseElementSerializedByLinkedOrSerialCode de la clase
 * WarehouseElementFacadeBeanLocal.
 * 
 * Fecha de Creación: 14/09/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDQuantityWarehouseElementsSummariesByFilters", mappedName = "ejb/CMDQuantityWarehouseElementsSummariesByFilters")
public class CMDQuantityWarehouseElementsSummariesByFilters extends BaseCommand implements
		ICommand,CMDQuantityWarehouseElementsSummariesByFiltersLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<QuantityWarehouseElementsDTO> response=null;

	@EJB
	private WarehouseElementFacadeBeanLocal whElementFacade;

	public CMDQuantityWarehouseElementsSummariesByFilters() {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {

		try {
			
			HashMap<String, String> map = getParams(args);
			QuantityWarehouseElementsDTO filters = new QuantityWarehouseElementsDTO();
			
			//Creacion y Asignacion de filtros
			String strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DEALER_ID_BRANCH.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setDealerIdBranch(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_DEALER_ID_COMPANY.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setDealerIdCompany(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_CREW_ID.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setCrewId(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_TYPE_ID.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setWhTypeId(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_WAREHOUSE_ID.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setWhId(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_MODEL_ID.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setElementModelId(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_TYPE_ID.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setTypeElementId(Long.parseLong(strTemp));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVEMENT_DATE_IN.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setMovementDateIn(UtilsBusiness.stringToDate(strTemp,"dd/MM/yyyy"));
			
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_MOVEMENT_DATE_OUT.getCodeEntity());
			if(strTemp != null && strTemp.trim().length() >0)
				filters.setMovementDateOut(UtilsBusiness.stringToDate(strTemp,"dd/MM/yyyy"));
			
			//Ejecucion del metodo que genera el DTO de los totales de los elementos
			response = whElementFacade.getQuantityWarehouseElementsSummariesByFilters(filters,null).getQuantityWarehouseElementsDTO();
			
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
