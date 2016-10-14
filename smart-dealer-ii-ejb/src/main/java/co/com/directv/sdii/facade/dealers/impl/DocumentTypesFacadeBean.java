package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DocumentTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad DocumentTypes 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal
 */
@Stateless(name="DocumentTypesFacadeBeanLocal",mappedName="ejb/DocumentTypesFacadeBeanLocal")
public class DocumentTypesFacadeBean implements DocumentTypesFacadeBeanLocal {
	
	@EJB(name="DocumentTypesCRUDBeanLocal",beanInterface=DocumentTypesCRUDBeanLocal.class)
	private DocumentTypesCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal#getDocumentTypesByID(java.lang.Long)
	 */
	public DocumentTypeVO getDocumentTypesByID(Long id) throws BusinessException {
		return business.getDocumentTypesByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DocumentTypesFacadeBeanLocal#getAllDocumentTypesByCountryId(java.lang.Long)
	 */
	public List<DocumentTypeVO> getAllDocumentTypesByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllDocumentTypesByCountryId(countryId);
	}
}
