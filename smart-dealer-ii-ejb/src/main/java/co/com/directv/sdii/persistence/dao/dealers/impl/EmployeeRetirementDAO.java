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
import co.com.directv.sdii.model.pojo.EmployeeRetirement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeRetirementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeeRetirement
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeRetirement
 * @see co.com.directv.sdii.model.hbm.EmployeeRetirement.hbm.xml
 */
@Stateless(name="EmployeeRetirementDAOLocal",mappedName="ejb/EmployeeRetirementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeRetirementDAO extends BaseDao implements EmployeeRetirementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeRetirementDAO.class);
    

    /**
     * Metodo: Crear EmployeeRetirement
     * @param obj EmployeeRetirement
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeeRetirement(EmployeeRetirement employeeRetirement) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createEmployeeRetirement/EmployeeRetirementDAO ==");
        try {         
        	Session session = this.getSession();

            session.save(employeeRetirement);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el createEmployeeRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeRetirement/EmployeeRetirementDAO ==");
        }
    }

    /**
     * Metodo: Consultar EmployeeRetirement por ID
     * @param id - Long
     * @return EmployeeRetirement
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeRetirement getEmployeeRetirementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeRetirementByID/EmployeeRetirementDAO ==");
        
        try {
        	Session session = this.getSession();
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(EmployeeRetirement.class.getName());
            stringQuery.append(" empRet where empRet.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+EmployeeRetirement.class.getName()+" empRet where empRet.id = :id");
            query.setString("id", id.toString());
            Object obj = query.uniqueResult();

            if (obj != null && obj instanceof EmployeeRetirement) {
                return (EmployeeRetirement) obj;
            }

            return null;
        } catch (Throwable ex) {
            log.debug("== Error en el getEmployeeRetirementByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRetirementByID/EmployeeRetirementDAO ==");
        }
    }

    /**
     * Metodo: Actualizar EmployeeRetirement
     * @param obj EmployeeRetirement
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeeRetirement(EmployeeRetirement obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateEmployeeRetirement/EmployeeRetirementDAO ==");
       

        try {
        	Session session = this.getSession();
            
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en updateEmployeeRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeeRetirement/EmployeeRetirementDAO ==");
        }

    }

    /**
     * Metodo: Eliminar EmployeeRetirement
     * @param obj EmployeeRetirement
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeeRetirement(EmployeeRetirement employeeRetirement) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeeRetirement/EmployeeRetirementDAO ==");       

        try {
        	 Session session = this.getSession();
           
            session.delete(employeeRetirement);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeeRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeRetirement/EmployeeRetirementDAO ==");
        }

    }

    /**
     * Metodo: Consultar todos los EmployeeRetirement
     * @return List<EmployeeRetirement>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeRetirement> getAllEmployeeRetirement() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeRetirement/EmployeeRetirementDAO ==");
       

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeRetirement.class.getName());
        	stringQuery.append(" empRet");
        	List<EmployeeRetirement> list = session.createQuery(stringQuery.toString()).list();
            //List<EmployeeRetirement> list = session.createQuery("from "+EmployeeRetirement.class.getName()+" empRet").list();
			return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllEmployeeRetirement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeRetirement/EmployeeRetirementDAO ==");
        }
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEmployeeRetirementByEmployeeId(Long employeeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteEmployeeRetirement/EmployeeRetirementDAO ==");
		
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(EmployeeRetirement.class.getName());
        	stringQuery.append(" emr where emr.employee.id = :aEmplId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + EmployeeRetirement.class.getName() + " emr where emr.employee.id = :aEmplId");
            query.setLong("aEmplId", employeeId);
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeeRetirementByEmployeeId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeRetirement/EmployeeRetirementDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeeRetirementDAOLocal#getEmployeeRetirementByEmployee(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<EmployeeRetirement> getEmployeeRetirementByEmployee(
			Long idEmployee) throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicia getEmployeeRetirementByEmployee/EmployeeRetirementDAO ==");
	       

	        try {
	        	Session session = this.getSession();
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(EmployeeRetirement.class.getName());
	        	stringQuery.append(" empRet");
	        	stringQuery.append(" where empRet.employee.id =  :idEmployee order by empRet.retirementDate desc");
	        	Query query = session.createQuery(stringQuery.toString());
	        	query.setLong("idEmployee", idEmployee);
	            //List<EmployeeRetirement> list = session.createQuery("from "+EmployeeRetirement.class.getName()+" empRet").list();
				return query.list();
	        } catch (Throwable ex) {
	            log.debug("== Error en getEmployeeRetirementByEmployee ==");
	            throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getEmployeeRetirementByEmployee/EmployeeRetirementDAO ==");
	        }
	}
}
