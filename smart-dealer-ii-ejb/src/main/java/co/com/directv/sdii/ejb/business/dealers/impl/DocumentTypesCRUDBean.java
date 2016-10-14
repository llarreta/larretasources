package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.DocumentTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad DocumentTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal
 * 
 */
@Stateless(name="DocumentTypesCRUDBeanLocal",mappedName="ejb/DocumentTypesCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentTypesCRUDBean extends BusinessBase implements DocumentTypesCRUDBeanLocal {

	@EJB(name="DocumentTypesDAOLocal",beanInterface=DocumentTypesDAOLocal.class)
	private DocumentTypesDAOLocal dao;

     private final static Logger log = UtilsBusiness.getLog4J(DocumentTypesCRUDBean.class);

	 /**
	 * Consulta todos los DocumentTypes
	 * @return List<DocumentTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DocumentTypeVO getDocumentTypesByID(Long id) throws BusinessException {
            log.debug("== Inicia getDocumentTypesByID/DocumentTypesCRUDBean ==");
            try {
               return UtilsBusiness.copyObject(DocumentTypeVO.class,dao.getDocumentTypesByID(id));
            } catch(Throwable ex){
            	log.debug("== Error al tratar de ejecutar la operación getDocumentTypesByID/DocumentTypesCRUDBean ==");
            	throw this.manageException(ex);
            } finally {
                log.debug("== Termina getDocumentTypesByID/PensionCRUDocumentTypesCRUDBeanDBean ==");
            }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.DocumentTypesCRUDBeanLocal#getAllDocumentTypesByCountryId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DocumentTypeVO> getAllDocumentTypesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllDocumentTypesByCountryId/DocumentTypesCRUDBean ==");
        try {
           return  UtilsBusiness.convertList(dao.getAllDocumentTypesByCountryId(countryId), DocumentTypeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllDocumentTypesByCountryId/DocumentTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDocumentTypesByCountryId/DocumentTypesCRUDBean ==");
        }
	}

}
