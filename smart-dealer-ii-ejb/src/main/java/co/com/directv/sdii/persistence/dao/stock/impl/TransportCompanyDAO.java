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
import co.com.directv.sdii.model.pojo.TransportCompany;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransportCompanyResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad TransportCompany
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.TransportCompany
 * @see co.com.directv.sdii.model.hbm.TransportCompany.hbm.xml
 */
@Stateless(name="TransportCompanyDAOLocal",mappedName="ejb/TransportCompanyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TransportCompanyDAO extends BaseDao implements TransportCompanyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(TransportCompanyDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#createTransportCompany(co.com.directv.sdii.model.pojo.TransportCompany)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createTransportCompany/TransportCompanyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el TransportCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTransportCompany/TransportCompanyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#updateTransportCompany(co.com.directv.sdii.model.pojo.TransportCompany)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateTransportCompany/TransportCompanyDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el TransportCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTransportCompany/TransportCompanyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#deleteTransportCompany(co.com.directv.sdii.model.pojo.TransportCompany)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteTransportCompany/TransportCompanyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from TransportCompany entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el TransportCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTransportCompany/TransportCompanyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getTransportCompanysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TransportCompany getTransportCompanyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getTransportCompanyByID/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (TransportCompany) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransportCompanyByID/TransportCompanyDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getAllTransportCompanys()
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<TransportCompany> getTransportCompaniesByCountryId(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllTransportCompanys/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" tc where tc.countryCodeId.id = :aCountryId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryId", countryId);
        	
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllTransportCompanys/TransportCompanyDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getTransportCompanyByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompany getTransportCompanyByCode(String code)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getTransportCompanyByCode/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" entity where entity.companyCode = :aTcCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aTcCode", code);

            return (TransportCompany) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransportCompanyByCode/TransportCompanyDAO ==");
        }
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getTransportCompanyByCodeAndCountryId(java.lang.String, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompany getTransportCompanyByCodeAndCountryId(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicio getTransportCompanyByCodeAndCountryId/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" entity where entity.companyCode = :aTcCode and entity.countryCodeId.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aTcCode", code);
            query.setLong("aCountryId", countryId);

            return (TransportCompany) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error en la operacion getTransportCompanyByCodeAndCountryId/TransportCompanyDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTransportCompanyByCodeAndCountryId/TransportCompanyDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getActiveTransportCompanys()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyResponse getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveTransportCompanys/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//paginacion
        	stringCount.append( "select count(*) " );
        	
        	//Cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" tc where tc.isActive = :aTcStatus and tc.countryCodeId.id = :aCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("aTcStatus", CodesBusinessEntityEnum.TRANSPORT_COMP_STATUS_ACTIVE.getCodeEntity());
        	query.setLong("aCountryId", countryId);
        	
        	//Paginacion
        	TransportCompanyResponse response = new TransportCompanyResponse();
         	stringCount.append( stringQuery.toString() );
        	Query countQuery = session.createQuery( stringCount.toString() );
        	countQuery.setString("aTcStatus", CodesBusinessEntityEnum.TRANSPORT_COMP_STATUS_ACTIVE.getCodeEntity());
        	countQuery.setLong("aCountryId", countryId);
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	      
        		recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );					
        	}
        	
        	List<TransportCompany> transportCompanies = query.list();       
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), transportCompanies.size(), recordQty.intValue());
        	response.setTransportCompanies(transportCompanies);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getActiveTransportCompanys/TransportCompanyDAO ==");
        }
	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.TransportCompanyDAOLocal#getActiveTransportCompanys()
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TransportCompanyResponse getAllTransportCompaniesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllTransportCompaniesByCountryId/TransportCompanyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//paginacion
        	stringCount.append( "select count(*) " );
        	
        	//Cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(TransportCompany.class.getName());
        	stringQuery.append(" tc where tc.countryCodeId.id = :aCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryId", countryId);
        	
        	//Paginacion
        	TransportCompanyResponse response = new TransportCompanyResponse();
         	stringCount.append( stringQuery.toString() );
        	Query countQuery = session.createQuery( stringCount.toString() );
        	countQuery.setLong("aCountryId", countryId);
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	      
        		recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );					
        	}
        	
        	List<TransportCompany> transportCompanies = query.list();       
        	if( requestCollInfo != null )
        		populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), transportCompanies.size(), recordQty.intValue());
        	response.setTransportCompanies(transportCompanies);
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllTransportCompaniesByCountryId/TransportCompanyDAO ==");
        }
	}

}
