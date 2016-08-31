package co.com.directv.sdii.persistence.dao.assign.impl;

import java.util.Collections;
import java.util.Comparator;
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
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerServiceSubCategory;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerServiceSubCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerServiceSubCategory
 * @see co.com.directv.sdii.model.hbm.DealerServiceSubCategory.hbm.xml
 */
@Stateless(name="DealerServiceSubCategoryDAOLocal",mappedName="ejb/DealerServiceSubCategoryDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerServiceSubCategoryDAO extends BaseDao implements DealerServiceSubCategoryDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerServiceSubCategoryDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#createDealerServiceSubCategory(co.com.directv.sdii.model.pojo.DealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#updateDealerServiceSubCategory(co.com.directv.sdii.model.pojo.DealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#deleteDealerServiceSubCategory(co.com.directv.sdii.model.pojo.DealerServiceSubCategory)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerServiceSubCategory(DealerServiceSubCategory obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerServiceSubCategory entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerServiceSubCategory ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerServiceSubCategory/DealerServiceSubCategoryDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#getDealerServiceSubCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerServiceSubCategory getDealerServiceSubCategoryByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerServiceSubCategoryByID/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerServiceSubCategory.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerServiceSubCategory) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerServiceSubCategoryByID/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerServiceSubCategoryByID/DealerServiceSubCategoryDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#getAllDealerServiceSubCategorys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerServiceSubCategory> getAllDealerServiceSubCategorys() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerServiceSubCategorys/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerServiceSubCategory.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerServiceSubCategorys/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerServiceSubCategorys/DealerServiceSubCategoryDAO ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dealer> getDealersWhoAttendServiceCategory(String countryCode, String serviceCode)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getDealersWhoAttendServiceCategory/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select distinct dssc.dealer ");
        	stringQuery.append("   from " + DealerServiceSubCategory.class.getName() + " dssc ");
        	stringQuery.append("  where dssc.country.countryCode = :aCountryCode ");
        	stringQuery.append("        and dssc.status = :aStatusCode ");
        	stringQuery.append("        and exists(select 1 ");
        	stringQuery.append("                     from " + Service.class.getName() + " ser ");
        	stringQuery.append("                    where ser.serviceCode = :aServiceCode ");
        	stringQuery.append("                          and ser.serviceStatus.serviceStatusCode = :aServiceStatusCode ");
        	stringQuery.append("                          and dssc.serviceCategory.serviceType.id = ser.serviceCategory.serviceType.id) ");
        	Query query =  session.createQuery(stringQuery.toString());
        	query.setString("aServiceCode", serviceCode.trim() );
        	query.setString("aCountryCode", countryCode.trim());
        	query.setString("aStatusCode", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	query.setString("aServiceStatusCode", CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_ACTIVE.getCodeEntity() );
        	
        	List<Dealer> dealers = query.list();
        	return dealers;
        } catch (Throwable ex){
			log.error("== Error getDealersWhoAttendServiceCategory/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealersWhoAttendServiceCategory/DealerServiceSubCategoryDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dealer> getDealersWhoAttendServiceSubCategory(String countryCode, String serviceCode)throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select distinct dssc.dealer  ");
        	stringQuery.append("  from " + DealerServiceSubCategory.class.getName() + " dssc  ");
        	stringQuery.append(" where exists(select 1  ");
        	stringQuery.append("                from Service ser  ");
        	stringQuery.append("               where ser.serviceCode = :aServiceCode  ");
        	stringQuery.append("                     and ser.serviceCategory.id=dssc.serviceCategory.id) ");
        	stringQuery.append("       and dssc.country.countryCode = :aCountryCode ");
        	stringQuery.append("       and dssc.status = :aStatusCode ");
        	
        	Query query =  session.createQuery(stringQuery.toString());
        	query.setString("aServiceCode", serviceCode);
        	query.setString("aCountryCode", countryCode);
        	query.setString("aStatusCode", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	
        	List<Dealer> dealers = query.list();
        	return dealers;
        } catch (Throwable ex){
			log.error("== Error getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal#getAllActiveByDealerId(java.lang.Long)
     */
    @Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerServiceSubCategory> getAllConfigurationActiveByDealerIdAndCountryIdOrderedByServiceType(Long dealerId, Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllConfigurationActiveByDealerId/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();

        try {
        	String mainData = new StringBuffer("from " + DealerServiceSubCategory.class.getName() + " entity ") 
					        		 .append(" where entity.dealer.id = :dealerId")
//						    		 .append("       and exists( select 1 ")
//						    		 .append("                     from "+Service.class.getName()+" s ")
//						    		 .append("                    where s.serviceCategory.id=entity.serviceCategory.id ")
//						    		 .append("                          and length(s.serviceCode)<=3 )")
						    		 .toString();
        	
    		
	    	String complementData = new StringBuffer(" select new ").append(DealerServiceSubCategory.class.getName())
	    		                           .append("                 (cct, ").append(countryId).append("L) ")
	    		                           .append("     from " + ServiceCategory.class.getName()).append(" cct ")
	    		                           .append("    where not exists(select 1 ")
	    		                           .append("                       from ").append(DealerServiceSubCategory.class.getName() + " entity1 ")
	    		                           .append("                      where entity1.dealer.id = :dealerId ")
	    		                           .append("                            and cct.id=entity1.serviceCategory.id) ")
//	    		                           .append("                            and exists( select 1 ")
//	    		                           .append("                                          from "+Service.class.getName()+" s ")
//	    		                           .append("                                         where s.serviceCategory.id=entity1.serviceCategory.id ")
//	    		                           .append("                                               and length(s.serviceCode)<=3 ) ) ")
//	    		                           .append("          and exists( select 1 ")
//	    		                           .append("                        from "+Service.class.getName()+" s ")
//	    		                           .append("                       where s.serviceCategory.id=cct.id ")
//	    		                           .append("  							 and length(s.serviceCode)<=3 )")
	    		.toString();
	    	
	    	Query query = session.createQuery(mainData);
	    	query.setLong("dealerId", dealerId);
	    	List<DealerServiceSubCategory> mainResult = query.list();
	    	
	    	query = session.createQuery(complementData);
	    	query.setLong("dealerId", dealerId);
	    	List<DealerServiceSubCategory> complementResult = query.list();
	    	
	    	mainResult.addAll(complementResult);
	    	/*
	    	System.err.println("antes de ordenar");
	    	for (DealerServiceSubCategory dealerServiceSubCategory : mainResult) {
				System.err.println("id = " + dealerServiceSubCategory.getId() + ". Name = " + dealerServiceSubCategory.getServiceCategory().getServiceCategoryName());
			}
	    	*/
	    	Collections.sort(mainResult, CONFIGURATION_ORDER);
	    	/*System.err.println("despues de ordenar");
	    	for (DealerServiceSubCategory dealerServiceSubCategory : mainResult) {
				System.err.println("id = " + dealerServiceSubCategory.getId() + ". Name = " + dealerServiceSubCategory.getServiceCategory().getServiceCategoryName());
			}
	    	*/
	    	return mainResult;
        } catch (Throwable ex){
			log.error("== Error getAllConfigurationActiveByDealerId/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllConfigurationActiveByDealerId/DealerServiceSubCategoryDAO ==");
        }
	}

    /**
     * Comparator empleado para ordenar por id y nombre de categoría el resultado en el método getAllConfigurationActiveByDealerIdAndCountryIdOrderedByServiceType
     */
    private static final Comparator<DealerServiceSubCategory> CONFIGURATION_ORDER = new Comparator<DealerServiceSubCategory>() {
        public int compare(DealerServiceSubCategory e1, DealerServiceSubCategory e2) {
			try {
				int idCompare = e1.getServiceCategory().getServiceType().getId().compareTo(e2.getServiceCategory().getServiceType().getId());
				if(idCompare == 0) {
					int categoryNameCompare = e1.getServiceCategory().getServiceCategoryName().compareTo(e2.getServiceCategory().getServiceCategoryName());
					return categoryNameCompare;
				}
				return idCompare;
			} catch (Exception e) {
				return 0;
			}
		}
	};
	
	private void saveAuditEnvers(DealerServiceSubCategory obj){
    	if(obj!=null){
    		if(obj.getDealer()!=null){
    			obj.setDealerId(obj.getDealer().getId());
    		}
    		if(obj.getUser()!=null){
    			obj.setUserId(obj.getUser().getId());
    		}
    		if(obj.getServiceCategory()!=null){
    			obj.setServiceCategoryId(obj.getServiceCategory().getId());
    		}
    		if(obj.getCountry()!=null){
    			obj.setCountryId(obj.getCountry().getId());
    		}
    		
    	}
    }
    
}
