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
import co.com.directv.sdii.model.pojo.ProgramStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ProgramStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ProgramStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ProgramStatus
 * @see co.com.directv.sdii.persistence.dao.config.ProgramStatusDAOLocal
 */
@Stateless(name="ProgramStatusDAOLocal",mappedName="ejb/ProgramStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProgramStatusDAO extends BaseDao implements ProgramStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ProgramStatusDAO.class);

    /**
     * Crea un ProgramStatus en el sistema
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createProgramStatus/DAOProgramStatusBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ProgramStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createProgramStatus/DAOProgramStatusBean ==");
        }
    }

    /**
     * Obtiene un programstatus con el id especificado
     * @param id - Long
     * @return - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ProgramStatus getProgramStatusByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getProgramStatusByID/DAOProgramStatusBean ==");
        Session session = super.getSession();
        try {
            StringBuffer bufferSql = new StringBuffer();
            bufferSql.append("select programstatus from ");
            bufferSql.append(ProgramStatus.class.getName());
            bufferSql.append(" programstatus where programstatus.id = :id");
            
            Query query = session.createQuery(bufferSql.toString());
            query.setLong("id", id);
            
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (ProgramStatus) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ProgramStatus por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getProgramStatusByID/DAOProgramStatusBean ==");
        }
    }

    /**
     * Actualiza un programstatus especificado
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateProgramStatus/DAOProgramStatusBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ProgramStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateProgramStatus/DAOProgramStatusBean ==");
        }

    }

    /**
     * Elimina un programstatus del sistema
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteProgramStatus/DAOProgramStatusBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ProgramStatus ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteProgramStatus/DAOProgramStatusBean ==");
        }

    }

    /**
     * Obtiene todos los programstatuss del sistema
     * @return - List<ProgramStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ProgramStatus> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOProgramStatusBean ==");
        Session session = super.getSession();

        try {
            StringBuffer bufferSql = new StringBuffer();
            bufferSql.append("from ");
            bufferSql.append(ProgramStatus.class.getName());
            
            Query query = session.createQuery(bufferSql.toString());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ProgramStatus ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOProgramStatusBean ==");
        }
    }

}
