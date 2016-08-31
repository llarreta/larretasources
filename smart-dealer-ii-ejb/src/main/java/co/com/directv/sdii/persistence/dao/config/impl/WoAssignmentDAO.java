package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Program;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WoAssignment
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoAssignment
 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal
 */
@Stateless(name="WoAssignmentDAOLocal",mappedName="ejb/WoAssignmentDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoAssignmentDAO extends BaseDao implements WoAssignmentDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoAssignmentDAO.class);

    /**
     * Crea una WoAssignment en el sistema
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWoAssignment/DAOWoAssignmentBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            //jalopez - Ajuste por adicion de estado en la tabla de WO_ASSIGMENTS.
            //Se debe marcar como Inactivo la ultima asiganacion de la WO.
            WoAssignment woAss = this.getLastDealerAssignmentByWoId( obj.getWorkOrder().getId() );
            if( woAss != null ){
            	woAss.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_INACTIVE.getCodeEntity() );
            	this.updateWoAssignment( woAss );
            }
            //jalopez - se marca como activa la asignacion que se va a crear
            obj.setIsActive( CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );	           
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.debug("== Error creando WoAssignment ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina createWoAssignment/WoAssignmentDAO ==");
        }
    }

    /**
     * Obtiene un woassignment con el id especificado
     * @param id - Long
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getWoAssignmentByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoAssignmentByID/WoAssignmentDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strignQuery =  new StringBuffer();
            strignQuery.append("select woassignment from ");
            strignQuery.append(WoAssignment.class.getName());
            strignQuery.append(" woassignment where woassignment.id = ");
            strignQuery.append(id);
            Object obj = session.createQuery(strignQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select woassignment from " + WoAssignment.class.getName() + " woassignment where woassignment.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (WoAssignment) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando WoAssignment por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWoAssignmentByID/WoAssignmentDAO ==");
        }
    }

    /**
     * Actualiza un woassignment especificado
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWoAssignment/WoAssignmentDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);       
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WoAssignment ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoAssignment/WoAssignmentDAO ==");
        }

    }

    /**
     * Elimina un woassignment del sistema
     * @param obj - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoAssignment(WoAssignment obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWoAssignment/WoAssignmentDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando WoAssignment ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteWoAssignment/WoAssignmentDAO ==");
        }

    }

    /**
     * Obtiene todos los woassignments del sistema
     * @return - List<WoAssignment>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WoAssignment> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/WoAssignmentDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WoAssignment");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todas las WoAssignment ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/WoAssignmentDAO ==");
        }
    }
    
    
    /**
     * Obtiene la ultima asignacion dealer de una work order 
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getLastDealerAssignmentByWoId(Long woID) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getLastDealerAssignmentByWoId/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment where woAssignment.workOrder.id = :aWoId and woAssignment.isActive = :activeStatus");
          Query query= session.createQuery(strignQuery.toString());
          query.setLong("aWoId", woID);
          query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          return wolist.get(0);
          
      } catch (Throwable ex) {
          log.debug("== Error consultando WoAssignment por WoID ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getLastDealerAssignmentByWoId/WoAssignmentDAO ==");
      }
    }
    
    /**
     * Obtiene la ultima asignacion dealer de una work order 
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getLastDealerAssignmentByWoIdWithoutWo(Long woID) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getLastDealerAssignmentByWoId/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("select woas.id, woas.program, woas.programAssignmentDate, woas.dealerId, woas.dealerAssignmentDate, woas.crewId, woas.crewAssignmentDate from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woas where woas.workOrder.id = :aWoId order by woas.dealerAssignmentDate desc");
          Query query= session.createQuery(strignQuery.toString());
          query.setLong("aWoId", woID);
          
          List<Object[]> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          WoAssignment result = new WoAssignment();
          Object[] resultArray = wolist.get(0);
          result.setId((Long)resultArray[0]);
          result.setProgram((Program)resultArray[1]);
          result.setProgramAssignmentDate((Date)resultArray[2]);
          result.setDealerId((Long)resultArray[3]);
          result.setDealerAssignmentDate((Date)resultArray[4]);
          result.setCrewId((Long)resultArray[5]);
          result.setCrewAssignmentDate((Date)resultArray[6]);
          return result;
          
      } catch (Throwable ex) {
          log.debug("== Error consultando WoAssignment por WoID ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getLastDealerAssignmentByWoId/WoAssignmentDAO ==");
      }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getLastDealerAssignmentByWoId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getLastProgramByWoId(Long woID) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getLastProgramByWoId/WoAssignmentDAO ==");
      Session session = ConnectionFactory.getSession();

      try {
          if (session == null) {
              throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
          }
          StringBuffer stringQuery = new StringBuffer();
          stringQuery.append("select woAssignment from ");
          stringQuery.append(WoAssignment.class.getName());
          stringQuery.append(" woAssignment where woAssignment.workOrder.id = :id order by woAssignment.dealerAssignmentDate desc");
          Query query= session.createQuery(stringQuery.toString());
          //Query query= session.createQuery("select woAssignment from " + WoAssignment.class.getName() + " woAssignment where woAssignment.workOrder.id = :id order by woAssignment.programAssignmentDate desc");
          query.setLong("id", woID);
          
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          return wolist.get(0);
          
      } catch (Throwable ex) {
          log.debug("== Error consultando WoAssignment por WoID ==");
          throw this.manageException(ex);
      } finally {
          log.debug("== Termina getLastProgramByWoId/WoAssignmentDAO ==");
      }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getLastProgramByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getLastProgramByWoId/WoAssignmentDAO ==");
      Session session = ConnectionFactory.getSession();

      try {
          if (session == null) {
              throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
          }
          StringBuffer stringQuery = new StringBuffer();
          stringQuery.append(" select woAssignment from "+WoAssignment.class.getName()+" woAssignment ");
          stringQuery.append(" where woAssignment.dealerId = :dealerId order by woAssignment.dealerAssignmentDate desc");
          
          Query query= session.createQuery(stringQuery.toString());
          query.setLong("dealerId", dealerId);
          
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          return wolist.get(0);
          
      } catch (Throwable ex) {
          log.debug("== Error consultando WoAssignment por DealerID ==");
          throw this.manageException(ex);
      } finally {
          log.debug("== Termina getLastProgramByWoId/WoAssignmentDAO ==");
      }
    }
    
    
    /**
     * 
     * Metodo: Operacion para consultar una WoAssignment
     * filtrando por el id de la Work Order y por
     * el id del Dealer.
     * @param pWoId Long
     * @param pDealerId Long
     * @return WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getWoAssignmentByAssignmentCrew(Long pWoId,Long pDealerId)throws DAOServiceException, DAOSQLException{
    	
    	 log.debug("== Inicia getWoAssignmentByWoDealer/WoAssignmentDAO ==");
         Session session = ConnectionFactory.getSession();

         try {
             if (session == null) {
                 throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
             }
             StringBuffer queryBuffer = new StringBuffer();
             queryBuffer.append(" from ");
             queryBuffer.append(WoAssignment.class.getName());
             queryBuffer.append(" woAssignment");
             queryBuffer.append(" where woAssignment.workOrder.id = :pWoId");
             queryBuffer.append(" and woAssignment.workOrder.dealerId = :pDealerId");
             queryBuffer.append(" and woAssignment.isActive = :aWoAssignmentActiveCode");
             
             Query query= session.createQuery(queryBuffer.toString());
             query.setLong("pWoId", pWoId);
             query.setLong("pDealerId", pDealerId);
             query.setString("aWoAssignmentActiveCode", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
             
             List<WoAssignment> wolist = query.list();
             if(wolist == null || wolist.isEmpty())
            	 return null;
             return wolist.get(0);
             
         } catch (Throwable ex) {
             log.debug("== Error consultando WoAssignment por WoID y DealerID ==");
             throw this.manageException(ex);
         } finally {
             log.debug("== Termina getWoAssignmentByWoDealer/WoAssignmentDAO ==");
         }
    }
    
    /**
     * 
     * Metodo: Retorna el numero de work orders
     * asignadas a una cuadrilla segun el dealer
     * y que se encuentran en estado:
     * WORKORDER_STATUS_SCHEDULED, WORKORDER_STATUS_RESCHEDULED, 
     * WORKORDER_STATUS_REASSIGN, WORKORDER_STATUS_ASSIGN y 
     * WORKORDER_STATUS_RALIZED.
     * @param pDealerId
     * @return Object
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Object getAmountWoAssigment(WoAssignment woAssignment)throws DAOServiceException, DAOSQLException{
    	
   	 log.debug("== Inicia getAmountWoAssigment/WoAssignmentDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select count(*)");
            queryBuffer.append(" from ");
            queryBuffer.append(WoAssignment.class.getName());
            queryBuffer.append(" wo_a");
            queryBuffer.append(" where wo_a.dealerId = ? and wo_a.crewId = ?");
            queryBuffer.append(" and wo_a.workOrder.workorderStatusByActualStatusId.woStateCode in (?,?,?,?,?,?)");
            queryBuffer.append(" and wo_a.isActive = ?");
            
            Query query= session.createQuery(queryBuffer.toString());             
            query.setLong(0, woAssignment.getDealerId().longValue());
            query.setLong(1, woAssignment.getCrewId().longValue());
            query.setString(2, CodesBusinessEntityEnum.WORKORDER_STATUS_SCHEDULED.getCodeEntity());
            query.setString(3, CodesBusinessEntityEnum.WORKORDER_STATUS_RESCHEDULED.getCodeEntity());
            query.setString(4, CodesBusinessEntityEnum.WORKORDER_STATUS_REASSIGN.getCodeEntity());
            query.setString(5, CodesBusinessEntityEnum.WORKORDER_STATUS_ASSIGN.getCodeEntity());
            query.setString(6, CodesBusinessEntityEnum.WORKORDER_STATUS_REALIZED.getCodeEntity());
            query.setString(7, CodesBusinessEntityEnum.WORKORDER_STATUS_PENDING.getCodeEntity());
            //Se consultan únicamente las asignaciones activas
            query.setString(8, CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
            
            Object amount = query.uniqueResult();
            if(amount != null )
           	 	return amount;
            else
            	return null;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando número de WOAssignment por woState ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAmountWoAssigment/WoAssignmentDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getLastDealerAssignmentsByWoIds(java.util.List)
     */
	@SuppressWarnings("unchecked")
	public List<WoAssignment> getLastDealerAssignmentsByWoIds(List<Long> workOrderIds) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getLastDealerAssignmentsByWoIds/WoAssignmentDAO ==");
	      Session session = ConnectionFactory.getSession();

	      try {
	    	  if (session == null) {
	    		  throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	    	  }

	    	  StringBuffer strBuffer = new StringBuffer("select woAssignment from ");
	    	  strBuffer.append(WoAssignment.class.getName());
	    	  strBuffer.append(" woAssignment where woAssignment.workOrder.id in (");

	    	  for (Long woId : workOrderIds) {
	    		  strBuffer.append(woId);
	    		  strBuffer.append(", ");
	    	  }

	    	  String queryStr = StringUtils.removeEnd(strBuffer.toString(), ", ");
	    	  queryStr += " )order by woAssignment.dealerAssignmentDate desc";

	    	  
	    	  Query query= session.createQuery(queryStr);
	    	  List<WoAssignment> wolist = query.list();
	    	  
	    	  Map<Long, WoAssignment> finalResults = new HashMap<Long, WoAssignment>();
	    	  WoAssignment woAssignmentInMap = null;
	    	  for (WoAssignment woAssignment : wolist) {
	    		  woAssignmentInMap = finalResults.get(woAssignment.getWorkOrder().getId());
	    		  if(woAssignmentInMap == null){
	    			  finalResults.put(woAssignment.getWorkOrder().getId(), woAssignment);
	    		  }
	    	  }
	    	  wolist = new ArrayList<WoAssignment>();
	    	  wolist.addAll(finalResults.values());
	    	  return wolist;
	      } catch (Throwable ex) {
	            log.debug("== Error consultando WoAssignment por WOId's ==");
	            throw this.manageException(ex);
	        }  finally {
	          log.debug("== Termina getLastDealerAssignmentsByWoIds/WoAssignmentDAO ==");
	      }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getLastDealerAssignmentsByWoIdsAndStatusActive(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<WoAssignment> getLastDealerAssignmentsByWoIdsAndStatusActive(List<Long> workOrderIds) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getLastDealerAssignmentsByWoIdsAndStatusActive/WoAssignmentDAO ==");
	      Session session = ConnectionFactory.getSession();

	      try {
	    	  if (session == null) {
	    		  throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
	    	  }

	    	  StringBuffer strBuffer = new StringBuffer("select woAssignment from ");
	    	  strBuffer.append(WoAssignment.class.getName());
	    	  strBuffer.append(" woAssignment where woAssignment.isActive = :activeStatus");
	    	  strBuffer.append(" and woAssignment.workOrder.id in (");

	    	  for (Long woId : workOrderIds) {
	    		  strBuffer.append(woId);
	    		  strBuffer.append(", ");
	    	  }

	    	  String queryStr = StringUtils.removeEnd(strBuffer.toString(), ", ");
	    	  queryStr += " )";

	    	  
	    	  Query query= session.createQuery(queryStr);
	    	  query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
	    	  List<WoAssignment> wolist = query.list();
	    	  
	    	  Map<Long, WoAssignment> finalResults = new HashMap<Long, WoAssignment>();
	    	  WoAssignment woAssignmentInMap = null;
	    	  for (WoAssignment woAssignment : wolist) {
	    		  woAssignmentInMap = finalResults.get(woAssignment.getWorkOrder().getId());
	    		  if(woAssignmentInMap == null){
	    			  finalResults.put(woAssignment.getWorkOrder().getId(), woAssignment);
	    		  }
	    	  }
	    	  wolist = new ArrayList<WoAssignment>();
	    	  wolist.addAll(finalResults.values());
	    	  return wolist;
	      } catch (Throwable ex) {
	            log.debug("== Error consultando WoAssignment por WOId's getLastDealerAssignmentsByWoIdsAndStatusActive/WoAssignmentDAO==");
	            throw this.manageException(ex);
	        }  finally {
	          log.debug("== Termina getLastDealerAssignmentsByWoIdsAndStatusActive/WoAssignmentDAO ==");
	      }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#countWoAssignmenstByProgramId(java.lang.Long)
	 */
	@Override
	public Long countWoAssignmenstByProgramId(Long programId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia countWoAssignmenstByProgramId/WoAssignmentDAO ==");
	      Session session = super.getSession();

	      try {
	    	  StringBuffer strBuffer = new StringBuffer("select count(*) from ");
	    	  strBuffer.append(WoAssignment.class.getName());
	    	  strBuffer.append(" woAssignment where woAssignment.program.id = :aProgramId");
	    	  
	    	  Query query= session.createQuery(strBuffer.toString());
	    	  query.setLong("aProgramId", programId);
	    	  
	    	  Long woAssignmentCount = (Long)query.uniqueResult();
	    	  return woAssignmentCount;
	      } catch (Throwable ex) {
	            log.debug("== Error contando WoAssignment por ProgramId countWoAssignmenstByProgramId/WoAssignmentDAO==");
	            throw this.manageException(ex);
	        }  finally {
	          log.debug("== Termina countWoAssignmenstByProgramId/WoAssignmentDAO ==");
	      }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getFirstAsigmentDateByDealerIdAndWoId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Date getFirstAsigmentDateByDealerIdAndWoId(Long woId,Long dealerId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getFirstAsigmentByDealerIdAndWoId/WoAssignmentDAO ==");
	      Session session = super.getSession();

	      try {
	          StringBuffer strignQuery =  new StringBuffer("select min(woAssignment.dealerAssignmentDate) from ");
	          strignQuery.append(WoAssignment.class.getName());
	          strignQuery.append(" woAssignment where ");
	          strignQuery.append("woAssignment.workOrder.id = :woId ");
	          strignQuery.append("and woAssignment.dealerId = :dealerId ");
	          Query query= session.createQuery(strignQuery.toString());
	          query.setLong("woId", woId);
	          query.setLong("dealerId", dealerId );
	          
	          return (Date) query.uniqueResult();
	          
	      } catch (Throwable ex) {
	          log.debug("== Error consultando getFirstAsigmentByDealerIdAndWoId/WoAssignmentDAO ==");
	          throw this.manageException(ex);
	      }  finally {
	          log.debug("== Termina getFirstAsigmentByDealerIdAndWoId/WoAssignmentDAO ==");
	      }
	}
	
	/**
     * Obtiene la ultima asignacion dealer de una work order 
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	@Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getWorkOrdersAssignment(Long pWoId) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getWorkOrdersAssignment/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment ");
          strignQuery.append(" where woAssignment.workOrder.id = :pWoId and woAssignment.dealerId <> null ");
          strignQuery.append(" and woAssignment.isActive = :activeStatus");
          Query query= session.createQuery(strignQuery.toString());
          
          query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
          query.setLong("pWoId", pWoId);
          
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          
          return wolist.get(0);          
      } catch (Throwable ex) {
          log.debug("== Error consultando getWorkOrdersAssignment/WoAssignmentDAO ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getWorkOrdersAssignment/WoAssignmentDAO ==");
      }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getWorkOrdersCrewActiveAssignment(java.lang.Long)
	 */
	@Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getWorkOrdersCrewActiveAssignment(Long pWoId) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getWorkOrdersCrewActiveAssignment/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment ");
          strignQuery.append(" where woAssignment.workOrder.id = :pWoId and woAssignment.crewId <> null ");
          strignQuery.append(" and woAssignment.isActive = :activeStatus");
          Query query= session.createQuery(strignQuery.toString());
          
          query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
          query.setLong("pWoId", pWoId);
          
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          
          return wolist.get(0);          
      } catch (Throwable ex) {
          log.debug("== Error consultando getWorkOrdersCrewActiveAssignment/WoAssignmentDAO ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getWorkOrdersCrewActiveAssignment/WoAssignmentDAO ==");
      }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getWorkOrdersProgramActiveAssignment(java.lang.Long)
	 */
	@Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getWorkOrdersProgramActiveAssignment(Long pWoId) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getWorkOrdersProgramActiveAssignment/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment ");
          strignQuery.append(" where woAssignment.workOrder.id = :pWoId and woAssignment.program <> null ");
          strignQuery.append(" and woAssignment.isActive = :activeStatus");
          Query query= session.createQuery(strignQuery.toString());
          
          query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
          query.setLong("pWoId", pWoId);
          
          List<WoAssignment> wolist = query.list();
          if(wolist == null || wolist.isEmpty())
        	  return null;
          
          return wolist.get(0);          
      } catch (Throwable ex) {
          log.debug("== Error consultando getWorkOrdersProgramActiveAssignment/WoAssignmentDAO ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getWorkOrdersProgramActiveAssignment/WoAssignmentDAO ==");
      }
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus(java.lang.Long, java.util.List)
	 */
	@Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WoAssignment> getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus(Long crewId,List<String> woStatussCode) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment ");
          strignQuery.append(" where woAssignment.crewId = :crewId ");
          strignQuery.append(" and woAssignment.isActive = :activeStatus");
          strignQuery.append(" and woAssignment.workOrder.workorderStatusByActualStatusId.woStateCode in (");
          
          StringBuffer sb = new StringBuffer();
          for( String code : woStatussCode ){
        	  sb.append( "'" );
        	  sb.append( code );
        	  sb.append( "'" );
        	  sb.append(",");
          }
          strignQuery.append( StringUtils.removeEnd(sb.toString(), ",") );
          strignQuery.append( ") " );
          Query query= session.createQuery(strignQuery.toString());
          
          query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity() );
          query.setLong("crewId", crewId);
          
          return query.list();
      } catch (Throwable ex) {
          log.debug("== Error consultando getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus/WoAssignmentDAO ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getWorkOrdersCrewActiveAssignmentByCrewIdAndWoStatus/WoAssignmentDAO ==");
      }
    }

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal#getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate(java.lang.String)
	 */
	@Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WoAssignment> getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate(String customerCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate/WoAssignmentDAO ==");
	      Session session = super.getSession();

	      try {
	          StringBuffer strignQuery =  new StringBuffer("from ");
	          strignQuery.append(WoAssignment.class.getName());
	          strignQuery.append(" woAssignment ");
	          strignQuery.append(" where woAssignment.isActive = :activeStatus ");
	          strignQuery.append("and woAssignment.workOrder.customer.customerCode = :customerCode ");
	          strignQuery.append("and woAssignment.workOrder.workorderStatusByActualStatusId.woStateCode = :finishedStatusCode ");
	          strignQuery.append("and woAssignment.workOrder.finalizationDate <> null ");
	          strignQuery.append("order by woAssignment.workOrder.finalizationDate desc");
	          
	         Query query= session.createQuery(strignQuery.toString());
	         
	         query.setString("customerCode", customerCode);
	         query.setString("finishedStatusCode", CodesBusinessEntityEnum.WORKORDER_STATUS_FINISHED.getCodeEntity());
	         query.setString("activeStatus", CodesBusinessEntityEnum.WO_ASSIGMENT_STATUS_ACTIVE.getCodeEntity());
	         query.setMaxResults( 2 );
	         
	         return query.list();
	      } catch (Throwable ex) {
	          log.debug("== Error consultando getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate/WoAssignmentDAO ==");
	          throw this.manageException(ex);
	      }  finally {
	          log.debug("== Termina getWorkOrdersByCustomerCodeAndStatusOrderByFinalizationDate/WoAssignmentDAO ==");
	      }
	}
	
	/**
     * Obtiene la ultima asignacion dealer de una work order 
     * ordenada por la ultima fecha de asignacion
     * @param woID - Id de la work order a buscar
     * @return - WoAssignment
     * @throws DAOServiceException
     * @throws DAOSQLException
     */   
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WoAssignment getLastDealerAssignmentByAssignmentDate(Long woID) throws DAOServiceException, DAOSQLException{
	  log.debug("== Inicia getLastDealerAssignmentByAssignmentDate/WoAssignmentDAO ==");
      Session session = super.getSession();

      try {
          StringBuffer strignQuery =  new StringBuffer("from ");
          strignQuery.append(WoAssignment.class.getName());
          strignQuery.append(" woAssignment where woAssignment.workOrder.id = :aWoId and woAssignment.dealerId <> null  order by woAssignment.dealerAssignmentDate desc");
          Query query= session.createQuery(strignQuery.toString());
          query.setLong("aWoId", woID);          
          query.setMaxResults(1);
          
          return (WoAssignment) query.uniqueResult();          
      } catch (Throwable ex) {
          log.debug("== Error consultando la operacion getLastDealerAssignmentByAssignmentDate/WoAssignmentDAO ==");
          throw this.manageException(ex);
      }  finally {
          log.debug("== Termina getLastDealerAssignmentByAssignmentDate/WoAssignmentDAO ==");
      }
    }
	
    /**
     * Obtiene todas las asignaciones de una WO ordenadas por fecha
     * @param pWoId - Id de la work order a buscar
     * @return - List<WoAssignment>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * */
    @SuppressWarnings("unchecked")
	public List<WoAssignment> getWorkOrderAssignments(Long pWoId) throws DAOServiceException, DAOSQLException{
    	try{
    		log.debug("== Inicia getWorkOrderAssignments/WoAssignmentDAO ==");
  	      	Session session = ConnectionFactory.getSession();

  	      	if (session == null) {
  	    		  throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
  	    	}

  	    	StringBuffer strBuffer = new StringBuffer("select woAssignment from ");
  	    	strBuffer.append(WoAssignment.class.getName());
  	    	strBuffer.append(" woAssignment where woAssignment.workOrder.id = ");
  	    	strBuffer.append(pWoId);	    	  
  	    	strBuffer.append(" order by woAssignment.dealerAssignmentDate desc");
 	    	  
  	    	Query query= session.createQuery(strBuffer.toString());
  	    	List<WoAssignment> wolist = query.list();
  	    	  
  	    	return wolist;
    		
    	} catch (Throwable ex){
            log.debug("== Error consultando la operacion getWorkOrderAssignments/WoAssignmentDAO ==");
            throw this.manageException(ex);    		
    	} finally{
            log.debug("== Termina getWorkOrderAssignments/WoAssignmentDAO ==");    		
    	}
    }


}
