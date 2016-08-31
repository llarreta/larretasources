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
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.DealerConfCustomerType;
import co.com.directv.sdii.model.pojo.DealerConfService;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad DealerConfiguration.
 * 
 * Fecha de Creación: M1 18, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfiguration
 * @see co.com.directv.sdii.model.hbm.DealerConfiguration.hbm.xml
 */
@Stateless(name="DealerConfigurationDAOLocal",mappedName="ejb/DealerConfigurationDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfigurationDAO extends BaseDao implements DealerConfigurationDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DealerConfigurationDAO.class);
    
    
    /**
	 * Metodo:  persiste la información de un DealerConfiguration
	 * @param obj objeto que encapsula la información de un DealerConfiguration
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerConfiguration(DealerConfiguration obj) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicio createDealerConfiguration/DealerConfigurationDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el DealerConfiguration ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerConfiguration/DealerConfigurationDAO ==");
        }

	}
	
	/**
	 * Metodo:  obtiene los DealerConfiguration existentes segun el CustomerClassType
	 * @param customerClassType 
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author ialessan
	 */		
	public List<DealerConfiguration> getDealerConfigurationByCustomerCategoryId(Long dealerId , Long customerCategoryId)throws DAOServiceException, DAOSQLException {
	
		
		log.debug("== Inicia getDealerConfigurationByCustomerClassType/DealerConfigurationDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
                
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" from "+DealerConfiguration.class.getName()+" as dc ");
			stringQuery.append(" where dc.dealerId = :dealerId");
			stringQuery.append(" and   dc.customerCategoryId = :categoryId");
						
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("dealerId", dealerId);
			query.setLong("categoryId", customerCategoryId);		
			            
            return (List<DealerConfiguration>)query.list();
            
        }catch (Throwable ex) {
            log.error("== Error getDealerConfigurationByCustomerCategoryId ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getDealerConfigurationByCustomerClassType/DealerConfigurationDAO ==");
        }
	}
	
	
	/**
	 * Metodo: obtiene los DealerConfiguration existentes segun el id
	 * @param  dealerId 
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author ialessan
	 */	
	public List<DealerConfiguration> getDealerConfigurationById(Long dealerId)throws DAOServiceException, DAOSQLException{
		
		log.debug("== Inicia getDealerConfigurationById/DealerConfigurationDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
                
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" from "+DealerConfiguration.class.getName()+" as dc ");
			stringQuery.append(" where dc.dealerId = :dealerId");
			
						
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("dealerId", dealerId);
					
			            
            return (List<DealerConfiguration>)query.list();
            
        }catch (Throwable ex) {
            log.error("== Error getDealerConfigurationById ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getDealerConfigurationById/DealerConfigurationDAO ==");
        }
	}	
	
	
	public DealerConfiguration getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(Long dealerId,
																					    Long areaId,
																					    Long customerCategoryId
     )throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getDealerConfigurationByDealerIdAreaIdCustomerCategoryId/DealerConfigurationDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
                
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" from "+DealerConfiguration.class.getName()+" as dc ");
			stringQuery.append(" where dc.dealerId = :dealerId ");
			stringQuery.append(" and   dc.businessAreaId = :areaId ");
			stringQuery.append(" and   dc.customerCategoryId = :customerCategoryId ");
			
						
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("dealerId", dealerId);
			query.setLong("areaId", areaId);
			query.setLong("customerCategoryId", customerCategoryId);
					
			            
            return (DealerConfiguration)query.uniqueResult(); 
            
        }catch (Throwable ex) {
            log.error("== Error getDealerConfigurationByDealerIdAreaIdCustomerCategoryId ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getDealerConfigurationByDealerIdAreaIdCustomerCategoryId/DealerConfigurationDAO ==");
        }		
	}
	
    public void deleteDealerConfiguration(DealerConfiguration obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteDealerConfiguration/DealerConfigurationDAO ==");
        Session session = super.getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error eliminando DealerConfiguration ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfiguration/DealerConfigurationDAO ==");
        }

    }

	@Override
	public DealerConfiguration getDealerConfigurationBy(Long   dealerCode, 
														String depotCode, 
														String customerCategoryCode,
														String businessAreaCode
    ) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getDealerConfigurationBy/DealerConfigurationDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
                
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" from "+DealerConfiguration.class.getName()+" as dc ");
			stringQuery.append(" where dc.dealer.dealerCode = :dealerCode ");
			stringQuery.append(" and   dc.dealer.depotCode = :depotCode ");
			stringQuery.append(" and   dc.customerCategory.code = :customerCategoryCode ");
			stringQuery.append(" and   dc.businessArea.businessAreaCode = :businessAreaCode ");
			
						
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("dealerCode", dealerCode);
			query.setString("depotCode", depotCode);
			query.setString("customerCategoryCode", customerCategoryCode);
			query.setString("businessAreaCode", businessAreaCode);
			            
            return (DealerConfiguration)query.uniqueResult(); 
            
        }catch (Throwable ex) {
            log.error("== Error getDealerConfigurationBy ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getDealerConfigurationBy/DealerConfigurationDAO ==");
        }		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal#getDealerFromDealerByCustomerType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Dealer> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerFromDealerByCustomerType/DealerConfigurationDAO ==");
        Session session = ConnectionFactory.getSession();
        try{
        	
            StringBuffer stringQuery = new StringBuffer();            
			stringQuery.append(" FROM "+ Dealer.class.getName() + " AS d ");
			stringQuery.append(" WHERE d.id IN (SELECT dc.dealerId ");
			stringQuery.append(" 				FROM " + DealerConfiguration.class.getName() +" AS dc ");
			stringQuery.append(" 				, " + DealerConfCustomerType.class.getName() +" AS dcct ");
			stringQuery.append(" 				, " + DealerConfService.class.getName() +" AS dcs ");
			stringQuery.append(" 				, " + DealerConfCoverage.class.getName() +" AS dcc ");
			stringQuery.append("                WHERE dc.id = dcct.dealerConfigurationId ");
			stringQuery.append("				AND dc.id = dcs.dealerConfigurationId ");
			stringQuery.append("				AND dc.id = dcc.dealerConfigurationId ");
			stringQuery.append("				AND dcct.customerClassTypeId = :customerClassTypeId ");
			stringQuery.append("				AND dcs.serviceCategoryId = :serviceCategoryId ");
			stringQuery.append("				AND dcc.postalCode.id = :postalCodeId ");
			stringQuery.append("				AND dc.businessAreaId = :businesAreaId ");
			stringQuery.append("				AND dc.customerCategoryId = :customerCategoryId ) ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("customerCategoryId", customerCategoryId);
			query.setLong("businesAreaId", businesAreaId);
			query.setLong("customerClassTypeId", customerClassTypeId);
			query.setLong("serviceCategoryId", serviceCategoryId);
			query.setLong("postalCodeId", postalCodeId);
			//query.setLong("countryCode", countryCode);
			            
            return (List<Dealer>)query.list();
            
        }catch (Throwable ex) {
            log.error("== Error getDealerFromDealerByCustomerType ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina getDealerFromDealerByCustomerType/DealerConfigurationDAO ==");
        }
	}
}
