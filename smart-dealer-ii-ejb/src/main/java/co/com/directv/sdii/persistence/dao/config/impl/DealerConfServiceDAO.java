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
import co.com.directv.sdii.model.pojo.DealerConfService;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.DealerConfServiceDAOLocal;


/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DealerConfService
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfServicee
 * @see co.com.directv.sdii.persistence.dao.config.DealerConfServiceDAOLocal
 */

@Stateless(name="DealerConfServiceDAOLocal",mappedName="ejb/DealerConfServiceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfServiceDAO extends BaseDao implements DealerConfServiceDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(DealerConfServiceDAO.class);
    
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfService en el sistema
     * @param obj - DealerConfService
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerConfService(DealerConfService obj) throws DAOServiceException, DAOSQLException  
    {

        log.debug("== Inicio createDealerConfService/DealerConfServiceDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando el createDealerConfService ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerConfService/DealerConfServiceDAO ==");
        }
    }

    public int deleteDealerConfServiceByDealerConfId(Long dealerConfId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia deleteDealerConfServiceByDealerConfId/DealerConfigurationDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from DealerConfService ");
        	stringQuery.append("where dealerConfigurationId = :dealerConfId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            int result = query.executeUpdate();
            return result;

        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfService ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfServiceByDealerConfId/DealerConfigurationDAO ==");
        }

    }

	@Override
	public int deleteDealerConfServiceByDealerConfIdAndServiceCatId(Long dealerConfId, Long serviceCategoryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia deleteDealerConfServiceByDealerConfIdAndServiceCatId/DealerConfServiceDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append(" DELETE FROM DealerConfService dcs ");
        	stringQuery.append(" WHERE dcs.dealerConfigurationId = :dealerConfId ");
        	stringQuery.append(" and dcs.serviceCategoryId = :serviceCategoryId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            query.setLong("serviceCategoryId", serviceCategoryId);
            int result = query.executeUpdate();
            return result;

        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfService ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfServiceByDealerConfIdAndServiceCatId/DealerConfServiceDAO ==");
        }
	}
}