package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.Date;

import javax.ejb.EJB;
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
import co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.AuditExternalSystemScheduleDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de AuditExternalSystemSchedule
 * 
 * Fecha de Creación: 15/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule
 * @see co.com.directv.sdii.persistence.dao.config.AuditExternalSystemScheduleDAOLocal
 */
@Stateless(name="AuditExternalSystemScheduleDAOLocal",mappedName="ejb/AuditExternalSystemScheduleDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AuditExternalSystemScheduleDAO extends BaseDao implements AuditExternalSystemScheduleDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AuditExternalSystemScheduleDAO.class);
    
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

    /**
     * Crea una WorkOrderCSR en el sistema
     * @param obj - WorkOrderCSR
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAuditExternalSystemSchedule(AuditExternalSystemSchedule obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createAuditExternalSystemSchedule/AuditExternalSystemScheduleDAO ==");
        Session session = ConnectionFactory.getSession();
        obj.setActualDate(getDateLastChangeOfUser(obj.getCountry().getId()));
		
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }  catch (Throwable ex) {
            log.error("== Error creando AuditExternalSystemSchedule ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAuditExternalSystemSchedule/AuditExternalSystemScheduleDAO ==");
        }
    }
    
    private Date getDateLastChangeOfUser(Long countryId){
    	Long userId=UtilsBusiness.getUserIdAdmin(userDao,systemParameterDAO,countryId);
    	return UtilsBusiness.getCurrentTimeZoneDateByUserId( userId, userDao);
    }
    
	@Override
	public AuditExternalSystemSchedule getAuditExternalSystemScheduleByID(
			Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAuditExternalSystemScheduleByID/AuditExternalSystemScheduleDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("select auditExternalSystemSchedule ");
            stringQuery.append("from " + AuditExternalSystemSchedule.class.getName() + " auditExternalSystemSchedule ");
            stringQuery.append("where auditExternalSystemSchedule.id = :aId ");
            
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aId", id);
            return (AuditExternalSystemSchedule) query.uniqueResult();

        } catch (Throwable ex) {
            log.error("== Error consultando AuditExternalSystemSchedule por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAuditExternalSystemScheduleByID/AuditExternalSystemScheduleDAO ==");
        }
	}
    
    
    
}
