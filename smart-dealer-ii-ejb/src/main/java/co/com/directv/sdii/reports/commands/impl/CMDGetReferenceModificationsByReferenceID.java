package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetReferenceModificationsByReferenceIDLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * 
 * Comando encargado de consultar las modificaciones realizadas a una remision 
 * 
 * Fecha de Creación: 13/10/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name = "CMDGetReferenceModificationsByReferenceID", mappedName = "ejb/CMDGetReferenceModificationsByReferenceID")
public class CMDGetReferenceModificationsByReferenceID extends BaseCommand implements ICommand,CMDGetReferenceModificationsByReferenceIDLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ReferenceFacadeBeanLocal referenceFacadeBean;

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ReferenceVO referenceVO = new ReferenceVO() ; 
			HashMap<String, String> map = getParams(args);
			String strIdReference = map.get("refId");
			if (strIdReference != null && !strIdReference.isEmpty()){
				referenceVO.setId( Long.parseLong(strIdReference) );
			}	
			// MODIFICACIONES
			List<ReferenceModificationVO> modifications = referenceFacadeBean.getReferenceModificationsByReferenceID(referenceVO);
			return (List<T>) modifications;
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
