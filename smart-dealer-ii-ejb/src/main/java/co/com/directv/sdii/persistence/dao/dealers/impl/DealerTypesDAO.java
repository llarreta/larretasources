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
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerTypes
 * @see co.com.directv.sdii.model.hbm.DealerTypes.hbm.xml
 */
@Stateless(name="DealerTypesDAOLocal",mappedName="ejb/DealerTypesDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerTypesDAO extends BaseDao implements DealerTypesDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerTypesDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#createDealerType(co.com.directv.sdii.model.pojo.DealerType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerType(DealerType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerType/DealerTypesDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el DealerType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerType/DealerTypesDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#updateDealerType(co.com.directv.sdii.model.pojo.DealerType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerType(DealerType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerType/DealerTypesDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el DealerType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerType/DealerTypesDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#deleteDealerType(co.com.directv.sdii.model.pojo.DealerType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerType(DealerType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerType/DealerTypesDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerType dt where dt.id = :aDtId");
            query.setLong("aDtId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el DealerType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerType/DealerTypesDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#getDealerTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerType getDealerTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerTypesByID/DealerTypesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerType.class.getName());
        	stringQuery.append(" dealerTypes where dealerTypes.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+DealerType.class.getName()+" dealerTypes where dealerTypes.id = :anId");
            query.setLong("anId", id);

            return (DealerType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerTypesByID/DealerTypesDAO ==");
        }
    }

    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#getDealerTypesByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerType getDealerTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerTypesByCode/DealerTypesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerType.class.getName());
        	stringQuery.append(" dealerTypes where dealerTypes.dealerTypeCode = :dealerTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+DealerType.class.getName()+" dealerTypes where dealerTypes.dealerTypeCode = :dealerTypeCode");
            query.setString("dealerTypeCode", code);

            return (DealerType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerTypesByCode/DealerTypesDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#getAllDealerTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerType> getAllDealerTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerTypes/DealerTypesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("from "+DealerType.class.getName()).list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerTypes/DealerTypesDAO ==");
        }
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealerTypesDAOLocal#getDealerTypesByIsAlloc(java.lang.String)
	 */
	@Override
	public List<DealerType> getDealerTypesByIsAlloc(String isAlloc)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerTypesByIsAlloc/DealerTypesDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerType.class.getName());
        	stringQuery.append(" dt where dt.isAlloc = :anIsAlloc");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("anIsAlloc", isAlloc);
        	return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerTypesByIsAlloc/DealerTypesDAO ==");
        }
	}
}
