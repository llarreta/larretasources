package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.RefRecieveData;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.RefRecieveDataDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad RefRecieveData
 * 
 * Fecha de Creación: 18/08/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="RefRecieveDataDAOLocal",mappedName="ejb/RefRecieveDataDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RefRecieveDataDAO extends BaseDao implements RefRecieveDataDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(RefRecieveDataDAO.class);

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefRecieveDataDAOLocal#getRefRecieveDataByReferenceId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public RefRecieveData getRefRecieveDataByReferenceId(Long referenceId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRefRecieveDataByReferenceId/RefRecieveDataDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(RefRecieveData.class.getName());
        	stringQuery.append(" entity where entity.reference.id = :referenceId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("referenceId", referenceId);
        	return (RefRecieveData) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error getRefRecieveDataByReferenceId/RefRecieveDataDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRefRecieveDataByReferenceId/RefRecieveDataDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.RefRecieveDataDAOLocal#createRefRecieveData(co.com.directv.sdii.model.pojo.RefRecieveData)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public RefRecieveData createRefRecieveData(RefRecieveData obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio RefRecieveData/RefRecieveDataDAO ==");
        Session session = super.getSession();
        try {
        	session.save(obj);
			this.doFlush(session);
			return obj;
        } catch (Throwable ex) {
            log.debug("== Error RefRecieveData/RefRecieveDataDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina RefRecieveData/RefRecieveDataDAO ==");
        }
	}


}
