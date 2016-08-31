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
import co.com.directv.sdii.model.pojo.Ibs6Status;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 *
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Ibs6Status
 *
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.model.pojo.Ibs6Status
 * @see co.com.directv.sdii.persistence.dao.config.Ibs6StatusDAOLocal
 */
@Stateless(name="Ibs6StatusDAOLocal",mappedName="ejb/Ibs6StatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Ibs6StatusDAO extends BaseDao implements Ibs6StatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(Ibs6StatusDAO.class);

    /**
     * Crea un Ibs6Status en el sistema
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createIbs6Status/DAOIbs6StatusBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando Ibs6Status ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina createIbs6Status/Ibs6StatusDAO ==");
        }
    }

    /**
     * Obtiene un Ibs6Status con el id especificado
     * @param id - Long
     * @return - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ibs6Status getIbs6StatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getIbs6StatusByID/Ibs6StatusDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select ibs6status from ");
            stringQuery.append(Ibs6Status.class.getName());
            stringQuery.append(" ibs6status where ibs6status.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select ibs6status from " + Ibs6Status.class.getName() + " ibs6status where ibs6status.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (Ibs6Status) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando Ibs6Status por ID ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getIbs6StatusByID/Ibs6StatusDAO ==");
        }
    }

    /**
     * Actualiza un Ibs6Status especificado
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateIbs6Status/Ibs6StatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error actualizando Ibs6Status ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateIbs6Status/Ibs6StatusDAO ==");
        }

    }

    /**
     * Elimina un Ibs6Status del sistema
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteIbs6Status/Ibs6StatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(Ibs6Status.class.getName());
            stringQuery.append(" ibs6 where ibs6.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Ibs6Status.class.getName() + " ibs6 where ibs6.id = :id");
            query.setLong("id", obj.getId());

            query.executeUpdate();
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error eliminando Ibs6Status  ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina deleteIbs6Status/Ibs6StatusDAO ==");
        }

    }

    /**
     * Obtiene todos los Ibs6Statuss del sistema
     * @return - List<Ibs6Status>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Ibs6Status> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/Ibs6StatusDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Ibs6Status.class.getName());
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Ibs6Status.class.getName());
            List<Ibs6Status> list = query.list();
			return list;
        }catch (Throwable ex) {
            log.debug("== Error consultando todos los Ibs6Status  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/Ibs6StatusDAO ==");
        }
    }

	@Override
	public Ibs6Status getIbs6StatusByIbsStateCode(String ibsStateCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getIbs6StatusByIbsStateCode/Ibs6StatusDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select ibs6status from ");
            stringQuery.append(Ibs6Status.class.getName());
            stringQuery.append(" ibs6status where ibs6status.ibs6StateCode = :ibsStateCode");
            Query query = session.createQuery(stringQuery.toString()); 
            query.setString("ibsStateCode", ibsStateCode);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (Ibs6Status) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error getIbs6StatusByIbsStateCode/Ibs6StatusDAO ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getIbs6StatusByIbsStateCode/Ibs6StatusDAO ==");
        }
	}

}
