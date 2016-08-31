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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ServiceCategory
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ServiceCategory
 * @see co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal
 */
@Stateless(name="ServiceCategoryDAOLocal",mappedName="ejb/ServiceCategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceCategoryDAO extends BaseDao implements ServiceCategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(ServiceCategoryDAO.class);

    /**
     * Crea una ServiceCategory en el sistema
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createServiceCategory/DAOServiceCategoryBean ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ServiceCategory ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createServiceCategory/DAOServiceCategoryBean ==");
        }
    }

    /**
     * Obtiene un servicecategory con el id especificado
     * @param id - Long
     * @return - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceCategory getServiceCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceCategoryByID/DAOServiceCategoryBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select servicecategory from ");
        	stringQuery.append(ServiceCategory.class.getName());
        	stringQuery.append(" servicecategory where servicecategory.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select servicecategory from " + ServiceCategory.class.getName() + " servicecategory where servicecategory.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (ServiceCategory) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceCategory por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByID/DAOServiceCategoryBean ==");
        }
    }

    /**
     * Actualiza un servicecategory especificado
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateServiceCategory/DAOServiceCategoryBean ==");
        Session session = super.getSession();

        try {
            session.merge(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ServiceCategory ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateServiceCategory/DAOServiceCategoryBean ==");
        }

    }

    /**
     * Elimina un servicecategory del sistema
     * @param obj - ServiceCategory
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteServiceCategory(ServiceCategory obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteServiceCategory/DAOServiceCategoryBean ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceCategory ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteServiceCategory/DAOServiceCategoryBean ==");
        }

    }

    /**
     * Obtiene todos los servicecategorys del sistema
     * @return - List<ServiceCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceCategory> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOServiceCategoryBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("from "+ ServiceCategory.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceCategory ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/DAOServiceCategoryBean ==");
        }
    }

    /**
     * Obtiene todos los servicecategorys del sistema pertenecientes a una categoria especifica
     * @return - List<ServiceCategory>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceCategory> getServiceCategoryByTypeId(Long typeId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getServiceCategoryByTypeId/DAOServiceCategoryBean ==");
        Session session = super.getSession();

        try {
            Query query = session.createQuery("select sc from " + ServiceCategory.class.getName() + " sc where sc.serviceType.id = :id");
            query.setLong("id", typeId);
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error borrando el ServiceCategory ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByTypeId/DAOServiceCategoryBean ==");
        }
    }

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceCategory> getServiceCategoryByTypesId(List<Long> typesId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getServiceCategoryByTypesId/DAOServiceCategoryBean ==");
        Session session = super.getSession();

        try {
        	StringBuffer queryString = new StringBuffer();
        	queryString.append("select sc from ");
        	queryString.append(ServiceCategory.class.getName());
        	queryString.append(" sc ");
        	if(typesId.size()>0){        		
        		queryString.append(" where ");
        		//Itera la lista de id's
        		for(int i = 0; i<typesId.size();i++ ){
        			if(i==0){
        				queryString.append(" sc.serviceType.id = :id"+i);
        			}else{
        				queryString.append(" or sc.serviceType.id = :id"+i);
        			}
        		}
        		
        	}
        	queryString.append( " order by sc.serviceCategoryName asc");
            Query query = session.createQuery(queryString.toString());
            if(typesId.size()>0){
        		queryString.append(" sc where ");
        		//Itera la lista de id's
        		for(int i = 0; i<typesId.size();i++ ){
        			 query.setLong("id"+i, typesId.get(i));
        		}
        		
        	}
           
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando ServiceCategory  portypesId ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByTypesId/DAOServiceCategoryBean ==");
        }
	}
    
	 /*
	  * (non-Javadoc)
	  * @see co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal#getServiceCategoryByCode(java.lang.String)
	  */
	@Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ServiceCategory getServiceCategoryByCode(String serviceCategoryCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getServiceCategoryByID/DAOServiceCategoryBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer("from ");
        	stringQuery.append(ServiceCategory.class.getName());
        	stringQuery.append(" where serviceCategoryCode = :serviceCategoryCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("serviceCategoryCode", serviceCategoryCode);
        	return (ServiceCategory) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando el ServiceCategory por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByID/DAOServiceCategoryBean ==");
        }
    }
	
	/**
	* Req-0096 - Requerimiento Consolidado Asignador
    * Obtiene ServiceCategory cuyos ServiceTypes pertenecen a determinada Area de Negocio 
    * @param businessArea - Long
    * @return List<ServiceCategory> - Lista de ServiceCategory 
    * @throws DAOServiceException
    * @throws DAOSQLException
    * @author ialessan
    */
	@Override
	public List<ServiceCategory> getServiceCategoryByServiceTypeBusinessArea(Long businessAreaId)throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getServiceCategoryByServiceTypeBusinessArea/DAOServiceCategoryBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer("from ");
        	stringQuery.append(ServiceCategory.class.getName()+ " sc ");
        	stringQuery.append(" where sc.serviceType.businessAreaId = :businessAreaId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("businessAreaId", businessAreaId);
        	return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getServiceCategoryByServiceTypeBusinessArea el ServiceCategory por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByServiceTypeBusinessArea/DAOServiceCategoryBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal#getServiceCategoryByServiceCode(java.lang.String)
	 */
	@Override
	public ServiceCategory getServiceCategoryByServiceCode(String serviceCode) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicio getServiceCategoryByServiceCode/DAOServiceCategoryBean ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer("");
        	stringQuery.append("FROM " + ServiceCategory.class.getName());
        	stringQuery.append(" sc WHERE sc.id IN ( ");
        	stringQuery.append(" 	SELECT s.serviceCategory.id FROM " + Service.class.getName());
        	stringQuery.append(" 	s WHERE s.serviceCode = :serviceCode ) ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("serviceCode", serviceCode);
        	return (ServiceCategory)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error getServiceCategoryByServiceCode el ServiceCategory por ID ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getServiceCategoryByServiceCode/DAOServiceCategoryBean ==");
        }
		
	}
}
