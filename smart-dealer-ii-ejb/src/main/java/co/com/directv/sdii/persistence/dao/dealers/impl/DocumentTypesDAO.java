package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DocumentType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DocumentTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DocumentTypes
 * @see co.com.directv.sdii.model.hbm.DocumentTypesVO.hbm.xml
 */
@Stateless(name="DocumentTypesDAOLocal",mappedName="ejb/DocumentTypesDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DocumentTypesDAO extends BaseDao implements DocumentTypesDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(DocumentTypesDAO.class);

	/**
	 * Obtiene un documento por id
	 * @param id - Long
	 * @return DocumentTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DocumentType getDocumentTypesByID(Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDocumentTypesByID/DocumentTypesDAO ==");
		Session session = getSession();
		DocumentType obj = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DocumentType.class.getName());
			stringQuery.append(" dt where dt.id = :aDtId");
			Query query = session.createQuery(stringQuery.toString());
			//Query query = session.createQuery("from "+DocumentType.class.getName()+" dt where dt.id = :aDtId");
			query.setLong("aDtId", id);

			obj = (DocumentType)query.uniqueResult();

			return obj;
		} catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDocumentTypesByID/DocumentTypesDAO ==");
		}
	}	

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal#getAllDocumentTypesByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DocumentType> getAllDocumentTypesByCountryId(Long countryId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDocumentTypesByCountryId/DocumentTypesDAO ==");

		Session session = getSession();
		List<DocumentType> list = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DocumentType.class.getName() +" dt ");
			if(countryId != null && countryId >= 0L){
				stringQuery.append(" where dt.country.id = :aCountryId");
			}
			Query query = session.createQuery(stringQuery.toString());
			
			if(countryId != null && countryId >= 0L){
				query.setLong("aCountryId", countryId);
			}
			list = query.list();

			return list;
		} catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getAllDocumentTypesByCountryId/DocumentTypesDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DocumentTypesDAOLocal#getDocumentTypesByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DocumentType getDocumentTypesByCodeAndCountryId(String documentTypeCode, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDocumentTypesByCodeAndCountryId/DocumentTypesDAO ==");

		Session session = getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DocumentType.class.getName());
			stringQuery.append(" dt where dt.documentTypeCode = :aDtCode and dt.country.id = :aCountryId");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aDtCode", documentTypeCode);
			query.setLong("aCountryId", countryId);
			DocumentType dt = (DocumentType)query.uniqueResult();
			return dt;
		} catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getDocumentTypesByCodeAndCountryId/DocumentTypesDAO ==");
		}
	}
}