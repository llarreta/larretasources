package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetReferenceElementItemsByReferenceIDAndNotSerLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * 
 * Comando encargado de realizar la consulta de inconsistencias de una remision para generar el excel
 * 
 * Fecha de Creación: 14/10/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDGetReferenceElementItemsByReferenceIDAndNotSer", mappedName = "ejb/CMDGetReferenceElementItemsByReferenceIDAndNotSer")
public class CMDGetReferenceElementItemsByReferenceIDAndNotSer extends BaseCommand implements ICommand,CMDGetReferenceElementItemsByReferenceIDAndNotSerLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
    private ReferenceElementItemFacadeBeanLocal ejbRefElement;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			Long refID = null; 
			HashMap<String, String> map = getParams(args);
			String strIdReference = map.get("refId");
			if (strIdReference != null && !strIdReference.isEmpty()){
				refID = Long.parseLong(strIdReference) ;
			}
			ReferenceElementItemsResponse reponse = ejbRefElement.getReferenceElementItemsByReferenceIDAndSerOrNotSer(refID,null,false) ;
			if( reponse != null )
				return (List<T>) reponse.getReferenceElementItemsVO();
			else
				return null;
		} catch (Exception e) {
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
