package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeRol;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeRolDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeeRol
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeRol
 * @see co.com.directv.sdii.model.hbm.EmployeeRol.hbm.xml
 */
@Stateless(name="EmployeeRolDAOLocal",mappedName="ejb/EmployeeRolDAOLocal")
public class EmployeeRolDAO extends BaseDao implements EmployeeRolDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeRolDAO.class);
    

    /**
     * Metodo: Consultar EmployeeRol por ID
     * @param id
     * @return EmployeeRol
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    public EmployeeRol getEmployeeRolByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeRolByID/EmployeeRolDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeRol.class.getName());
        	stringQuery.append(" employeeRol where employeeRol.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeRol.class.getName() + " employeeRol where employeeRol.id = :id");
            query.setString("id", id.toString());
            Object obj = query.uniqueResult();
            if (obj != null && obj instanceof EmployeeRol) {
                return (EmployeeRol) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeRolByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRolByID/EmployeeRolDAO ==");
        }
    }

    /**
     * Metodo: Consultar EmployeeRol por Codigo
     * @param code
     * @return EmployeeRol
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    public EmployeeRol getEmployeeRolByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeRolByCode/EmployeeRolDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeRol.class.getName());
        	stringQuery.append(" employeeRol where employeeRol.rolCode = :rolCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeRol.class.getName() + " employeeRol where employeeRol.rolCode = :rolCode");
            query.setString("rolCode", code);
            Object obj = query.uniqueResult();
            if (obj != null && obj instanceof EmployeeRol) {
                return (EmployeeRol) obj;
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeRolByCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeRolByCode/EmployeeRolDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los EmployeeRol
     * @return List<EmployeeRol>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	public List<EmployeeRol> getAllEmployeeRol() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeRol/EmployeeRolDAO ==");
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeRol.class.getName());
            Query query = session.createQuery("from " + EmployeeRol.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllEmployeeRol ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeRol/EmployeeRolDAO ==");
        }
    }
}
