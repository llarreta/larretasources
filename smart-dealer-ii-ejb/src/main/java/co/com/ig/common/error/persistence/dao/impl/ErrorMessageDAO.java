package co.com.ig.common.error.persistence.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.ig.common.error.persistence.dao.BaseDao;
import co.com.ig.common.error.persistence.dao.ErrorMessageDAOLocal;
import co.com.ig.common.error.pojo.ErrorMessage;
import co.com.ig.common.error.pojo.ErrorReason;

/**
 * Implementa el mecanismo de acceso a la persistencia de los mensajes de error
 * 
 * Fecha de Creación: 7/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal
 * @see co.com.directv.sdii.persistence.dao.BaseDao
 */
@Stateless(name="ErrorMessageDAOLocal",mappedName="ejb/ErrorMessageDAOLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ErrorMessageDAO extends BaseDao implements ErrorMessageDAOLocal {

	private final static Log log = LogFactory.getLog(ErrorMessageDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal#createErrorMessage(co.com.directv.sdii.error.ErrorMessage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createErrorMessage(ErrorMessage errorMessage){
		log.debug("== Inicio createErrorMessage/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            session.save(errorMessage);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el ErrorMessage ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina createErrorMessage/ErrorMessageDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal#deleteErrorMessage(co.com.directv.sdii.error.ErrorMessage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteErrorMessage(ErrorMessage errorMessage){
		log.debug("== Inicio deleteErrorMessage/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from ErrorMessage em where em.id = :aMessageId");
            query.setLong("aMessageId", errorMessage.getId());
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el ErrorMessage ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina deleteErrorMessage/ErrorMessageDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal#getAllErrorMessages()
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ErrorMessage> getAllErrorMessages() {
		log.debug("== Inicio getAllErrorMessages/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("from "+ErrorMessage.class.getName());
            List<ErrorMessage> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error obteniendo todos los ErrorMessages ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina getAllErrorMessages/ErrorMessageDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal#getErrorMessageByErrorKey(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ErrorMessage getErrorMessageByErrorKey(String errorKey){
		log.debug("== Inicio getErrorMessageByErrorKey/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("from ErrorMessage em where em.errorKey = :anErrorKey");
            query.setString("anErrorKey", errorKey);
            ErrorMessage result = (ErrorMessage)query.uniqueResult();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el ErrorMessage por error Key ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina getErrorMessageByErrorKey/ErrorMessageDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.persistence.dao.ErrorMessageDAOLocal#updateErrorMessage(co.com.directv.sdii.error.ErrorMessage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateErrorMessage(ErrorMessage errorMessage){
		log.debug("== Inicio updateErrorMessage/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            session.merge(errorMessage);
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el ErrorMessage ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina updateErrorMessage/ErrorMessageDAO ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.ig.common.error.persistence.dao.ErrorMessageDAOLocal#getErrorMessageReasonByReasonCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ErrorReason getErrorReasonByReasonCode(String reasonCode){
		log.debug("== Inicio getErrorReasonByReasonCode/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("from ErrorReason er where er.reasonCode = :reasonCode");
            query.setString("reasonCode", reasonCode);
            ErrorReason result = (ErrorReason)query.uniqueResult();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error obteniendo el ErrorReason por error reasonCode ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina getErrorReasonByReasonCode/ErrorMessageDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.ig.common.error.persistence.dao.ErrorMessageDAOLocal#getAllErrorReasons()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ErrorReason> getAllErrorReasons() {
		log.debug("== Inicio getAllErrorReasons/ErrorMessageDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("from ErrorReason");
            List<ErrorReason> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error obteniendo todos los ErrorReasons ==");
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
            log.debug("== Termina getAllErrorReasons/ErrorMessageDAO ==");
        }
	}

}
