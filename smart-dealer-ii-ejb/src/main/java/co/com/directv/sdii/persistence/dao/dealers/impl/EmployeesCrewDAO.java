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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de EmployeesCrew
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.EmployeesCrew
 * @see co.com.directv.sdii.model.hbm.EmployeesCrew.hbm.xml
 */
@Stateless(name="EmployeesCrewDAOLocal",mappedName="ejb/EmployeesCrewDAOLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeesCrewDAO extends BaseDao implements EmployeesCrewDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(EmployeesCrewDAO.class);

    /**
     * Metodo: Crear EmployeesCrew
     * @param obj EmployeesCrew
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createEmployeesCrew/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en createEmployeesCrew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeesCrew/EmployeesCrewDAO ==");
        }
    }

    /**
     * Metodo: Consultar EmployeesCrew por ID
     * @param id
     * @return EmployeesCrew
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeCrew> getEmployeesCrewByCrewID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeesCrewByID/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EmployeeCrew.class.getName()+" empCrew ");
        	stringQuery.append(" inner join fetch empCrew.crew crew ");
        	stringQuery.append(" inner join fetch crew.vehicle veh ");
        	stringQuery.append(" inner join fetch empCrew.employee employee ");
        	stringQuery.append(" inner join fetch employee.position pos ");
        	stringQuery.append("  where empCrew.id.crewId = :aEmpCrewId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + EmployeeCrew.class.getName() + " empCrew where empCrew.id.crewId = :aEmpCrewId");
            query.setLong("aEmpCrewId", id);

            List<EmployeeCrew> employeeCrewList = query.list();
            return employeeCrewList;            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByCrewID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByID/EmployeesCrewDAO ==");
        }
    }

    /**
     * Metodo: Actualizar EmployeesCrew
     * @param obj EmployeesCrew
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateEmployeesCrew/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en updateEmployeesCrew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeesCrew/EmployeesCrewDAO ==");
        }

    }

    /**
     * Metodo: Eliminar EmployeesCrew
     * @param obj EmployeesCrew
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeesCrew(EmployeeCrew obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeesCrew/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeesCrew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeesCrew/EmployeesCrewDAO ==");
        }

    }
    
    /**
     * Metodo: Eliminar EmployeesCrew por el id de cuadrilla
     * @param obj EmployeesCrew
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeesCrewByCrewId(final Long crewId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteEmployeesCrewByCrewId/EmployeesCrewDAO ==");
        
        try {
        	Session session = super.getSession();
            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append(" delete from  ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.id.crewId = :crewId ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("crewId", crewId);
            query.executeUpdate();
            
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeesCrewByCrewId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeesCrewByCrewId/EmployeesCrewDAO ==");
        }

    }

    /**
     * Metodo: Consultar todos los EmployeesCrew
     * @return List<EmployeesCrew>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeCrew> getAllEmployeesCrew() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEmployeesCrew/EmployeesCrewDAO ==");
        
        List<EmployeeCrew> list = null;

        try {
        	Session session = this.getSession();
        	StringBuffer queryBuffer=new StringBuffer();
        	queryBuffer.append("from ");
        	queryBuffer.append(EmployeeCrew.class.getName());
        	Query query = session.createQuery(queryBuffer.toString());
            //Query query = session.createQuery("from " + EmployeeCrew.class.getName());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllEmployeesCrew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeesCrew/EmployeesCrewDAO ==");
        }
    }
    
    /**
     * Elimina los empleados relacionados a una cuadrilla
     * @param idCrew Id de la cuadrilla
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteEmployeesByCrewId(Long idCrew) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia deleteEmployeesByCrewId/EmployeesCrewDAO ==");
               
        try {
        	Session session = this.getSession();    
        	StringBuffer queryBuffer=new StringBuffer();
        	queryBuffer.append("delete from ");
        	queryBuffer.append(EmployeeCrew.class.getName());
        	queryBuffer.append(" ec where ec.id.crewId = :id");
        	Query query = session.createQuery(queryBuffer.toString());
            //Query query = session.createQuery("delete from " + EmployeeCrew.class.getName() + " ec where ec.id.crewId = :id");
            query.setLong("id", idCrew);
            
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeesByCrewId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeesByCrewId/EmployeesCrewDAO ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un listado de empleados por
     * cuadrilla filtrando por el condigo del dealer.
     * @param Long dealerId
     * @param String isResponsible
     * @return List<EmployeeCrew>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeCrew> getEmployeesCrewByDealerID(Long dealerId,String isResponsible) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeesCrewByDealerID/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append(" select new ");    
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" (e_crew.crew,e_crew.employee) ");  
            queryBuffer.append(" from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" e_crew ,");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew ");
            queryBuffer.append(" WHERE e_crew.id.crewId = crew.id and e_crew.isResponsible = ? and crew.dealer.id = ? ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString(0, isResponsible);
            query.setLong(1, dealerId);
            

            List<EmployeeCrew> employeeCrewList = query.list();
            return employeeCrewList;            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByDealerID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByDealerID/EmployeesCrewDAO ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un listado de empleados por
     * cuadrilla filtrando por el codigo del dealer y el tipo de cuadrilla
     * @param Long dealerId
     * @param String isResponsible
     * @param Long crewType
     * @return List<EmployeeCrew>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeCrew> getEmployeesCrewByDealerIDAndCrewType(Long dealerId,String isResponsible , Long crewType) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeesCrewByDealerIDAndCrewType/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append(" select new ");    
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" (e_crew.crew,e_crew.employee) ");  
            queryBuffer.append(" from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" e_crew ,");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew ");
            queryBuffer.append(" WHERE e_crew.id.crewId = crew.id and e_crew.isResponsible = ? and crew.dealer.id = ? and crew.crewType.id = ? and crew.crewStatus.statusCode = ?");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString(0, isResponsible);
            query.setLong(1, dealerId);
            query.setLong(2, crewType);
            query.setString(3, CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            

            List<EmployeeCrew> employeeCrewList = query.list();
            return employeeCrewList;            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByDealerIDAndCrewType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByDealerIDAndCrewType/EmployeesCrewDAO ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un listado de Empleados por cuadrilla
     * filtrando por nombre del empleado, dealer, tipo de cuadrilla
     * y estado de la cuadrilla
     * @param Long dealerId
     * @param String isResponsible
     * @param Long crewType
     * @param Long responsibleName
     * @return List<EmployeeCrew>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author Joan Lopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<EmployeeCrew> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(Long dealerId,String isResponsible , Long crewType, String responsibleName) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEmployeesCrewByDealerIdAndCrewTypeAndResponsible/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where");          
            queryBuffer.append(" lower(employeeCrew.employee.firstName)|| ' ' ||lower(employeeCrew.employee.lastName)  LIKE '%'||:name||'%'  and employeeCrew.isResponsible = :isResponsible and employeeCrew.crew.dealer.id = :dealerId and employeeCrew.crew.crewType.id = :crewType and employeeCrew.crew.crewStatus.statusCode = :crewStatus");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("isResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setString("name", responsibleName.toLowerCase());
            query.setLong("dealerId", dealerId);           
            query.setLong("crewType", crewType);
            query.setString("crewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());       
            

            List<EmployeeCrew> employeeCrewList = query.list();
            return employeeCrewList;            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByDealerIdAndCrewTypeAndResponsible ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByDealerIdAndCrewTypeAndResponsible/EmployeesCrewDAO ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getEmployeeResponsibleByCrewId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@Override
	public Employee getEmployeeResponsibleByCrewId(Long crewId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getEmployeeResponsibleByCrewId/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append("select employeeCrew.employee from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.isResponsible = :anIsResponsible and employeeCrew.crew.id = :aCrewId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setLong("aCrewId", crewId);

            List<Employee> employeeCrewList = query.list();
            if(employeeCrewList.isEmpty()){
            	return null;
            }
            
            return employeeCrewList.get(0);            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeResponsibleByCrewId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeResponsibleByCrewId/EmployeesCrewDAO ==");
        }
	}
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getEmployeeNotResponsibleByCrewId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeNotResponsibleByCrewId(Long crewId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getEmployeeResponsibleByCrewId/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append("select employeeCrew.employee from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.isResponsible = :anIsResponsible and employeeCrew.crew.id = :aCrewId ");
            queryBuffer.append(" and employeeCrew.crew.id = :aCrewId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
            query.setLong("aCrewId", crewId);

            List<Employee> employeeCrewList = query.list();
            if(employeeCrewList.isEmpty()){
            	return null;
            }
            
            return employeeCrewList;            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeResponsibleByCrewId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeResponsibleByCrewId/EmployeesCrewDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getEmployeeWoutResponsibleByCrewId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Employee getEmployeeWoutResponsibleByCrewId(Long crewId)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getEmployeeWoutResponsibleByCrewId/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();

            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append("select employeeCrew.employee from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.isResponsible <> :anIsResponsible and employeeCrew.crew.id = :aCrewId ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setLong("aCrewId", crewId);

            List<Employee> employeeCrewList = query.list();
            if(employeeCrewList.isEmpty()){
            	return null;
            }
            
            return employeeCrewList.get(0);            
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeeWoutResponsibleByCrewId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeeWoutResponsibleByCrewId/EmployeesCrewDAO ==");
        }
	    	
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getAllResponsableEmployeeCrewByCountryAndDealerId(java.lang.Long, java.lang.Long)
     */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeCrew> getAllResponsableEmployeeCrewByCountryAndDealerId(Long countryId,Long dealerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
        try {
        	Session session = this.getSession();
        	boolean isDealerId = dealerId != null && dealerId.longValue() > 0;
            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.isResponsible = :anIsResponsible and employeeCrew.crew.dealer.postalCode.city.state.country.id = :countryId and employeeCrew.crew.crewStatus.statusCode = :activeCrewStatus ");
            if( isDealerId ){
            	queryBuffer.append("and employeeCrew.crew.dealer.id = :dealerId ");
            }
            queryBuffer.append(" order by employeeCrew.crew.dealer.dealerName asc ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setString("activeCrewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            query.setLong("countryId", countryId);
            if( isDealerId ){
            	query.setLong("dealerId", dealerId);
            }
            return query.list();            
        } catch (Throwable ex) {
            log.debug("== Error en getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
        }
	}

	@Override
	public void deleteEmployeeCrewsByEmployeeId(Long employeeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteEmployeeCrewsByEmployeeId/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();    
        	StringBuffer queryBuffer=new StringBuffer();
        	queryBuffer.append("delete from ");
        	queryBuffer.append(EmployeeCrew.class.getName());
        	queryBuffer.append(" ec where ec.id.employeeId = :anEmpId");
        	Query query = session.createQuery(queryBuffer.toString());
            query.setLong("anEmpId", employeeId);
            
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteEmployeeCrewsByEmployeeId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeeCrewsByEmployeeId/EmployeesCrewDAO ==");
        }
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EmployeeCrew> getEmployeesCrewByEmpId(Long employeeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getEmployeesCrewByEmpId/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();    
        	StringBuffer queryBuffer=new StringBuffer();
        	queryBuffer.append("from ");
        	queryBuffer.append(EmployeeCrew.class.getName());
        	queryBuffer.append(" ec where ec.id.employeeId = :anEmpId");
        	Query query = session.createQuery(queryBuffer.toString());
            query.setLong("anEmpId", employeeId);
            
            List<EmployeeCrew> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByEmpId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByEmpId/EmployeesCrewDAO ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getEmployeesCrewByEmpIdAndCrewStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeCrew> getEmployeesCrewByEmpIdAndCrewStatus(
			Long employeeId, String codeStatus) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getEmployeesCrewByEmpIdAndCrewStatus/EmployeesCrewDAO ==");
        
        try {
        	Session session = this.getSession();    
        	StringBuffer queryBuffer=new StringBuffer();
        	queryBuffer.append("from ");
        	queryBuffer.append(EmployeeCrew.class.getName());
        	queryBuffer.append(" ec where ec.id.employeeId = :employeeId ");
        	queryBuffer.append(" and  ec.crew.crewStatus.statusCode = :codeStatus ");
        	Query query = session.createQuery(queryBuffer.toString());
            query.setLong("employeeId", employeeId);
            query.setString("codeStatus", codeStatus);
            
            List<EmployeeCrew> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error en getEmployeesCrewByEmpIdAndCrewStatus ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByEmpIdAndCrewStatus/EmployeesCrewDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getActiveTechniciansQtyByDealerIdAndActiveCrew(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Long getActiveTechniciansQtyByDealerIdAndActiveCrew(Long dealerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveTechniciansQtyByDealerIdAndActiveCrew/EmployeesCrewDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select count(ec.id.employeeId) from ");
        	stringQuery.append(EmployeeCrew.class.getName());
        	stringQuery.append(" ec where ec.crew.dealer.id = :dealerId and ec.employee.employeeStatus.statusCode = :codeStatus and ec.crew.crewStatus.statusCode = :crewStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerId", dealerId);
            query.setString("codeStatus", CodesBusinessEntityEnum.CODIGO_EMPLOYEE_STATUS_ACTIVE.getCodeEntity());
            query.setString("crewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());

            Long qty = (Long)query.uniqueResult();
			return qty;
        } catch (Throwable ex) {
            log.debug("== Error getActiveTechniciansQtyByDealerIdAndActiveCrew/EmployeesCrewDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveTechniciansQtyByDealerIdAndActiveCrew/EmployeesCrewDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal#getAllResponsableEmployeeCrewByCountryAndDealerIds(java.lang.Long, java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmployeeCrew> getAllResponsableEmployeeCrewByCountryAndDealerIds(Long countryId,List<Long> dealerIds) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
        try {
        	Session session = this.getSession();
        	boolean isDealerId = dealerIds != null && !dealerIds.isEmpty();
            StringBuffer queryBuffer=new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(EmployeeCrew.class.getName());
            queryBuffer.append(" employeeCrew where employeeCrew.isResponsible = :anIsResponsible and employeeCrew.crew.dealer.postalCode.city.state.country.id = :countryId and employeeCrew.crew.crewStatus.statusCode = :activeCrewStatus ");
            if( isDealerId ){
            	queryBuffer.append("and employeeCrew.crew.dealer.id in ( " + UtilsBusiness.longListToString(dealerIds, ",") +" ) ");
            }
            queryBuffer.append(" order by employeeCrew.crew.dealer.dealerName asc ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setString("activeCrewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            query.setLong("countryId", countryId);
            return query.list();            
        } catch (Throwable ex) {
            log.debug("== Error en getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllResponsableEmployeeCrewByCountryAndDealerId/EmployeesCrewDAO ==");
        }
	}
    
}
