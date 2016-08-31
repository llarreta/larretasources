package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WoType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoType
 * @see co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal
 */
@Stateless(name="WoTypeDAOLocal",mappedName="ejb/WoTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoTypeDAO extends BaseDao implements WoTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoTypeDAO.class);

    /**
     * Crea una WoType en el sistema
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoType(WoType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWoType/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error createWoType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoType/DAOWoTypeBean ==");
        }
    }

    /**
     * Obtiene un wotype con el id especificado
     * @param id - Long
     * @return - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoType getWoTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoTypeByID/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select wotype from WoType wotype ");
            stringQuery.append("where ");
            stringQuery.append("wotype.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select wotype from WoType wotype where wotype.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WoType) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error getWoTypeByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoTypeByID/DAOWoTypeBean ==");
        }
    }

    /**
     * Actualiza un wotype especificado
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoType(WoType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWoType/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error updateWoType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoType/DAOWoTypeBean ==");
        }

    }

    /**
     * Elimina un wotype del sistema
     * @param obj - WoType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoType(WoType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWoType/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error deleteWoType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoType/DAOWoTypeBean ==");
        }

    }

    /**
     * Obtiene todos los wotypes del sistema
     * @return - List<WoType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WoType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            //Query query = session.createQuery("from WoType");
            //gfandino - 21/05/2011 Modificación: Se adicionar order by
            Query query = session.createQuery("from WoType wot order by wot.woTypeName");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getAllWoType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOWoTypeBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoTypeDAOLocal#getWoTypeByCode(java.lang.String)
	 */
	@Override
	public WoType getWoTypeByCode(String woTypeCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWoTypeByCode/DAOWoTypeBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WoType wot ");
            stringQuery.append("where ");
            stringQuery.append("wot.woTypeCode = :aWoType ");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from WoType wot where wot.woTypeCode = :aWoType");
            query.setString("aWoType", woTypeCode);
            return (WoType)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getWoTypeByCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoTypeByCode/DAOWoTypeBean ==");
        }
	}

}
