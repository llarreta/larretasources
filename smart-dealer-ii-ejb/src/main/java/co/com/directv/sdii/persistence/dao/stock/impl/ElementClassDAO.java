package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad ElementClass
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementClass
 * @see co.com.directv.sdii.model.hbm.ElementClass.hbm.xml
 */
@Stateless(name="ElementClassDAOLocal",mappedName="ejb/ElementClassDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementClassDAO extends BaseDao implements ElementClassDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ElementClassDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementClasssDAOLocal#createElementClass(co.com.directv.sdii.model.pojo.ElementClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createElementClass/ElementClassDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ElementClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createElementClass/ElementClassDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementClasssDAOLocal#updateElementClass(co.com.directv.sdii.model.pojo.ElementClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateElementClass/ElementClassDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ElementClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateElementClass/ElementClassDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementClasssDAOLocal#deleteElementClass(co.com.directv.sdii.model.pojo.ElementClass)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteElementClass(ElementClass obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteElementClass/ElementClassDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ElementClass entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el ElementClass ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteElementClass/ElementClassDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementClasssDAOLocal#getElementClasssByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ElementClass getElementClassByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getElementClassByID/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (ElementClass) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByID/ElementClassDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.ElementClasssDAOLocal#getAllElementClasss()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ElementClass> getAllElementClasss() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllElementClasss/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementClass.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllElementClasss/ElementClassDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal#getElementClassByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClass getElementClassByCode(String elementClassCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementClassByCode/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity where entity.elementClassCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", elementClassCode);

            return (ElementClass) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByCode/ElementClassDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal#getElementClassByName(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClass getElementClassByName(String elementClassName)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementClassByName/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity where upper(entity.elementClassName) = :aName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aName", elementClassName.toUpperCase());

            return (ElementClass) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByName/ElementClassDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal#getElementClassByStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ElementClass> getElementClassByStatus(String codeEntity)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getElementClassByStatus/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	stringQuery.append(" order by entity.elementClassCode asc");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("codeEntity", codeEntity);

            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByStatus/ElementClassDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal#getElementClassByStatus(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClassResponse getElementClassByStatus(String codeEntity,RequestCollectionInfo requestCollInfo)
		throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getElementClassByStatus/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity where entity.isActive = :codeEntity");
        	stringQuery.append(" order by entity.elementClassCode asc");
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
        	
        	ElementClassResponse response = new ElementClassResponse();
        	List<ElementClass> elementClass = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementClass.size(), recordQty.intValue() );
        	response.setElementClass(elementClass );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByStatus/ElementClassDAO ==");
        }
	}



	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.ElementClassDAOLocal#getElementClassByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClassResponse getElementClassByAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getElementClassByAllStatusPage/ElementClassDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringCount = new StringBuffer();
        	stringQuery.append("from ");
        	stringCount.append("select count(*) ");
        	stringQuery.append(ElementClass.class.getName());
        	stringQuery.append(" entity ");
        	if(code!=null){
        		stringQuery.append(" where entity.elementClassCode = :elementClassCode ");	
        	}
        	
        	stringQuery.append(" order by entity.elementClassCode asc");
        	Query query = session.createQuery(stringQuery.toString());
        	if(code!=null){
        		query.setString("elementClassCode", code);	
        	}
        	
            //Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){
        		stringCount.append(stringQuery.toString());
        		Query queryCount = session.createQuery(stringCount.toString());
        		if(code!=null){
        			queryCount.setString("elementClassCode", code);	
            	}
                recordQty = (Long)queryCount.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	ElementClassResponse response = new ElementClassResponse();
        	List<ElementClass> elementClass = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), elementClass.size(), recordQty.intValue() );
        	response.setElementClass(elementClass );
			
			return response;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getElementClassByAllStatusPage/ElementClassDAO ==");
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ElementClass getElementClassByElementSerialNumber(String serialNumber) throws DAOSQLException, DAOServiceException {
        try {
            log.debug("== Inicio getElementClassCodeByElementSerialNumber/ElementClassDAO ==");
            Session session = super.getSession();

            StringBuilder queryBuffer = new StringBuilder();
            queryBuffer.append(" SELECT EC.ELEMENT_CLASS_CODE elementClassCode, EC.ELEMENT_CLASS_NAME elementClassName, EC.IS_ACTIVE isActive ");
            queryBuffer.append(" FROM ELEMENTS E ");
            queryBuffer.append(" INNER JOIN SERIALIZED S ON (S.ELEMENT_ID = E.ID) ");
            queryBuffer.append(" INNER JOIN ELEMENT_TYPES ET ON (ET.ID = E.ELEMENT_TYPE_ID) ");
            queryBuffer.append(" INNER JOIN ELEMENT_MODELS EM ON (EM.ID = ET.ELEMENT_MODEL_ID) ");
            queryBuffer.append(" INNER JOIN ELEMENT_CLASSES EC ON (EC.ID = EM.ELEMENT_CLASS_ID) ");
            queryBuffer.append(" WHERE S.SERIAL_CODE = :serialNumber ");
            
            Query querySQL = session.createSQLQuery(queryBuffer.toString())
            					    .addScalar("elementClassCode", Hibernate.STRING)
                                    .addScalar("elementClassName", Hibernate.STRING)
                                    .addScalar("isActive", Hibernate.STRING)
            					    .setResultTransformer(Transformers.aliasToBean(ElementClass.class));
            
            querySQL.setParameter("serialNumber", serialNumber);
            
        	ElementClass elementClass = (ElementClass)querySQL.uniqueResult();
        	return elementClass;
        }catch (Throwable ex) {
            log.error("== Error getElementClassCodeByElementSerialNumber==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getElementClassCodeByElementSerialNumber/ElementClassDAO ==");
        }
	}

}
