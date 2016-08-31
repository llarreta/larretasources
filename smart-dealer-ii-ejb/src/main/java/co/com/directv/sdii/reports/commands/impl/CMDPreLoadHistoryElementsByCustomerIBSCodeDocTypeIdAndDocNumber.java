package co.com.directv.sdii.reports.commands.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.CustomerElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumberLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Clase que genera un List<CustomerElementsDTO> invocando el metodo
 * getWarehouseElementsByCustomerIdSerialAndDatesRange de la clase
 * WarehouseElementFacadeBeanLocal.
 * 
 * Fecha de Creación: 6/09/2011
 * 
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber", mappedName = "ejb/CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber")
public class CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber extends BaseCommand implements
		ICommand,CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumberLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<CustomerElementsDTO> response=null;

	@EJB
	private WarehouseElementFacadeBeanLocal whElementFacade;

	/**
	 * Constructor: Creador Vacio
	 * @author
	 */
	public CMDPreLoadHistoryElementsByCustomerIBSCodeDocTypeIdAndDocNumber() {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {

		try {
			
			HashMap<String, String> map = getParams(args);
			String tempArgs="";
			
			WareHouseElementClientFilterRequestDTO request=new WareHouseElementClientFilterRequestDTO();
			
			tempArgs = map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_CUSTOMER_ID.getCodeEntity());
			if(tempArgs != null && tempArgs.trim().length()>0)
				request.setCustomerId(Long.parseLong(tempArgs));
			
			tempArgs = map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_SERIAL.getCodeEntity());
			if (tempArgs != null && !tempArgs.isEmpty())
				request.setSerial(tempArgs);
			
			tempArgs = map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_START_DATE.getCodeEntity());
			if(tempArgs != null && tempArgs.trim().length()>0)
				request.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(tempArgs));
			
			tempArgs = map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_END_DATE.getCodeEntity());
			if (tempArgs != null && !tempArgs.isEmpty())
				request.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(tempArgs));

			response=whElementFacade.getWarehouseElementsByCustomerIdSerialAndDatesRange(request,null).getCustomerElementDTO();
			
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
