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
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.WorkOrderService;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WorkOrderServiceResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de WorkOrderService
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WorkOrderService
 * @see co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal
 */
@Stateless(name="WorkOrderServiceDAOLocal",mappedName="ejb/WorkOrderServiceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderServiceDAO extends BaseDao implements WorkOrderServiceDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderServiceDAO.class);

    /**
     * Crea una WorkOrderService en el sistema
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrderService/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando WorkOrderService ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderService/DAOWorkOrderServiceBean ==");
        }
    }

    /**
     * Obtiene un workorderservice con el id especificado
     * @param id - Long
     * @return - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderService getWorkOrderServiceByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWorkOrderServiceByID/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Object obj = session.get(WorkOrderService.class, id);
            if (obj != null) {
                return (WorkOrderService) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderService por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderServiceByID/DAOWorkOrderServiceBean ==");
        }
    }

    /**
     * Actualiza un workorderservice especificado
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateWorkOrderService/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando WorkOrderService==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWorkOrderService/DAOWorkOrderServiceBean ==");
        }

    }

    /**
     * Elimina un workorderservice del sistema
     * @param obj - WorkOrderService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWorkOrderService(WorkOrderService obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteWorkOrderService/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error eliminando WorkOrderService ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkOrderService/DAOWorkOrderServiceBean ==");
        }

    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal#deleteWorkOrderServiceByWoId(java.lang.Long)
     */
    @Override
	public void deleteWorkOrderServiceByWoId(Long workorderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WorkOrderService where woId = :aWoId");
            query.setLong("aWoId", workorderId);
            query.executeUpdate();
            super.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error eliminando WorkOrderService ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        }
	}

    /**
     * Obtiene todos los workorderservices del sistema
     * @return - List<WorkOrderService>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<WorkOrderService> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from WorkOrderService");
            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando todas las WorkOrderService ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/DAOWorkOrderServiceBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal#getWOServiceByWorkorderIdAndServiceIdAndSerailNumber(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
	public List<WorkOrderService> getWOServiceByWorkorderIdAndServiceIdAndSerailNumber(
			Long workOrderId, Long serviceId, String decoderSerialNumber)
			throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getWOServiceByWorkorderIdAndServiceIdAndSerailNumber/DAOWorkOrderServiceBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from WorkOrderService wos where ");
            stringQuery.append("wos.woId = :aWosWorkorderId ");
            stringQuery.append("and wos.service.id = :aWosServiceId ");
            
            boolean isDecoderSerialSpecified = false;
            if(decoderSerialNumber != null && decoderSerialNumber.trim().length() > 0){
            	isDecoderSerialSpecified = true;
            	stringQuery.append("and wos.serialNumber = :aSerialNumber ");
            }
            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWosWorkorderId", workOrderId);
            query.setLong("aWosServiceId", serviceId);
            
            if(isDecoderSerialSpecified){
            	query.setString("aSerialNumber", decoderSerialNumber);
            }
            
            List<WorkOrderService> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando WorkOrderService por Wo, Service, decoderSerialNumber ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getWOServiceByWorkorderIdAndServiceIdAndSerailNumber/DAOWorkOrderServiceBean ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal#getWOServiceByWorkorderIdAndServiceIdAndSerailNumber(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @SuppressWarnings({ "unchecked"})
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public WorkOrderServiceResponse getWOServiceByWorkorderId(Long workOrderId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getWOServiceByWorkorderId/DAOWorkOrderServiceBean ==");
	      Session session = super.getSession();
	      WorkOrderServiceResponse response = new WorkOrderServiceResponse();
        try {
            int firstResult = 0;
            int maxResult = 0;
            Long totalRowCount = 0L;
            
            StringBuffer stringQuery =  new StringBuffer();
            StringBuffer countQueryBuffer = new StringBuffer("select count(*) ");
            stringQuery.append("from ");
            stringQuery.append(WorkOrderService.class.getName());
            stringQuery.append(" wos where ");
            stringQuery.append("wos.woId = :aWosWorkorderId");
                       
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWosWorkorderId", workOrderId);
            
            // Paginación
            if (requestCollectionInfo != null){
            	Query countQuery = session.createQuery(countQueryBuffer.toString() + stringQuery.toString());
            	countQueryBuffer.append( stringQuery.toString() );   
            	countQuery.setLong("aWosWorkorderId", workOrderId);
                countQuery = session.createQuery( countQueryBuffer.toString() );
                
            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);                
            }
            
            response.setWorkOrderServices(query.list());
            if (requestCollectionInfo != null)
            	populatePaginationInfo(response, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), response.getWorkOrderServices().size(), totalRowCount.intValue());
            return response;
        }catch (Throwable ex) {
            log.debug("== Error consultando la operacion getWOServiceByWorkorderId/DAOWorkOrderServiceBean ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWOServiceByWorkorderId/DAOWorkOrderServiceBean ==");
        }
	}
    
    /**
     * 
     * Metodo: Retorna un listado de Servicios de una work order
     * a partir de la relacion en work order service
     * @param workOrderId
     * @return List<Service>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author Joan Lopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Service> getServicesOfWorkOrderServiceByWorkorderId(Long workOrderId) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getServicesOfWorkOrderServiceByWorkorderId/WorkOrderServiceDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select woService.service ");            
            stringQuery.append("from ");
            stringQuery.append(WorkOrderService.class.getName());
            stringQuery.append(" woService ");
            stringQuery.append("inner join woService.service where woService.woId = :aWosWorkorderId ");            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aWosWorkorderId", workOrderId);
            List<Service> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error en getServicesOfWorkOrderServiceByWorkorderId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getServicesOfWorkOrderServiceByWorkorderId/WorkOrderServiceDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.WorkOrderServiceDAOLocal#getWorkOrderServiceByWoId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<WorkOrderService> getWorkOrderServiceByWoId(Long workorderId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuilder sb = new StringBuilder();
        	sb.append("from ");
        	sb.append(WorkOrderService.class.getName());
        	sb.append(" woService ");
        	sb.append("where woService.woId = :aWoId ");
            Query query = session.createQuery(sb.toString());
            query.setLong("aWoId", workorderId);
            return query.list();
            
        }catch (Throwable ex) {
            log.debug("== Error getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        }
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<WorkOrderService> getWorkOrderServiceByWoIds(List<Long> woIDs) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        Session session = super.getSession();
        try {
        	StringBuilder sb = new StringBuilder();
        	sb.append("from ");
        	sb.append(WorkOrderService.class.getName());
        	sb.append(" woService ");
        	sb.append("where woService.woId in("+UtilsBusiness.longListToString(woIDs,",")+")");
        	sb.append(" order by woService.woId desc");
            Query query = session.createQuery(sb.toString());
            return query.list();
            
        }catch (Throwable ex) {
            log.debug("== Error getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getWorkOrderServiceByWoId/DAOWorkOrderServiceBean ==");
        }
	}

}
