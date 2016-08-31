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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerCustomerTypesWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc
 * @see co.com.directv.sdii.model.hbm.DealerCustomerTypesWoutPc.hbm.xml
 */
@Stateless(name="DealerCustomerTypesWoutPcDAOLocal",mappedName="ejb/DealerCustomerTypesWoutPcDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCustomerTypesWoutPcDAO extends BaseDao implements DealerCustomerTypesWoutPcDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerCustomerTypesWoutPcDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#createDealerCustomerTypesWoutPc(co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerCustomerTypesWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#updateDealerCustomerTypesWoutPc(co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        saveAuditEnvers(obj);
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el DealerCustomerTypesWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#deleteDealerCustomerTypesWoutPc(co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPc obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from DealerCustomerTypesWoutPc entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el DealerCustomerTypesWoutPc ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#getDealerCustomerTypesWoutPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerCustomerTypesWoutPc getDealerCustomerTypesWoutPcByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerCustomerTypesWoutPc.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (DealerCustomerTypesWoutPc) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#getAllDealerCustomerTypesWoutPcs()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerCustomerTypesWoutPc> getAllDealerCustomerTypesWoutPcs() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerCustomerTypesWoutPc.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcDAO ==");
        }
    }



	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersWhoAttendCustomerTypeWoutCoverage(
			String countryCode, String customerTypeCode, String customerClassCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append(" select dctwpc.dealer ");
        	stringQuery.append("   from " + DealerCustomerTypesWoutPc.class.getName() + " dctwpc ");
        	stringQuery.append("  where dctwpc.country.countryCode = :aCountryCode ");
        	stringQuery.append("        and dctwpc.customerClassType.customerType.customerTypeCode = :aCutTypeCode ");
        	stringQuery.append("        and dctwpc.customerClassType.customerClass.customerClassCode = :customerClassCode ");
        	stringQuery.append("        and dctwpc.status = :anActiveStatus ");
        	Query query = session.createQuery(stringQuery.toString());
        	//customerClassCode
        	query.setString("customerClassCode", customerClassCode);
        	query.setString("aCountryCode", countryCode);
        	query.setString("aCutTypeCode", customerTypeCode);
        	query.setString("anActiveStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	
        	List<Dealer> result = query.list();
        	return result;
        } catch (Throwable ex){
			log.error("== Error getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersWhoAttendCustomerTypeWoutCoverage(
			String countryCode, Long customerClassTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append(" select dctwpc.dealer ");
        	stringQuery.append("   from " + DealerCustomerTypesWoutPc.class.getName() + " dctwpc ");
        	stringQuery.append("  where dctwpc.country.countryCode = :aCountryCode ");
        	stringQuery.append("        and dctwpc.customerClassType.id = :customerClassTypeId ");
        	stringQuery.append("        and dctwpc.status = :anActiveStatus ");
        	Query query = session.createQuery(stringQuery.toString());
        	//customerClassCode
        	query.setLong("customerClassTypeId", customerClassTypeId);
        	query.setString("aCountryCode", countryCode);
        	query.setString("anActiveStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	
        	List<Dealer> result = query.list();
        	return result;
        } catch (Throwable ex){
			log.error("== Error getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCustomerTypesWoutPc> getDealerCustomerTypesWoutPcByDealerIdCountryIdAndCustomerTypeCodeId(
			Long dealerId,Long countryId, Long customerClassTypeId,String activeStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerCustomerTypesWoutPcByDealerIdCountryIdAndCustomerTypeCodeId/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();
        String strWhereOrAnd = " WHERE ";
        boolean dealerIdSpecified=false,
                countryIdSpecified=false, 
                customerTypeCodeIdSpecified=false,
                activeStatusSpecified=false;
        
        try {
        	StringBuilder stringQuery = new StringBuilder();
        	stringQuery.append("select dctwpc from ");
        	stringQuery.append(DealerCustomerTypesWoutPc.class.getName()).append(" dctwpc ");
        	
        	if(dealerId!=null && dealerId.longValue()>0){
        		stringQuery.append(strWhereOrAnd).append(" dctwpc.dealer.id = :aDealerId ");
            	strWhereOrAnd=" AND ";
            	dealerIdSpecified=true;
        	}
            
            if(countryId!=null && countryId.longValue()>0){
            	stringQuery.append(strWhereOrAnd).append(" dctwpc.country.id = :aCountryId ");
            	strWhereOrAnd=" AND ";
            	countryIdSpecified=true;
            }
            
            if(customerClassTypeId!=null && customerClassTypeId.longValue()>0){
            	stringQuery.append(strWhereOrAnd).append(" dctwpc.customerClassType.id = :aCustomerTypeCodeId ");
            	strWhereOrAnd=" AND ";
            	customerTypeCodeIdSpecified=true;
            }
            
            if(activeStatus!=null && activeStatus.trim().length()>0){
            	stringQuery.append(strWhereOrAnd).append(" dctwpc.status = :anActiveStatus ");
            	strWhereOrAnd=" AND ";
            	activeStatusSpecified=true;
            }
            
            Query query = session.createQuery(stringQuery.toString());
        	
            if(countryIdSpecified)
            	query.setLong("aCountryId", countryId);
            
            if(dealerIdSpecified)
            	query.setLong("aDealerId", dealerId);
        		
            if(customerTypeCodeIdSpecified)
            	query.setLong("aCustomerTypeCodeId", customerClassTypeId);
            
            if(activeStatusSpecified)	
            	query.setString("anActiveStatus", activeStatus);
        	
        	List<DealerCustomerTypesWoutPc> result = query.list();
        	return result;
        } catch (Throwable ex){
			log.error("== Error getDealerCustomerTypesWoutPcByDealerIdCountryIdAndCustomerTypeCodeId/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerCustomerTypesWoutPcByDealerIdCountryIdAndCustomerTypeCodeId/DealerCustomerTypesWoutPcDAO ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCustomerTypesWoutPc> getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(Long dealerId, Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId/DealerCustomerTypesWoutPcDAO ==");
        Session session = super.getSession();

        try {
        	
        	String mainData = new StringBuffer("from ")
        		.append(DealerCustomerTypesWoutPc.class.getName())
        		.append(" dct where dct.dealer.id = :dealerId").toString();
        		
        	String complementData = new StringBuffer(" select new ").append(DealerCustomerTypesWoutPc.class.getName())
        		.append("(cct, ").append(countryId).append("L) from ")
        		.append(CustomerClassType.class.getName()).append(" cct where cct.id not in ")
        		.append("(select distinct dct1.customerClassType.id from ").append(DealerCustomerTypesWoutPc.class.getName())
        		.append(" dct1 where dct1.dealer.id = :dealerId)").toString();
        	
        	Query query = session.createQuery(mainData);
        	query.setLong("dealerId", dealerId);
        	List<DealerCustomerTypesWoutPc> mainResult = query.list();
        	
        	query = session.createQuery(complementData);
        	query.setLong("dealerId", dealerId);
        	List<DealerCustomerTypesWoutPc> complementResult = query.list();
        	
        	mainResult.addAll(complementResult);
        	
        	return mainResult;
        } catch (Throwable ex){
			log.error("== Error getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId/DealerCustomerTypesWoutPcDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId/DealerCustomerTypesWoutPcDAO ==");
        }
	}
	
	private void saveAuditEnvers(DealerCustomerTypesWoutPc obj){
    	if(obj!=null){
    		if(obj.getDealer()!=null){
    			obj.setDealerId(obj.getDealer().getId());
    		}
    		if(obj.getUser()!=null){
    			obj.setUserId(obj.getUser().getId());
    		}
    		if(obj.getCustomerClassType()!=null && obj.getCustomerClassType().getCustomerType()!=null){
    			obj.setCustomerCodeTypeId(obj.getCustomerClassType().getCustomerType().getId());
    		}
    		if(obj.getCountry()!=null){
    			obj.setCountryId(obj.getCountry().getId());
    		}

    	}
    }

}
