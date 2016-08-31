package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.DealerDetail;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerDetail
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerDetail
 * @see co.com.directv.sdii.model.hbm.DealerDetail.hbm.xml
 */
@Stateless(name="DealerDetailDAOLocal",mappedName="ejb/DealerDetailDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerDetailDAO extends BaseDao implements DealerDetailDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerDetailDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#createDealerDetail(co.com.directv.sdii.model.pojo.DealerDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerDetail/DealerDetailDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerDetail/DealerDetailDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#updateDealerDetail(co.com.directv.sdii.model.pojo.DealerDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerDetail/DealerDetailDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerDetail/DealerDetailDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#deleteDealerDetail(co.com.directv.sdii.model.pojo.DealerDetail)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerDetail/DealerDetailDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerDetail entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getDealerId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerDetail ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerDetail/DealerDetailDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#getDealerDetailsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerDetail getDealerDetailByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerDetailByID/DealerDetailDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerDetail.class.getName());
        	stringQuery.append(" entity where entity.dealerId = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerDetail) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerDetailByID/DealerDetailDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerDetailByID/DealerDetailDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#getAllDealerDetails()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerDetail> getAllDealerDetails() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerDetails/DealerDetailDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerDetail.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerDetails/DealerDetailDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerDetails/DealerDetailDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal#getAllDealerDetailsByCountry(java.lang.Long)
	 */
	@Override
	public List<DealerDetail> getAllDealerDetailsByCountry(Long idCountry)
			throws DAOServiceException, DAOSQLException {
		 log.debug("== Inicio getAllDealerDetailsByCountry/DealerDetailDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(DealerDetail.class.getName());
	        	stringQuery.append(" entity where entity.dealer.postalCode.city.state.country.id = :anId");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("anId", idCountry);

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error getAllDealerDetailsByCountry/DealerDetailDAO ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getAllDealerDetailsByCountry/DealerDetailDAO ==");
	        }
	}

}
