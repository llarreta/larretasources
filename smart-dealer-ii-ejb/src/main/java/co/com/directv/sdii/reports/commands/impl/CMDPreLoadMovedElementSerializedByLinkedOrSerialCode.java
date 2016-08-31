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
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MovedElementSerializedDTO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDPreLoadMovedElementSerializedByLinkedOrSerialCodeLocal;
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
@Stateless(name = "CMDPreLoadMovedElementSerializedByLinkedOrSerialCode", mappedName = "ejb/CMDPreLoadMovedElementSerializedByLinkedOrSerialCode")
public class CMDPreLoadMovedElementSerializedByLinkedOrSerialCode extends BaseCommand implements
		ICommand,CMDPreLoadMovedElementSerializedByLinkedOrSerialCodeLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<MovedElementSerializedDTO> response=null;

	@EJB
	private WarehouseElementFacadeBeanLocal whElementFacade;

	public CMDPreLoadMovedElementSerializedByLinkedOrSerialCode() {
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.reports.commands.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {

		try {
			
			HashMap<String, String> map = getParams(args);
			String serialCodeElement = map
					.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_SERIALCODE_OR_LINK_SERIALCODE.getCodeEntity());
			
			String strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_PARAM_COUNTRY_ID.getCodeEntity());
			Long countryId = null;
			if(strTemp!=null && strTemp.trim().length()>0)
			     countryId = Long.valueOf(strTemp);
			response=whElementFacade.getMovedWareHouseElementSerializedByLinkedOrSerialCode(serialCodeElement,countryId,null).getMovedElementSerialized();
			
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
