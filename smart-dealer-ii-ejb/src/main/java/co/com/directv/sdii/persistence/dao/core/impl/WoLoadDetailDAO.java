package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.WoLoadDetail;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoLoadDetailDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WoLoadDetail
 * 
 * Fecha de Creación:Feb 10, 2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WoLoadDetail
 * @see co.com.directv.sdii.model.hbm.WoLoadDetail.hbm.xml
 */
@Stateless(name="WoLoadDetailDAOLocal",mappedName="ejb/WoLoadDetailDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoLoadDetailDAO extends BaseDao implements WoLoadDetailDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WoLoadDetailDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDetailDAOLocal#createWoLoadDetail(co.com.directv.sdii.model.pojo.WoLoadDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createWoLoadDetail(WoLoadDetail obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createWoLoadDetail/WoLoadDetailDAO ==");
        Session session = super.getSession();
        try {
        	List<WoLoadDetail> oldWoLoadDetail = getWoLoadDetailByWoCodeAndStatus(obj.getWoCode(), CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), obj.getCountry().getId());
        	
    		for (WoLoadDetail woLoadDetail : oldWoLoadDetail) {
				woLoadDetail.setRecordStatus(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
				updateWoLoadDetail(woLoadDetail);
			}
        	
        	obj.setRecordStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el WoLoadDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoLoadDetail/WoLoadDetailDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDetailDAOLocal#updateWoLoadDetail(co.com.directv.sdii.model.pojo.WoLoadDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateWoLoadDetail(WoLoadDetail obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateWoLoadDetail/WoLoadDetailDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el WoLoadDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateWoLoadDetail/WoLoadDetailDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDetailDAOLocal#deleteWoLoadDetail(co.com.directv.sdii.model.pojo.WoLoadDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteWoLoadDetail(WoLoadDetail obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteWoLoadDetail/WoLoadDetailDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from WoLoadDetail entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el WoLoadDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteWoLoadDetail/WoLoadDetailDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.WoLoadDetailDAOLocal#getWoLoadDetailsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public WoLoadDetail getWoLoadDetailByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWoLoadDetailByID/WoLoadDetailDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoLoadDetail.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (WoLoadDetail) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoLoadDetailByID/WoLoadDetailDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.core.WoLoadDetailDAOLocal#getWoLoadDetailByWoCode(java.lang.String, java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<WoLoadDetail> getWoLoadDetailByWoCode(String woCode, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllWoLoadDetailByWoCode/WoLoadDetailDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoLoadDetail.class.getName());
        	stringQuery.append(" wold where wold.woCode = :aWoCode and wold.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aWoCode", woCode);
        	query.setLong("aCountryId", countryId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllWoLoadDetailByWoCode/WoLoadDetailDAO ==");
        }
    }


    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.core.WoLoadDetailDAOLocal#getWoLoadDetailByWoCodeAndStatus(java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WoLoadDetail> getWoLoadDetailByWoCodeAndStatus(String woCode,
			String recordStatus, Long countryId) throws DAOServiceException,
			DAOSQLException {
    	log.debug("== Inicia getWoLoadDetailByWoCodeAndStatus/WoLoadDetailDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(WoLoadDetail.class.getName());
        	stringQuery.append(" wold where wold.woCode = :aWoCode and wold.recordStatus = :aRecordStatus and wold.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aWoCode", woCode);
        	query.setString("aRecordStatus", recordStatus);
        	query.setLong("aCountryId", countryId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWoLoadDetailByWoCodeAndStatus/WoLoadDetailDAO ==");
        }
	}
	
}
