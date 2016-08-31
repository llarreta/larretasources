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
import co.com.directv.sdii.model.pojo.ElementBrand;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ElementBrand
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementBrand
 * @see co.com.directv.sdii.model.hbm.ElementBrand.hbm.xml
 */
@Stateless(name="ElementBrandDAOLocal",mappedName="ejb/ElementBrandDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementBrandDAO extends BaseDao implements ElementBrandDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementBrandDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandsDAOLocal#createElementBrand(co.com.directv.sdii.model.pojo.ElementBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElementBrand/ElementBrandDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ElementBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementBrand/ElementBrandDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandsDAOLocal#updateElementBrand(co.com.directv.sdii.model.pojo.ElementBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElementBrand/ElementBrandDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ElementBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementBrand/ElementBrandDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandsDAOLocal#deleteElementBrand(co.com.directv.sdii.model.pojo.ElementBrand)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElementBrand/ElementBrandDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ElementBrand entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ElementBrand ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementBrand/ElementBrandDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandsDAOLocal#getElementBrandsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementBrand getElementBrandByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementBrandByID/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ElementBrand) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByID/ElementBrandDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandsDAOLocal#getAllElementBrands()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ElementBrand> getAllElementBrands() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElementBrands/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementBrand.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElementBrands/ElementBrandDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal#getElementBrandByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementBrand getElementBrandByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementBrandByID/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity where entity.brandCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (ElementBrand) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByID/ElementBrandDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal#getElementBrandByName(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementBrand getElementBrandByName(String name)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementBrandByName/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity where upper(entity.brandName) = :aName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aName", name.toUpperCase());

            return (ElementBrand) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByName/ElementBrandDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementBrandDAOLocal#getElementBrandByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<ElementBrand> getElementBrandByStatus(String codeEntity)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementBrandByStatus/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	stringQuery.append(" order by entity.brandCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("codeEntity", codeEntity);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByStatus/ElementBrandDAO ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	public ElementBrandResponse getElementBrandByStatus(String codeEntity,RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementBrandByStatus/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	stringQuery.append(" order by entity.brandCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("codeEntity", codeEntity);

            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		queryCount.setString("codeEntity", codeEntity);
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementBrandResponse response = new ElementBrandResponse();
        	List<ElementBrand> elementBrandList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementBrandList.size(), recordQty.intValue() );
        	response.setElementBrand(elementBrandList );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByStatus/ElementBrandDAO ==");
        }
	}



	
	@Override
	public ElementBrandResponse getElementBrandByAllStatuPage(String code,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getElementBrandByAllStatuPage/ElementBrandDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*)  ");
        	stringQuery.append(ElementBrand.class.getName());
        	stringQuery.append(" entity ");
        	if(code!=null){
        		stringQuery.append(" where entity.brandCode = :brandCode ");
        	}
        	stringQuery.append(" order by entity.brandCode asc");
        	Query query = session.createQuery(stringQuery.toString());
        	if(code!=null){
        		query.setString("brandCode", code);
        	}
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(code!=null){
        			queryCount.setString("brandCode", code);
            	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementBrandResponse response = new ElementBrandResponse();
        	List<ElementBrand> elementBrandList = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementBrandList.size(), recordQty.intValue() );
        	response.setElementBrand(elementBrandList );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementBrandByAllStatuPage/ElementBrandDAO ==");
        }
	}


}
