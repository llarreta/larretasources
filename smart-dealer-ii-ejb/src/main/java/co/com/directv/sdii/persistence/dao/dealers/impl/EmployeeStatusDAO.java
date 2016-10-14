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
import co.com.directv.sdii.model.pojo.EmployeeStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeStatusDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeeStatus
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeStatus
 * @see co.com.directv.sdii.model.hbm.EmployeeStatus.hbm.xml
 */
@Stateless(name="EmployeeStatusDAOLocal",mappedName="ejb/EmployeeStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeStatusDAO extends BaseDao implements EmployeeStatusDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeeStatusDAO.class);
   

    /**
     * Metodo: Consultar todos los EmployeeStatus
     * @return List<EmployeeStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeStatus> getAllEmployeeStatus() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeStatus/EmployeeStatusDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeStatus.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("from " + EmployeeStatus.class.getName()).list();

        } catch (Throwable ex) {
            log.debug("== Error en getAllEmployeeStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeStatus/EmployeeStatusDAO ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeStatus getEmployeeStatusById(Long statusId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeStatus/EmployeeStatusDAO ==");
        
        EmployeeStatus obj = null;
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeStatus.class.getName());
        	stringQuery.append(" es where es.id = :aEmpSId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeStatus.class.getName() + " es where es.id = :aEmpSId");
            query.setLong("aEmpSId", statusId);

            obj = (EmployeeStatus) query.uniqueResult();

            return obj;

        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeStatusById ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeeStatus/EmployeeStatusDAO ==");
        }
    }
    
    /**
     * Obtiene un status de empleado por codigo
     * @param code Codigo de estado de empleado a buscar
     * @return EmployeeStatus Estado de un empleado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EmployeeStatus getEmployeeStatusByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getEmployeeStatusByCode/EmployeeStatusDAO ==");
        
        EmployeeStatus obj = null;
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeStatus.class.getName());
        	stringQuery.append(" es where es.statusCode = :statusCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeStatus.class.getName() + " es where es.statusCode = :statusCode");
            query.setString("statusCode", code);

            obj = (EmployeeStatus) query.uniqueResult();

            return obj;

        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeStatusByCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeStatusByCode/EmployeeStatusDAO ==");
        }
    }
}
