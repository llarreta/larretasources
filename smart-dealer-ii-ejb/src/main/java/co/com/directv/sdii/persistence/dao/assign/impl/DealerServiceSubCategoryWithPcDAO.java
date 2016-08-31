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
import co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerServiceSubCategoryWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc
 * @see co.com.directv.sdii.model.hbm.DealerServiceSubCategoryWithPc.hbm.xml
 */
@Stateless(name="DealerServiceSubCategoryWithPcDAOLocal",mappedName="ejb/DealerServiceSubCategoryWithPcDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerServiceSubCategoryWithPcDAO extends BaseDao implements DealerServiceSubCategoryWithPcDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerServiceSubCategoryWithPcDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#createDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerServiceSubCategoryWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#updateDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerServiceSubCategoryWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#deleteDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerServiceSubCategoryWithPc entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerServiceSubCategoryWithPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getDealerServiceSubCategoryWithPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerServiceSubCategoryWithPc getDealerServiceSubCategoryWithPcByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerServiceSubCategoryWithPc.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerServiceSubCategoryWithPc) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getAllDealerServiceSubCategoryWithPcs()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerServiceSubCategoryWithPc> getAllDealerServiceSubCategoryWithPcs() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerServiceSubCategoryWithPc.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcDAO ==");
        }
    }
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getDealersWhoAttendServiceSubCategoryWithCoverage(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Dealer> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	
        	stringQuery.append("  select distinct dc.dealer  ");
        	stringQuery.append("    from " + DealerServiceSubCategoryWithPc.class.getName() + " dsswp ");
        	stringQuery.append("         inner join dsswp.dealerCoverage dc ");
        	stringQuery.append("         inner join dsswp.serviceCategory sc  ");
        	stringQuery.append("         inner join dc.postalCode pc ");
        	stringQuery.append("         inner join pc.city ci ");
        	stringQuery.append("         inner join ci.state st ");
        	stringQuery.append("   where dc.country.countryCode = :aCountryCode ");
        	stringQuery.append("         and dc.status = :aStatusCode  ");
        	stringQuery.append("         and dsswp.status = :aStatusCode  ");
        	stringQuery.append("         and pc.postalCodeCode = :aPostalCode  ");
        	stringQuery.append(" 	    and st.country.countryCode = :aCountryCode  ");
        	stringQuery.append(" 		and exists(select 1  ");
        	stringQuery.append(" 			         from " + Service.class.getName() + " ser  ");
        	stringQuery.append(" 			        where sc.id = ser.serviceCategory.id  ");
        	stringQuery.append(" 			              and ser.serviceCode = :aServiceCode) ");
        	
//        	stringQuery.append(" select distinct dc.dealer ");
//        	stringQuery.append("   from " + DealerCoverage.class.getName() + " dc ");
//        	stringQuery.append("  where dc.country.countryCode = :aCountryCode ");
//         	stringQuery.append("        and dc.status = :aStatusCode ");
//        	stringQuery.append("        and  dc.postalCode.id in ");
//        	stringQuery.append(" ( select pc.id ");
//        	stringQuery.append("     from " + PostalCode.class.getName() + " pc, " );
//        	stringQuery.append( City.class.getName() );
//        	stringQuery.append(" ci, " );
//        	stringQuery.append( State.class.getName() );
//        	stringQuery.append(" st, " );
//        	stringQuery.append( Country.class.getName() );
//        	stringQuery.append(" co " );
//        	stringQuery.append(" where pc.postalCodeCode = :aPostalCode ");
//        	stringQuery.append("       and pc.city.id=ci.id ");
//        	stringQuery.append("       and ci.state.id=st.id ");
//        	stringQuery.append("       and st.country.id=co.id ");
//        	stringQuery.append("       and co.countryCode = :aCountryCode ) ");
//        	stringQuery.append("       and dc.id in ( " );
//
//        	stringQuery.append(" select dsswp.dealerCoverage.id " );        	
//        	stringQuery.append(" from  " );        	
//        	stringQuery.append(  DealerServiceSubCategoryWithPc.class.getName() );        	
//        	stringQuery.append(" dsswp, " );        	
//        	stringQuery.append( ServiceCategory.class.getName() );        	
//        	stringQuery.append( " sc " );        	
//        	stringQuery.append("where dsswp.serviceCategory.id = sc.id " );
//        	stringQuery.append("and sc.id in (" );        	
//
//        	stringQuery.append("select ser.serviceCategory.id " );        	
//        	stringQuery.append("from " );        	
//        	stringQuery.append( Service.class.getName() );        	
//        	stringQuery.append(" ser " );        	
//
//        	stringQuery.append("where ser.serviceCode = :aServiceCode" );        	
//
//        	stringQuery.append(" ) )" );
        	
        	log.debug("DealerServiceSubCategoryDAO:getDealersWhoAttendServiceSubCategory: stringQuery >>" + stringQuery + "<<");
        	
        	Query query =  session.createQuery(stringQuery.toString());
        	
        	query.setString("aServiceCode", serviceCode);
        	query.setString("aCountryCode", countryCode);
        	query.setString("aPostalCode", postalCode);
        	query.setString( "aStatusCode", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity() );
        	
        	List<Dealer> dealers = query.list();
        	return dealers;
        } catch (Throwable ex){
			log.error("== Error getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getDealerServiceSubCategoryWithPcsByCountryAndStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerServiceSubCategoryWithPc> getDealerServiceSubCategoryWithPcsByCountryAndStatus(
			Long countryId, String status) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getDealerServiceSubCategoryWithPcsByCountryAndStatus/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerServiceSubCategoryWithPc.class.getName());
        	stringQuery.append(" entity where entity.dealerCoverage.country.id = :countryId");
        	stringQuery.append(" and entity.status = :status");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("status", status);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getDealerServiceSubCategoryWithPcsByCountryAndStatus/DealerServiceSubCategoryWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerServiceSubCategoryWithPcsByCountryAndStatus/DealerServiceSubCategoryWithPcDAO ==");
        }
	}
	
	 /*
    * (non-Javadoc)
    * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getDealerServiceSubCategoriesWithPcActiveByPostalCodeId(java.lang.Long, java.lang.Long)
    */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerServiceSubCategoryWithPc> getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType(Long dealerCoverageId) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();

        try {
        	String mainData = new StringBuffer("from ")
	    		.append(DealerServiceSubCategoryWithPc.class.getName())
	    		.append(" entity where entity.dealerCoverage.id = :dealerCoverageId").toString();
			
	    	String complementData = new StringBuffer(" select new ").append(DealerServiceSubCategoryWithPc.class.getName())
	    		.append("(cct, ").append(dealerCoverageId).append("L) from ")
	    		.append(ServiceCategory.class.getName()).append(" cct where cct.id not in ")
	    		.append("(select entity1.serviceCategory.id from ").append(DealerServiceSubCategoryWithPc.class.getName())
	    		.append(" entity1 where entity1.dealerCoverage.id = :dealerCoverageId)").toString();
	    	
	    	Query query = session.createQuery(mainData);
	    	query.setLong("dealerCoverageId", dealerCoverageId);
	    	List<DealerServiceSubCategoryWithPc> mainResult = query.list();
	    	
	    	query = session.createQuery(complementData);
	    	query.setLong("dealerCoverageId", dealerCoverageId);
	    	List<DealerServiceSubCategoryWithPc> complementResult = query.list();
	    	
	    	mainResult.addAll(complementResult);

	    	Collections.sort(mainResult, CONFIGURATION_ORDER);
	    	
	    	return mainResult;
            
        } catch (Throwable ex){
			log.error("== Error getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType/DealerServiceSubCategoryWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType/DealerServiceSubCategoryWithPcDAO ==");
        }
    }
    
    /**
     * Comparator empleado para ordenar por id del tipo de servicio
     */
    private static final Comparator<DealerServiceSubCategoryWithPc> CONFIGURATION_ORDER = new Comparator<DealerServiceSubCategoryWithPc>() {
        public int compare(DealerServiceSubCategoryWithPc e1, DealerServiceSubCategoryWithPc e2) {
			try {
				int idServiceTypeCompare = e1.getServiceCategory().getServiceType().getId().compareTo(e2.getServiceCategory().getServiceType().getId());
				return idServiceTypeCompare;
			} catch (Exception e) {
				return 0;
			}
		}
	};


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal#getByDealerCoverageIdAndServiceCategotyId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public DealerServiceSubCategoryWithPc getByDealerCoverageIdAndServiceCategotyId(
			Long dealerCoverageId, Long serviceCategoryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(DealerServiceSubCategoryWithPc.class.getName());
        	stringQuery.append(" entity where entity.dealerCoverage = :dealerCoverageId and entity.serviceCategory = :serviceCategoryId");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("dealerCoverageId", dealerCoverageId);
            query.setLong("serviceCategoryId", serviceCategoryId);

            return (DealerServiceSubCategoryWithPc) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcDAO ==");
        }
	}
    
}
