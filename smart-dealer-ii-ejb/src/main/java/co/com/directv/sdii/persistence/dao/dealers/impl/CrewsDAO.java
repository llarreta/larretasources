/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Crew;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;

/**
 *
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Crews
 *
 * Fecha de Creaci�n: Mar 08, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.model.pojo.Crews
 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal
 */
@Stateless(name="CrewsDAOLocal",mappedName="ejb/CrewsDAOLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CrewsDAO extends BaseDao implements CrewsDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealersDAO.class);

    /**
     * Crea una cuadrilla en el sistema
     * @param newCrew - Crews
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCrew(Crew newCrew) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCrew/CrewsDAO ==");
        Session session = getSession();

        try {
            session.save(newCrew);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCrew/CrewsDAO ==");
        }
    }

    /**
     * Obtiene un listado de cuadrillas segun su placa y/o documento del
     * colaborador
     * @param plate - String
     * @param document - String
     * @return - List<Crew>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getCrewsByPlateOrDocument(String plate, String document) throws DAOServiceException, DAOSQLException {
        return getCrewsByPlateOrDocumentOrDealer(plate, document, null);
    }
    
    /**
     * Obtiene un listado de cuadrillas segun su placa y/o documento del
     * colaborador y delaer id
     * @param plate - String
     * @param document - String
     * @return - List<Crew>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getCrewsByPlateOrDocumentOrDealer(String plate, String document, Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewsByPlateOrDocument/DAOCrewsBean ==");
        Session session = getSession();
        try {
            StringBuffer bufferSql = new StringBuffer("select crews ");
            bufferSql.append(" from ");
            bufferSql.append(Crew.class.getName());
            bufferSql.append(" crews where crews.id in ( ");
            bufferSql.append(" select ec.id.crewId from ");
            bufferSql.append(EmployeeCrew.class.getName());
            bufferSql.append(" ec where 1=1");
            
            if(plate != null && !"".equals(plate)){
            	bufferSql.append(" and ec.crew.vehicle.plate='" + plate + "'");
            }
            if(document != null && !"".equals(document)){
            	bufferSql.append(" and ec.employee.documentNumber='" + document + "'");
            }
            bufferSql.append(")");
            
            if(dealerId!=null && !"".equals(plate)){
            	bufferSql.append(" and crews.dealer.id =" + dealerId + " ");
            }
            
            Query query = session.createQuery(bufferSql.toString());
            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error consultando Crews por documentColaborator and Plate ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsByPlateOrDocument/DAOCrewsBean ==");
        }
    }

    /**
     * Obtiene todos las cuadrillas existentes en el sistema
     * @return - Listado de Cuadrillas
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOCrewsBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from " );
        	stringQuery.append(Crew.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Crew.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOCrewsBean ==");
        }
    }

    /**
     * Obtiene una cuadrilla por el id especificado
     * @param id - Long
     * @return - Entidad con la informacion basica de la cuadrilla
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Crew getCrewById(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewById/DAOCrewBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" crews where crews.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Crew.class.getName() + " crews where crews.id = :id");
            query.setString("id", id.toString());

            Object obj = query.uniqueResult();

            if (obj != null) {
                return (Crew) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Crew por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewById/DAOCrewBean ==");
        }
    }

    /**
     * Actualiza una cuadrilla
     * @param updateCrew - Crews
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCrew(Crew updateCrew) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio updateCrew/DAOCrewBean ==");
        Session session = getSession();

        try {
           session.merge(updateCrew);
           this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Crew por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCrew/DAOCrewBean ==");
        }
    }

    /**
     * Elimina una cuadrilla del sistema
     * @param crewToDelete - Crews
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCrew(Crew crewToDelete) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio updateCrew/DAOCrewBean ==");
        Session session = getSession();

        try {
            session.delete(crewToDelete);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCrew/DAOCrewBean ==");
        }
    }

    /**
     * Obtiene un listado de cuadrillas programadas en un rango de fechas especifico
     * @param initDate - Date
     * @param endDate - Date
     * @return - List<Crews>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getCrewsBetweenDates(final Date initDate, final Date endDate) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewsBetweenDates/DAOCrewBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" c where c.initDate >= :initDate and c.endDate <= :endDate");
        	String hQuery = stringQuery.toString();
            //String hQuery = "from " + Crew.class.getName() + " c where c.initDate >= :initDate and c.endDate <= :endDate";
            Query query = session.createQuery(hQuery);
            query.setDate("initDate", initDate);
            query.setDate("endDate", endDate);

            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error eliminando el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsBetweenDates/DAOCrewBean ==");
        }
    }
    
    /**
     * Obtiene un listado de cuadrillas programadas en un rango de fechas y un empleado especifico 
     * @param initDate - Date
     * @param endDate - Date
     * @param empId - EmployeeId
     * @return - List<Crews>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getCrewsBetweenDatesAndEmployee(final Date initDate, final Date endDate, final Long empId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewsBetweenDatesAndEmployee/DAOCrewBean ==");
        Session session = getSession();

        try {
            StringBuffer sb = new StringBuffer();

            sb.append(" select cc from " + EmployeeCrew.class.getName() + " ec ");
            sb.append(" inner join ec.crew cc ");
            sb.append(" where cc.initDate >= :initDate ");
            sb.append(" and cc.endDate <= :endDate ");
            sb.append(" and ec.id.employeeId = :employeeId ");
            sb.append(" and ec.crew.crewStatus.statusCode = :activeStatus ");

            Query query = session.createQuery(sb.toString());
            query.setDate("initDate", initDate);
            query.setDate("endDate", endDate);
            query.setLong("employeeId", empId);
            query.setString("activeStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());

            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error buscando el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsBetweenDatesAndEmployee/DAOCrewBean ==");
        }
    }
    
    /**
     * Obtiene un listado de cuadrillas programadas en un rango de fechas y un empleado especifico 
     * @param initDate - Date
     * @param endDate - Date
     * @param empId - EmployeeId
     * @return - List<Crews>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Crew> getCrewsBetweenDatesAndEmployeeAndCrew(final Date initDate, final Date endDate, final Long empId, final Long crewId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCrewsBetweenDatesAndEmployee/DAOCrewBean ==");
        Session session = getSession();

        try {
            StringBuffer sb = new StringBuffer();

            sb.append(" select cc from " + EmployeeCrew.class.getName() + " ec ");
            sb.append(" inner join ec.crew cc ");
            sb.append(" where cc.initDate >= :initDate ");
            sb.append(" and cc.endDate <= :endDate ");
            sb.append(" and ec.id.employeeId = :employeeId ");
            sb.append(" and cc.id <> :crewId");
            sb.append(" and ec.crew.crewStatus.statusCode = :activeStatus ");
            sb.append(" and ec.isResponsible = :responsableStatus ");

            Query query = session.createQuery(sb.toString());
            query.setDate("initDate", initDate);
            query.setDate("endDate", endDate);
            query.setLong("employeeId", empId);
            query.setLong("crewId", crewId);
            query.setString("activeStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            query.setString("responsableStatus", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());

            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error buscando el Crew ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsBetweenDatesAndEmployee/DAOCrewBean ==");
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Crew> getCrewsByDealerIdStartDateAndEndDate(Long dealerId,
			Date startDate, Date endDate) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getCrewsByDealerIdStartDateAndEndDate/DAOCrewsBean ==");
        Session session = getSession();

        List<Crew> result = null;
        try {
            StringBuffer queryStr = new StringBuffer("from ");
            queryStr.append(Crew.class.getName());
            
            boolean dealerIdEmpty = dealerId == null || dealerId <= 0L;
            boolean startDateEmpty = startDate == null;
            boolean endDateEmpty = endDate == null;
            boolean someParameter = !dealerIdEmpty || !startDateEmpty || !endDateEmpty;
            
            if(someParameter){
            	queryStr.append(" c where ");
            }
            
            if(!dealerIdEmpty){
            	queryStr.append(" c.dealer.id = :aDealerId and");
            }
            if(!startDateEmpty){
            	queryStr.append(" c.initDate = :anInitDate and");
            }
            if(!endDateEmpty){
            	queryStr.append(" c.endDate = :anEndDate and");
            }
            String finalQuery = queryStr.toString();
            if(someParameter){
            	finalQuery = StringUtils.removeEnd(finalQuery, " and");
            }
            
            Query query = session.createQuery(finalQuery);
            if(!dealerIdEmpty){
            	query.setLong("aDealerId", dealerId);
            }
            if(!startDateEmpty){
            	query.setTimestamp("anInitDate", startDate);
            }
            if(!endDateEmpty){
            	query.setTimestamp("anEndDate", endDate);
            }
            
            result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error obteniendo los Crews por DealerId, fechaInicial y fechaFinal ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsByDealerIdStartDateAndEndDate/DAOCrewsBean ==");
        }
	}

	@SuppressWarnings("unchecked")
	public List<Crew> getAllCrewsOnlyBasicInfo() throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getAllCrewsOnlyBasicInfo/DAOCrewsBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" (c.id, c.initDate, c.endDate) from ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" c");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select new "+Crew.class.getName()+" (c.id, c.initDate, c.endDate) from " + Crew.class.getName() + " c");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getAllCrewsOnlyBasicInfo ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCrewsOnlyBasicInfo/DAOCrewsBean ==");
        }
	}

	@SuppressWarnings("unchecked")
	public List<Crew> getCrewsByVehicleId(long vehicleId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCrewsByVehicleId/DAOCrewsBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" (c.id, c.initDate, c.endDate) from ");
        	stringQuery.append(Crew.class.getName());
        	stringQuery.append(" c where c.vehicle.id = :aVehicleId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select new "+Crew.class.getName()+" (c.id, c.initDate, c.endDate) from " + Crew.class.getName() + " c where c.vehicle.id = :aVehicleId");
            query.setLong("aVehicleId", vehicleId);
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByVehicleId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsByVehicleId/DAOCrewsBean ==");
        }
	}

	/**
     * Method: Retorna un listado de Crews por el
     * id del Dealer
     * @param  Long dealerId
     * @return - List<Crew>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jalopez
     */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Crew> getCrewsByDealerId(Long dealerId)	throws DAOServiceException, DAOSQLException {
				
		log.debug("== Inicia getCrewsByDealerId/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.dealer.id = :dealerId order by crew.dealer.id");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("dealerId", dealerId);
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByDealerId ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getCrewsByDealerId/DAOCrewsBean ==");
        }		
	}
	
	
	/**
	 * Obtiene las cuadrillas correspondientes a un responsable
	 * @param responsibleName - Nombre del responsable de la y/o las cuadrillas
	 * @param idDealer - Id del dealer
	 * @return List<Crew> - Listado de cuadrillas pertenecientes a un responsable
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 * @author jcasas
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> getCrewsByResponsibleNameAndDealerId(String responsibleName, Long idDealer) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicia getCrewsByResponsibleName/DAOCrewsBean ==");
        Session session = getSession();

        try {
          StringBuffer buffer = new StringBuffer();
          buffer.append("select crews, (select count(wo.id) from " + WoAssignment.class.getName() + " woa inner join woa.workOrder as wo where woa.crewId = crews.id and wo.workorderStatusByActualStatusId.woStateCode = :statusId) from ");
          buffer.append(EmployeeCrew.class.getName());
          buffer.append(" as ec ");
          buffer.append("inner join ec.crew as crews ");
          buffer.append("inner join ec.employee as e ");
          buffer.append("where ec.isResponsible = :isResponsible and  lower(e.firstName) || ' ' || lower(e.lastName) LIKE '%'||:name||'%' and e.dealer.id = :dealerId");
          
          Query query = session.createQuery(buffer.toString());
          query.setString("isResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
          query.setString("name", responsibleName.toLowerCase());
          query.setLong("dealerId", idDealer);
          query.setString("statusId", CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
          
          System.out.println("Query Final: " + query.getQueryString());
          
          List<Object[]> objects = query.list();
          
          return objects;
          
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByResponsibleName ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsByResponsibleName/DAOCrewsBean ==");
        }		
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Crew> getCrewsByResponsible(Long responsibleId) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicia getCrewsByResponsibleName/DAOCrewsBean ==");
        Session session = getSession();
        try {
          StringBuffer buffer = new StringBuffer();
          buffer.append("select employeeCrew.crew from ");
          buffer.append(EmployeeCrew.class.getName()+ " employeeCrew ");
          buffer.append(" where  employeeCrew.isResponsible = :isResponsible and ");
          buffer.append(" employeeCrew.employee.id = :id ");
          Query query = session.createQuery(buffer.toString());
          
          query.setString("isResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
          query.setLong("id", responsibleId);

          return query.list();
          
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByResponsible ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewsByResponsible/DAOCrewsBean ==");
        }		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal#getCrewsByDealerIdAndType(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<Crew> getCrewsByDealerIdAndType(Long dealerId, Long crewType)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCrewsByDealerIdAndType/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.dealer.id = :dealerId ");
            queryBuffer.append("and crew.crewType.id = :crewType ");
            queryBuffer.append("and crew.crewStatus.statusCode = :crewStatus ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("dealerId", dealerId);
            query.setLong("crewType", crewType);
            query.setString("crewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByDealerIdAndType ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getCrewsByDealerIdAndType/DAOCrewsBean ==");
        }		
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal#getActiveCrewsByDealerIdAndType(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<Crew> getActiveCrewsByDealerIdAndType(Long dealerId, Long crewType)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveCrewsByDealerIdAndType/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.dealer.id = :dealerId ");
            queryBuffer.append("and crew.crewType.id = :crewType ");
            queryBuffer.append("and crew.crewStatus.statusCode = :crewStatus ");
            queryBuffer.append("and crew.vehicle.vehicleStatus.statusCode = :vehiculeStatus");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("dealerId", dealerId);
            query.setLong("crewType", crewType);
            query.setString("crewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());            
            query.setString("vehiculeStatus", CodesBusinessEntityEnum.ACTIVE_VEHICLE.getCodeEntity());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getActiveCrewsByDealerIdAndType ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getActiveCrewsByDealerIdAndType/DAOCrewsBean ==");
        }		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal#getActiveCrewsByDealerId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Crew> getActiveCrewsByDealerId(Long dealerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveCrewsByDealerId/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.dealer.id = :dealerId and crew.crewStatus.statusCode = :aCrewStatus order by crew.dealer.id");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("dealerId", dealerId);
            query.setString("aCrewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getActiveCrewsByDealerId ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getActiveCrewsByDealerId/DAOCrewsBean ==");
        }		
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal#getActiveCrewsQtyByDealerId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Long getActiveCrewsQtyByDealerId(Long dealerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveCrewsByDealerId/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select count(crew) ");
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.dealer.id = :dealerId and crew.crewStatus.statusCode = :aCrewStatus order by crew.dealer.id");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("dealerId", dealerId);
            query.setString("aCrewStatus", CodesBusinessEntityEnum.CREW_STATUS_ACTIVE.getCodeEntity());
            
            return (Long) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getActiveCrewsByDealerId ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getActiveCrewsByDealerId/DAOCrewsBean ==");
        }		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal#getCrewsByVehicleIdAndCrewStatusCode(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Crew> getCrewsByVehicleIdAndCrewStatusCode(Long vehicleId,String crewStatus) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCrewsByVehicleIdAndCrewStatusCode/DAOCrewsBean ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Crew.class.getName());
            queryBuffer.append(" crew where crew.vehicle.id = :vehicleId ");
            queryBuffer.append("and crew.crewStatus.statusCode = :aCrewStatus ");
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("vehicleId", vehicleId);
            query.setString("aCrewStatus", crewStatus);
            
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el getCrewsByVehicleIdAndCrewStatusCode/DAOCrewsBean ==");
            throw this.manageException(ex);
        }   finally {
            log.debug("== Termina getCrewsByVehicleIdAndCrewStatusCode/DAOCrewsBean ==");
        }		
	}	
}
