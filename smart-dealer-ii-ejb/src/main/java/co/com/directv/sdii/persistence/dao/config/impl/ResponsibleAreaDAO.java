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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ResponsibleArea;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ResponsibleAreaDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ResponsibleArea
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ResponsibleArea
 * @see co.com.directv.sdii.persistence.dao.config.ResponsibleAreaDAOLocal
 */
@Stateless(name="ResponsibleAreaDAOLocal",mappedName="ejb/ResponsibleAreaDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ResponsibleAreaDAO extends BaseDao implements ResponsibleAreaDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ResponsibleAreaDAO.class);

    /**
     * Crea una ResponsibleArea en el sistema
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createResponsibleArea/DAOResponsibleAreaBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ResponsibleArea ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createResponsibleArea/DAOResponsibleAreaBean ==");
        }
    }

    /**
     * Obtiene un responsiblearea con el id especificado
     * @param id - Long
     * @return - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ResponsibleArea getResponsibleAreaByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getResponsibleAreaByID/DAOResponsibleAreaBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select responsiblearea from ");
        	stringQuery.append("ResponsibleArea responsiblearea ");
        	stringQuery.append("where ");
        	stringQuery.append("responsiblearea.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select responsiblearea from ResponsibleArea responsiblearea where responsiblearea.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ResponsibleArea) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ResponsibleArea por ID ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getResponsibleAreaByID/DAOResponsibleAreaBean ==");
        }
    }

    /**
     * Actualiza un responsiblearea especificado
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateResponsibleArea/DAOResponsibleAreaBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ResponsibleArea ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateResponsibleArea/DAOResponsibleAreaBean ==");
        }

    }

    /**
     * Elimina un responsiblearea del sistema
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteResponsibleArea/DAOResponsibleAreaBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ResponsibleArea ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteResponsibleArea/DAOResponsibleAreaBean ==");
        }

    }

    /**
     * Obtiene todos los responsibleareas del sistema
     * @return - List<ResponsibleArea>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ResponsibleArea> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOResponsibleAreaBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ResponsibleArea.class.getName());
        	stringQuery.append(" ra order by ra.areaName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + ResponsibleArea.class.getName() + " ra order by ra.areaName");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ResponsibleArea ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOResponsibleAreaBean ==");
        }
    }

}
