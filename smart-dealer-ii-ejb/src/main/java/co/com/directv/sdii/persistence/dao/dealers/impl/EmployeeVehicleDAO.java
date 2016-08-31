package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeVehicle;
import co.com.directv.sdii.model.pojo.EmployeeVehicleId;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeVehicleDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeeVehicle
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeeVehicle
 * @see co.com.directv.sdii.model.hbm.EmployeeVehicle.hbm.xml
 */
@Stateless(name="EmployeeVehicleDAOLocal",mappedName="ejb/EmployeeVehicleDAOLocal")
public class EmployeeVehicleDAO extends BaseDao implements EmployeeVehicleDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(EmployeeVehicleDAO.class);
    
   /**
	 * Metodo: Crear EmployeeVehicle
	 * @param obj EmployeeVehicle
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public void createEmployeeVehicle(EmployeeVehicle obj) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicio createEmployeeVehicle/EmployeeVehicleDAO ==");
                
        try {
        	Session session = this.getSession();
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en createEmployeeVehicle ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeeVehicle/EmployeeVehicleDAO ==");
        }
    }

    /**
	 * Metodo: Consultar EmployeeVehicle por ID 
	 * @param id
	 * @return EmployeeVehicle
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public EmployeeVehicle getEmployeeVehicleByID(EmployeeVehicleId id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeeVehicleByID/EmployeeVehicleDAO ==");
        
        EmployeeVehicle obj = null;
       
        try {
        	Session session = this.getSession();
            obj = (EmployeeVehicle)session.get(EmployeeVehicle.class, id);

            return obj;
        }catch (Throwable ex) {
            log.debug("== Error en getEmployeeVehicleByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getEmployeeVehicleByID/EmployeeVehicleDAO ==");
        }
    }
       
   
   /**
	 * Metodo: Actualizar EmployeeVehicle
	 * @param obj EmployeeVehicle
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public void updateEmployeeVehicle(EmployeeVehicle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateEmployeeVehicle/EmployeeVehicleDAO ==");
        
       
        try {
        	Session session = this.getSession();
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en updateEmployeeVehicle ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina updateEmployeeVehicle/EmployeeVehicleDAO ==");
        }
        
    }

   /**
	 * Metodo: Eliminar EmployeeVehicle
	 * @param obj EmployeeVehicle
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public void deleteEmployeeVehicle(EmployeeVehicle obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeeVehicle/EmployeeVehicleDAO ==");
        
        
        try {
        	Session session = this.getSession();
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeeVehicle ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina deleteEmployeeVehicle/EmployeeVehicleDAO ==");
        }
        
    }

    /**
	 * Metodo: Consultar todos los EmployeeVehicle
	 * @return List<EmployeeVehicle>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<EmployeeVehicle> getAllEmployeeVehicle() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeeVehicle/EmployeeVehicleDAO ==");
        
        List<EmployeeVehicle> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeVehicle.class.getName());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllEmployeeVehicle ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllEmployeeVehicle/EmployeeVehicleDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	public List<EmployeeVehicle> getEmployeeVehicleByVehicleId(long vehicleId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getEmployeeVehicleByVehicleId/EmployeeVehicleDAO ==");
        
        List<EmployeeVehicle> list = null;

        try {
        	Session session = this.getSession();
        	//Query query = session.createQuery("select new "+EmployeeVehicle.class.getName()+" (empVeh.id, new "+Employee.class.getName()+"(empVeh.employee.id, empVeh.employee.firstName, empVeh.employee.lastName, empVeh.employee.address, empVeh.employee.documentNumber, empVeh.employee.hireDate), empVeh.allocationDate) from "+EmployeeVehicle.class.getName()+" empVeh where empVeh.id.vehicleId= :aVehId");
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	stringQuery.append(" (empVeh.id, empVeh.employee, empVeh.allocationDate) from ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	stringQuery.append(" empVeh where empVeh.id.vehicleId= :aVehId");
        	Query query = session.createQuery(stringQuery.toString());
        	//Query query = session.createQuery("select new "+EmployeeVehicle.class.getName()+" (empVeh.id, empVeh.employee, empVeh.allocationDate) from "+EmployeeVehicle.class.getName()+" empVeh where empVeh.id.vehicleId= :aVehId");
            query.setLong("aVehId", vehicleId);

            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeVehicleByVehicleId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getEmployeeVehicleByVehicleId/EmployeeVehicleDAO ==");
        }
    }

    public void deleteAllEmployeeVehicleByVehicleId(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteAllEmployeeVehicleByVehicleId/EmployeeVehicleDAO ==");
        

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	stringQuery.append(" empV where empV.id.vehicleId = :aVehId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from "+EmployeeVehicle.class.getName()+" empV where empV.id.vehicleId = :aVehId");
            query.setLong("aVehId", id);

            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteAllEmployeeVehicleByVehicleId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina deleteAllEmployeeVehicleByVehicleId/EmployeeVehicleDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	public EmployeeVehicle getEmployeeVehicleByEmployeeId(long employeeId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getEmployeeVehicleByEmployeeId/EmployeeVehicleDAO ==");
        
        List<EmployeeVehicle> list = null;

        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	stringQuery.append(" empVeh where empVeh.id.employeeId = :employeeId order by empVeh.allocationDate desc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("employeeId", employeeId);

            list = query.list();
            if( list != null && !list.isEmpty() )
            	return list.get(0);
            else
            	return null;
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeVehicleByEmployeeId/EmployeeVehicleDAO ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getEmployeeVehicleByEmployeeId/EmployeeVehicleDAO ==");
        }
    }

	@Override
	public void deleteAllEmployeeVehicleByEmployeeId(Long employeeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteAllEmployeeVehicleByEmployeeId/EmployeeVehicleDAO ==");
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(EmployeeVehicle.class.getName());
        	stringQuery.append(" empV where empV.id.employeeId = :anEmpId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anEmpId", employeeId);
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteAllEmployeeVehicleByEmployeeId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina deleteAllEmployeeVehicleByEmployeeId/EmployeeVehicleDAO ==");
        }
		
	}
}