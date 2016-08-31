package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.ReportedElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ReportedElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ReportedElement
 * @see co.com.directv.sdii.model.hbm.ReportedElement.hbm.xml
 */
@Stateless(name="ReportedElementDAOLocal",mappedName="ejb/ReportedElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReportedElementDAO extends BaseDao implements ReportedElementDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ReportedElementDAO.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementsDAOLocal#createReportedElement(co.com.directv.sdii.model.pojo.ReportedElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createReportedElement/ReportedElementDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ReportedElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createReportedElement/ReportedElementDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementsDAOLocal#updateReportedElement(co.com.directv.sdii.model.pojo.ReportedElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateReportedElement/ReportedElementDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ReportedElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateReportedElement/ReportedElementDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementsDAOLocal#deleteReportedElement(co.com.directv.sdii.model.pojo.ReportedElement)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteReportedElement(ReportedElement obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteReportedElement/ReportedElementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReportedElement entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ReportedElement ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReportedElement/ReportedElementDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementsDAOLocal#getReportedElementsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ReportedElement getReportedElementByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getReportedElementByID/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReportedElement.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ReportedElement) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReportedElementByID/ReportedElementDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementsDAOLocal#getAllReportedElements()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ReportedElement> getAllReportedElements() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllReportedElements/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ReportedElement.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllReportedElements/ReportedElementDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#getReportedElementsByRefInconsistencyId(java.lang.Long, boolean, boolean)
     */
	@Override
	public List<ReportedElement> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getReportedElementsByRefInconsistencyId/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ")
        		.append(ReportedElement.class.getName())
        		.append(" re where")
        		.append(" re.refInconsistency.id = :refInconsistencyId");
        	
        	if((incluirSobrantes ^ incluirFaltantes)) {
        		stringQuery.append(" and re.refInconsistency.refIncType.refIncTypeCode = '");
        		if(incluirSobrantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity());
        		} else if (incluirFaltantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity());
        		}
        		stringQuery.append("'");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("refInconsistencyId", refInconsistencyId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReportedElementsByRefInconsistencyId/ReportedElementDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#getCountReportedElementsActiveByRefElementItemId(java.lang.Long, java.lang.String, java.lang.Long, boolean, boolean)
	 */
	@Override
	public Double getQuantityReportedElementsByRefElementItemId(
			Long refElementItemId, boolean incluirSobrantes, boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer("select sum(qty) from ")
        		.append(ReportedElement.class.getName())
        		.append(" re where")
        		.append(" re.referenceElementItem.id = :refElementItemId");
        		/*.append(" and re.refInconsistency.refIncStatus.refIncStatusCode = '")
        		.append(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()).append("'");*/
        		
        	if((incluirSobrantes ^ incluirFaltantes)) {
        		stringQuery.append(" and re.refInconsistency.refIncType.refIncTypeCode = '");
        		if(incluirSobrantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity());
        		} else if (incluirFaltantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity());
        		}
        		stringQuery.append("'");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("refElementItemId", refElementItemId);
        	
        	Double count = (Double) query.uniqueResult(); 
        	count = count == null? 0D : count;
        	return count; 
            
        } catch (Throwable ex){
			log.debug("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#deleteReportedElementsByRefInconsitencyId(java.lang.Long)
	 */
	@Override
	public void deleteReportedElementsByRefInconsitencyId(Long refInconsitencyId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteReportedElementsByRefInconsitencyId/ReportedElementDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ReportedElement entity where entity.refInconsistency.id = :refInconsitencyId");
            query.setLong("refInconsitencyId", refInconsitencyId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error ejecutando deleteReportedElementsByRefInconsitencyId/ReportedElementDAO == " +  ex.getMessage());
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteReportedElementsByRefInconsitencyId/ReportedElementDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#getCountReportedElementsByRefIdAndElementTypeId(java.lang.Long, java.lang.Long, boolean, boolean)
	 */
	@Override
	public Long getCountReportedElementsByRefIdAndElementTypeId(
			Long referenceId, Long elementTypeId, boolean incluirSobrantes, boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer("select count(*) from ")
        		.append(ReportedElement.class.getName())
        		.append(" re where")
        		.append(" re.refInconsistency.reference.id = :referenceId")
        		.append(" and re.elementType.id = :elementTypeId");
        		/*.append(" and re.refInconsistency.refIncStatus.refIncStatusCode = '")
        		.append(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()).append("'");*/
        		
        	if((incluirSobrantes ^ incluirFaltantes)) {
        		stringQuery.append(" and re.refInconsistency.refIncType.refIncTypeCode = '");
        		if(incluirSobrantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity());
        		} else if (incluirFaltantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity());
        		}
        		stringQuery.append("'");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("referenceId", referenceId);
        	query.setLong("elementTypeId", elementTypeId);
        	
        	Long count = (Long) query.uniqueResult(); 
        	count = count == null? 0L : count;
        	return count; 
            
        } catch (Throwable ex){
			log.debug("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#getCountReportedElementsByRefIdAndElementTypeId(java.lang.Long, java.lang.Long, boolean, boolean)
	 */
	@Override
	public ReportedElement getReportedElementByRefIdAndElementTypeId(
			Long referenceId, Long elementTypeId, boolean incluirSobrantes, boolean incluirFaltantes) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer("from ")
        		.append(ReportedElement.class.getName())
        		.append(" re where")
        		.append(" re.refInconsistency.reference.id = :referenceId")
        		.append(" and re.elementType.id = :elementTypeId");
        		/*.append(" and re.refInconsistency.refIncStatus.refIncStatusCode = '")
        		.append(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()).append("'");*/
        		
        	if((incluirSobrantes ^ incluirFaltantes)) {
        		stringQuery.append(" and re.refInconsistency.refIncType.refIncTypeCode = '");
        		if(incluirSobrantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_EXCEEDED_QUANTITY.getCodeEntity());
        		} else if (incluirFaltantes) {
        			stringQuery.append(CodesBusinessEntityEnum.INCONSISTENCY_TYPE_INCOMPLETE_QUANTITY.getCodeEntity());
        		}
        		stringQuery.append("'");
        	}
        	
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("referenceId", referenceId);
        	query.setLong("elementTypeId", elementTypeId);
        	
        	List<ReportedElement> result = query.list();
        	if(result != null && !result.isEmpty()) {
        		return result.get(0);
        	}
        	
        	return null; 
            
        } catch (Throwable ex){
			log.debug("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCountReportedElementsActiveByRefElementItemId/ReportedElementDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ReportedElementDAOLocal#getReportedElementByReferenceIdAndSerial(java.lang.Long, java.lang.String)
	 */
	@Override
	public ReportedElement getReportedElementByReferenceIdAndSerial(
			Long referenceId, String serialCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getReportedElementByReferenceIdAndSerial/ReportedElementDAO ==");
        Session session = super.getSession();

        try {
        	
        	StringBuffer stringQuery = new StringBuffer("from ")
        		.append(ReportedElement.class.getName())
        		.append(" re where")
        		.append(" re.refInconsistency.reference.id = :referenceId")
        		.append(" and upper(re.serialCode) = upper(:serialCode)");
        		/*.append(" and re.refInconsistency.refIncStatus.refIncStatusCode = '")
        		.append(CodesBusinessEntityEnum.INCONSISTENCY_STATUS_ACTIVE.getCodeEntity()).append("'");*/
        		
        	Query query = session.createQuery(stringQuery.toString());
        	
        	query.setLong("referenceId", referenceId);
        	query.setString("serialCode", serialCode);
        	
        	return (ReportedElement) query.uniqueResult(); 
            
        } catch (Throwable ex){
			log.debug("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getReportedElementByReferenceIdAndSerial/ReportedElementDAO ==");
        }
	}
	
}
