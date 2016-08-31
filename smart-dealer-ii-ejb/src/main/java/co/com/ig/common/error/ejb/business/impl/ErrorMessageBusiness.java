/**
 * Creado 7/07/2010 9:28:37
 */
package co.com.ig.common.error.ejb.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import co.com.ig.common.error.ejb.business.ErrorMessageBusinessLocal;
import co.com.ig.common.error.ejb.business.ErrorMessageBusinessRemote;
import co.com.ig.common.error.persistence.dao.ErrorMessageDAOLocal;
import co.com.ig.common.error.pojo.ErrorMessage;
import co.com.ig.common.error.pojo.ErrorReason;
import co.com.ig.common.error.vo.ErrorMessageVO;
import co.com.ig.common.error.vo.ErrorReasonVO;

/**
 * Implementa las operaciones de administración de los mensajes de error
 * 
 * Fecha de Creación: 7/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.BusinessBase
 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal
 */
@Stateless(name="ErrorMessageBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local({ErrorMessageBusinessLocal.class})
@Remote({ErrorMessageBusinessRemote.class})
public class ErrorMessageBusiness implements ErrorMessageBusinessLocal {

	@EJB(name="ErrorMessageDAOLocal", beanInterface=ErrorMessageDAOLocal.class)
	private ErrorMessageDAOLocal errorMessageDao;
	
	private final static Log log = LogFactory.getLog(ErrorMessageBusiness.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal#createErrorMessage(co.com.directv.sdii.error.vo.ErrorMessageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createErrorMessage(ErrorMessageVO errorMessage){
		log.debug("== Inicia createErrorMessage/ErrorMessageBusiness ==");
		try {
			ErrorMessage errorMessagePojo = new ErrorMessage();
			BeanUtils.copyProperties(errorMessagePojo, errorMessage);
			errorMessageDao.createErrorMessage(errorMessagePojo);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion createErrorMessage/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina createErrorMessage/ErrorMessageBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal#deleteErrorMessage(co.com.directv.sdii.error.vo.ErrorMessageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteErrorMessage(ErrorMessageVO errorMessage){
		log.debug("== Inicia deleteErrorMessage/ErrorMessageBusiness ==");
		try {
			ErrorMessage errorMessagePojo = new ErrorMessage();
			BeanUtils.copyProperties(errorMessagePojo, errorMessage);
			errorMessageDao.deleteErrorMessage(errorMessagePojo);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion deleteErrorMessage/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina deleteErrorMessage/ErrorMessageBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal#getAllErrorMessages()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ErrorMessageVO> getAllErrorMessages() {
		log.debug("== Inicia getAllErrorMessages/ErrorMessageBusiness ==");
		try {
			
			List<ErrorMessage> resultPojo = errorMessageDao.getAllErrorMessages();
			List<ErrorMessageVO> result = new ArrayList<ErrorMessageVO>();
			ErrorMessageVO errorMessageVo;
			for (ErrorMessage errorMessage : resultPojo) {
				errorMessageVo = new ErrorMessageVO();
				BeanUtils.copyProperties(errorMessageVo, errorMessage);
				result.add(errorMessageVo);
			}
			return result;
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getAllErrorMessages/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina getAllErrorMessages/ErrorMessageBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal#getErrorMessageByErrorKey(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ErrorMessageVO getErrorMessageByErrorKey(String errorKey){
		log.debug("== Inicia getErrorMessageByErrorKey/ErrorMessageBusiness ==");
		try {
			ErrorMessage errorMessagePojo = errorMessageDao.getErrorMessageByErrorKey(errorKey);
			if(errorMessagePojo == null){
				return null;
			}
			ErrorMessageVO result = new ErrorMessageVO();
			BeanUtils.copyProperties(result, errorMessagePojo);
			return result;
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getErrorMessageByErrorKey/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina getErrorMessageByErrorKey/ErrorMessageBusiness ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal#updateErrorMessage(co.com.directv.sdii.error.vo.ErrorMessageVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateErrorMessage(ErrorMessageVO errorMessage){
		log.debug("== Inicia updateErrorMessage/ErrorMessageBusiness ==");
		try {
			ErrorMessage errorMessagePojo = new ErrorMessage();
			BeanUtils.copyProperties(errorMessagePojo, errorMessage);
			errorMessageDao.updateErrorMessage(errorMessagePojo);
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion updateErrorMessage/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina updateErrorMessage/ErrorMessageBusiness ==");
		}
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		errorMessageDao.setSessionFactory(sessionFactory);
	}
	
	/* (non-Javadoc)
	 * @see co.com.ig.common.error.ejb.business.ErrorMessageBusinessLocal#getErrorMessageReasonByReasonCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ErrorReason getErrorReasonByReasonCode(String reasonCode){
		log.debug("== Inicia getErrorReasonByReasonCode/ErrorMessageBusiness ==");
		try {
			ErrorReason errorReason = errorMessageDao.getErrorReasonByReasonCode(reasonCode);
			if(errorReason == null){
				return null;
			}
			ErrorReasonVO result = new ErrorReasonVO();
			BeanUtils.copyProperties(result, errorReason);
			return result;
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getErrorReasonByReasonCode/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina getErrorReasonByReasonCode/ErrorMessageBusiness ==");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.ig.common.error.ejb.business.ErrorMessageBusinessLocal#getAllErrorReasons()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ErrorReasonVO> getAllErrorReasons() {
		log.debug("== Inicia getAllErrorReasons/ErrorMessageBusiness ==");
		try {
			List<ErrorReason> resultPojo = errorMessageDao.getAllErrorReasons();
			List<ErrorReasonVO> result = new ArrayList<ErrorReasonVO>();
			ErrorReasonVO errorReasonVo;
			for (ErrorReason errorReason : resultPojo) {
				errorReasonVo = new ErrorReasonVO();
				BeanUtils.copyProperties(errorReasonVo, errorReason);
				result.add(errorReasonVo);
			}
			return result;
		} catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getAllErrorReasons/ErrorMessageBusiness", ex);
            throw new IllegalStateException(ex.getMessage(),ex);
        } finally {
			log.debug("== Termina getAllErrorReasons/ErrorMessageBusiness ==");
		}
	}

}
