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
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeeMediaContact
 * 
 * 
 * Fecha de Creacion: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeMediaContact
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal
 */
@Stateless(name="EmployeeMediaContactDAOLocal",mappedName="ejb/EmployeeMediaContactDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeMediaContactDAO extends BaseDao implements EmployeeMediaContactDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeMediaContactDAO.class);

    /**
     * Crea un tipo de contacto del empleado
     * @param obj - EmployeeMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createEmployeeMediaContact/EmployeeMediaContactDAO ==");
        Session session = getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeMediaContact/EmployeeMediaContactDAO ==");
        }
    }

    /**
     * Obtiene un tipo de contacto de un empleado por el id especificado
     * @param id - Long
     * @return - EmployeeMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeMediaContact getEmployeeMediaContactByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeMediaContactByID/EmployeeMediaContactDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new " );
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" (emc.mediaContactType, emc.mediaContactValue, emc.id) from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" emc where emc.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select new " + EmployeeMediaContact.class.getName() + " (emc.mediaContactType, emc.mediaContactValue, emc.id) from " + EmployeeMediaContact.class.getName() + " emc where emc.id = :id");
            query.setLong("id", id);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (EmployeeMediaContact) obj;

        } catch (Throwable ex) {
            log.debug("== Error consultando el EmployeeMediaContact por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeMediaContactByID/EmployeeMediaContactDAO ==");
        }
    }

    /**
     * Actualiza un tipo de contacto
     * @param obj - EmployeeMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateEmployeeMediaContact/EmployeeMediaContactDAO ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeMediaContact/EmployeeMediaContactDAO ==");
        }

    }

    /**
     * Elimina un tipo de contacto
     * @param obj - EmployeeMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeeMediaContact/EmployeeMediaContactDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" emc where emc.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + EmployeeMediaContact.class.getName() + " emc where emc.id = :id");
            query.setLong("id", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeMediaContact/EmployeeMediaContactDAO ==");
        }

    }

    /**
     * Obtiene todos los tipos de contacto
     * @param obj - EmployeeMediaContact
     * @return - List<EmployeeMediaContact>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeMediaContact> getAllEmployeeMediaContact() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeMediaContact/EmployeeMediaContactDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	List<EmployeeMediaContact> list = session.createQuery(stringQuery.toString()).list();
            //List<EmployeeMediaContact> list = session.createQuery("from " + EmployeeMediaContact.class.getName()).list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeMediaContact/EmployeeMediaContactDAO ==");
        }
    }

    /**
     * Obtiene todos los tipos de contacto por un empleado especificado
     * @param obj - EmployeeMediaContact
     * @return - List<EmployeeMediaContact>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeMediaContact> getEmployeeMediaContactByEmployeeId(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeMediaContact/EmployeeMediaContactDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append("(emc.mediaContactType, emc.mediaContactValue, emc.id) from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" emc where emc.employee.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select new " + EmployeeMediaContact.class.getName() + "(emc.mediaContactType, emc.mediaContactValue, emc.id) from " + EmployeeMediaContact.class.getName() + " emc where emc.employee.id = :id");
            query.setLong("id", id);

            List<EmployeeMediaContact> list = query.list();
			return list;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeMediaContact/EmployeeMediaContactDAO ==");
        }
    }

        /**
     * Elimina los tipos de contacto pertenecientes a  un empleado
     * @param employeeId - Long
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeMediaContactByEmployeeId(Long employeeId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeeMediaContactByEmployeeId/EmployeeMediaContactDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" emc where emc.employee.id = :id");
            Query query = session.createQuery("delete from " + EmployeeMediaContact.class.getName() + " emc where emc.employee.id = :id");
            query.setLong("id", employeeId);
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el EmployeeMediaContact ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeMediaContactByEmployeeId/EmployeeMediaContactDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeMediaContactDAOLocal#getEmployeeMediaContactByEmployeeIdAndMediaContCode(java.lang.Long, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmployeeMediaContact getEmployeeMediaContactByEmployeeIdAndMediaContCode(Long employeeId, String mediaContactType) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeeMediaContactByEmployeeIdAndMediaContCode/EmployeeMediaContactDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeMediaContact.class.getName());
        	stringQuery.append(" emc where emc.employee.id = :employeeId");
        	stringQuery.append(" and emc.mediaContactType.mediaCode = :mediaContactType");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("employeeId", employeeId);
            query.setString("mediaContactType", mediaContactType);
            List response = query.list();
            if( response != null && !response.isEmpty() )
            	return (EmployeeMediaContact) response.get(0);
            else 
            	return null;            
        } catch (Throwable ex) {
            log.debug("== Error getEmployeeMediaContactByEmployeeIdAndMediaContCode/EmployeeMediaContactDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeMediaContactByEmployeeIdAndMediaContCode/EmployeeMediaContactDAO ==");
        }
	}
}
