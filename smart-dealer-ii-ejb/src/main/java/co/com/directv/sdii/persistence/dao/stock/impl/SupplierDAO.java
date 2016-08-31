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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Supplier
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Supplier
 * @see co.com.directv.sdii.model.hbm.Supplier.hbm.xml
 */
@Stateless(name="SupplierDAOLocal",mappedName="ejb/SupplierDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SupplierDAO extends BaseDao implements SupplierDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SupplierDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SuppliersDAOLocal#createSupplier(co.com.directv.sdii.model.pojo.Supplier)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSupplier(Supplier obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSupplier/SupplierDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Supplier ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSupplier/SupplierDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SuppliersDAOLocal#updateSupplier(co.com.directv.sdii.model.pojo.Supplier)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSupplier(Supplier obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSupplier/SupplierDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Supplier ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSupplier/SupplierDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SuppliersDAOLocal#deleteSupplier(co.com.directv.sdii.model.pojo.Supplier)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSupplier(Supplier obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSupplier/SupplierDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Supplier entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Supplier ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSupplier/SupplierDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SuppliersDAOLocal#getSuppliersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Supplier getSupplierByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSupplierByID/SupplierDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Supplier) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSupplierByID/SupplierDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.SuppliersDAOLocal#getAllSuppliers()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SuppliersResponse getAllSuppliers( Long countryId, RequestCollectionInfo requestCollInfo ) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSuppliers/SupplierDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	
        	//Paginacion
        	stringCount.append("select count(*) ");
        	//Cuerpo de la consulta
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName());
        	stringQuery.append(" supplier where supplier.countryIdHSP.id = :aCountryId");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryId", countryId);
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setLong("aCountryId", countryId);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	SuppliersResponse response = new SuppliersResponse();
        	List<Supplier> suppliers = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), suppliers.size(), recordQty.intValue() );
        	response.setSuppliers( suppliers );
        	
        	return response;
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSuppliers/SupplierDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal#getSupplierByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Supplier getSupplierByCode(String code) throws DAOServiceException,
			DAOSQLException {
		 log.debug("== Inicio getSupplierByCode/SupplierDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append("from ");
	        	stringQuery.append(Supplier.class.getName());
	        	stringQuery.append(" entity where entity.supplierCode = :aCode");
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setString("aCode", code);

	            return (Supplier) query.uniqueResult();

	        } catch (Throwable ex){
				log.error("== Error ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getSupplierByCode/SupplierDAO ==");
	        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal#getSupplierByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Supplier> getSupplierByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSupplierByCountryId/SupplierDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName());
        	stringQuery.append(" entity where entity.countryId = :aCountry");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountry", countryId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSupplierByCountryId/SupplierDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal#getSupplierByNit(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Supplier getSupplierByNit(String nit) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getSupplierByCode/SupplierDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName());
        	stringQuery.append(" entity where entity.supplierNit = :aNit");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aNit", nit);

            return (Supplier) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSupplierByCode/SupplierDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal#getSupplierByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Supplier> getSupplierByStatus(String codeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSupplierByStatus/SupplierDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName());
        	stringQuery.append(" entity where entity.isActive = :aCodeStatus");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCodeStatus", codeStatus);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSupplierByStatus/SupplierDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.SupplierDAOLocal#getSupplierByStatusAndCountry(java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Supplier> getSupplierByStatusAndCountry(String codeStatus,
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSupplierByStatusAndCountry/SupplierDAO ==");
		Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Supplier.class.getName()+" entity ");
        	stringQuery.append(" left join fetch entity.postalCode pc ");
        	stringQuery.append(" left join fetch pc.city ct ");
        	stringQuery.append(" left join fetch ct.state st ");
        	stringQuery.append(" left join fetch pc.zoneType zt");
        	stringQuery.append(" where entity.isActive = :aCodeStatus and");
        	stringQuery.append(" entity.countryIdHSP.id = :anIdCountry");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCodeStatus", codeStatus);
            query.setLong("anIdCountry", countryId);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSupplierByStatusAndCountry/SupplierDAO ==");
        }
	}

}
