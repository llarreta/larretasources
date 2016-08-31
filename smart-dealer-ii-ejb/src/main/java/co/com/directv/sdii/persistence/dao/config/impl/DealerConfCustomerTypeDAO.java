package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerConfCustomerType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.DealerConfCustomerTypeDAOLocal;


/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DealerConfCustomerType
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfCustomerType
 * @see co.com.directv.sdii.persistence.dao.config.DealerConfCustomerTypeDAOLocal
 */

@Stateless(name="DealerConfCustomerTypeDAOLocal",mappedName="ejb/DealerConfCustomerTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfCustomerTypeDAO extends BaseDao implements DealerConfCustomerTypeDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(DealerConfCustomerTypeDAO.class);
    
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfCustomerType en el sistema
     * @param obj - DealerConfCustomerType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerConfCustomerType(DealerConfCustomerType obj) throws DAOServiceException, DAOSQLException 
    {

        log.debug("== Inicio createDealerConfCustomerType/DealerConfCustomerTypeDAO ==");
        //Session session = ConnectionFactory.getSession();
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando el createDealerConfCustomerType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerConfCustomerType/DealerConfCustomerTypeDAO ==");
        }
    }
	
    
    public int deleteDealerConfCustomerTypeByDealerConfId(Long dealerConfId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia deleteDealerConfCustomerTypeByDealerConfId/DealerConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from DealerConfCustomerType ");
        	stringQuery.append("where dealerConfigurationId = :dealerConfId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            int result = query.executeUpdate();
            return result;

        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfCustomerType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfCustomerTypeByDealerConfId/DealerConfigurationDAO ==");
        }

    }
    
 	@Override
 	public int deleteDealerConfCustomerTypeByDealerConfIdAndCustClassTypeId(Long dealerConfId, Long customerClassTypeId) throws DAOServiceException, DAOSQLException {
 		log.debug("== Inicia deleteDealerConfCustomerTypeByDealerConfIdAndCustClassTypeId/DealerConfigurationDAO ==");
         Session session = super.getSession();
 
         try {
         	StringBuffer stringQuery =  new StringBuffer();
         	stringQuery.append(" DELETE FROM DealerConfCustomerType dcct ");
         	stringQuery.append(" WHERE dcct.dealerConfigurationId = :dealerConfId ");
         	stringQuery.append(" AND dcct.customerClassTypeId = :custClassTypeId ");
         
         	Query query = session.createQuery(stringQuery.toString());
             query.setLong("dealerConfId", dealerConfId);
             query.setLong("custClassTypeId", customerClassTypeId);
             int result = query.executeUpdate();
             return result;
 
         }catch (Throwable ex) {
             log.debug("== Error eliminando DealerConfCustomerType ==");
             throw super.manageException(ex);
         } finally {
             log.debug("== Termina deleteDealerConfCustomerTypeByDealerConfIdAndCustClassTypeId/DealerConfigurationDAO ==");
         }
 
 		
 	}    
    
}