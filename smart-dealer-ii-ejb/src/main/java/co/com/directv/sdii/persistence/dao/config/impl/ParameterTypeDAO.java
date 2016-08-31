package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ParameterType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ParameterTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ParameterType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ParameterType
 * @see co.com.directv.sdii.persistence.dao.config.ParameterTypeDAOLocal
 */
@Stateless(name="ParameterTypeDAOLocal",mappedName="ejb/ParameterTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParameterTypeDAO extends BaseDao implements ParameterTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ParameterTypeDAO.class);

    /**
     * Crea un parametro en el sistema
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createParameterType/DAOParameterTypeBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (HibernateException ex) {
            log.debug("== Error creando el ParameterType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createParameterType/DAOParameterTypeBean ==");
        }
    }

    /**
     * Obtiene un parametertype con el id especificado
     * @param id - Long
     * @return - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ParameterType getParameterTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getParameterTypeByID/DAOParameterTypeBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select parametertype from ");
        	stringQuery.append("ParameterType parametertype ");
        	stringQuery.append("parametertype.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select parametertype from ParameterType parametertype where parametertype.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ParameterType) obj;
            }
            return null;
        } catch (HibernateException ex) {
            log.debug("== Error consultando el ParameterType por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getParameterTypeByID/DAOParameterTypeBean ==");
        }
    }

    /**
     * Actualiza un parametertype especificado
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateParameterType/DAOParameterTypeBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (HibernateException ex) {
            log.debug("== Error actualizando el ParameterType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateParameterType/DAOParameterTypeBean ==");
        }

    }

    /**
     * Elimina un parametertype del sistema
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteParameterType/DAOParameterTypeBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (HibernateException ex) {
            log.debug("== Error borrando el ParameterType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteParameterType/DAOParameterTypeBean ==");
        }

    }

    /**
     * Obtiene todos los parametertypes del sistema
     * @return - List<ParameterType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ParameterType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOParameterTypeBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from ParameterType");
            return query.list();
        } catch (HibernateException ex) {
            log.debug("== Error borrando el ParameterType ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOParameterTypeBean ==");
        }
    }

}
