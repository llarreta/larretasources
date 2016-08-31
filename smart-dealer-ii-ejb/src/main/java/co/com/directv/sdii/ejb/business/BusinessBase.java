package co.com.directv.sdii.ejb.business;

import javax.ejb.EJBException;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.business.AbstractPaginationBase;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.KeyUtil;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ReferenceProcedureException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.intergrupo.ldap.service.exception.SpringLdapComunicationException;
import co.com.intergrupo.ldap.service.exception.SpringLdapException;
import co.com.intergrupo.ldap.service.exception.SpringLdapSecurityException;

/**
 * Clase base que determina las operaciones comunes en la capa de negocio
 * 
 * Fecha de Creación: 24/04/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 */
public abstract class BusinessBase extends AbstractPaginationBase {

	private final static Logger log = UtilsBusiness.getLog4J(BusinessBase.class);
	
	/**
	 * Metodo: convierte la exepción que se obtiene en una administrada por el sistema
	 * @param ex excepción a tratar y convertir en una administrada por el sistema
	 * que el contenedor podrá reconocer para realizar el reverso de la transacción
	 * @return Excepción manejada por el sistema
	 * @author jjimenezh
	 * @author jalopez
	 */
	public BusinessException manageException(Throwable ex){
		
		KeyUtil keyUtil = new KeyUtil();
		BusinessException businessException = new BusinessException();
		
		//Se obtiene el identificador del error para rastrear en el log.
		String txErrorKey = keyUtil.getCompositeKey();
		//Formato del mensaje de error en el log.
		String formatMessage = "== Error en la Capa de Negocio debido a una {0}, Codigo Error {1}, Identificador Error {2}, Mensaje del Error {3} ==";	
		//Parametros del errror.
		Object[] params = null;
		params = new Object[4];	
		
		if (ex instanceof DAOServiceException) {			
			businessException = new BusinessException(txErrorKey, ((DAOServiceException)ex).getMessageCode(), ((DAOServiceException) ex).getMessage(),ex,((DAOServiceException) ex).getParameters());
			params[0] = DAOServiceException.class.getName();	
			params[1] = ((DAOServiceException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((DAOServiceException) ex).getMessage();
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof DAOSQLException) {			
			businessException = new BusinessException(txErrorKey, ((DAOSQLException)ex).getMessageCode(), ((DAOSQLException) ex).getMessage(),ex,((DAOSQLException) ex).getParameters());
			params[0] = DAOSQLException.class.getName();
			params[1] = ((DAOSQLException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((DAOSQLException) ex).getMessage();			
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof PropertiesException) {			
			businessException = new BusinessException(txErrorKey, ((PropertiesException)ex).getMessageCode(), ((PropertiesException) ex).getMessage(),ex,((PropertiesException) ex).getParameters());
			params[0] = PropertiesException.class.getName();	
			params[1] = ((PropertiesException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((PropertiesException) ex).getMessage();			
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof EmailMessageException) {			
			businessException = new BusinessException(txErrorKey, ((EmailMessageException)ex).getMessageCode(), ((EmailMessageException) ex).getMessage(),ex,((EmailMessageException) ex).getParameters());
			params[0] = EmailMessageException.class.getName();	
			params[1] = ((EmailMessageException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((EmailMessageException) ex).getMessage();
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof PDFException) {			
			businessException = new BusinessException(txErrorKey, ((PDFException)ex).getMessageCode(), ((PDFException) ex).getMessage(),ex,((PDFException) ex).getParameters());
			params[0] = PDFException.class.getName();	
			params[1] = ((PDFException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((PDFException) ex).getMessage();
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof WebServiceException) {			
			businessException = new BusinessException (txErrorKey, ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getCode(), ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getMessage( )+ "["+ex.getMessage() + "]");
			params[0] = WebServiceException.class.getName();	
			params[1] = ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getCode();
			params[2] = txErrorKey;
			params[3] = ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getMessage()+"["+ex.getMessage()+"]";
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof SOAPFaultException) {			
			businessException = new BusinessException (txErrorKey, ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getCode(), ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getMessage() + "[" +((SOAPFaultException)ex).getFault().getFaultString() + "]");
			params[0] = PDFException.class.getName();	
			params[1] = ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getCode();
			params[2] = txErrorKey;
			params[3] = ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getMessage() + "[" +((SOAPFaultException)ex).getFault().getFaultString() + "]";
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof  ServiceLocatorException) {			
			businessException = new BusinessException(txErrorKey, ((ServiceLocatorException)ex).getMessageCode(), ((ServiceLocatorException) ex).getMessage(),ex,((ServiceLocatorException)ex).getParameters());
			params[0] = ServiceLocatorException.class.getName();	
			params[1] = ((ServiceLocatorException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((ServiceLocatorException) ex).getMessage();
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof  SpringLdapException) {			
			String code = this.getCodeValidate(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode());
			String message = this.getMessageValidate(ex.getMessage());
			businessException = new BusinessException(txErrorKey, code, message, ex);
			params[0] = BusinessException.class.getName();	
			params[1] = code;
			params[2] = txErrorKey;
			params[3] = message;
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof  SpringLdapSecurityException) {			
			String code = this.getCodeValidate(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode());
			String message = this.getMessageValidate(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessage(((SpringLdapSecurityException) ex).getParametersVector()));
			businessException = new BusinessException(txErrorKey, code, message, ex, UtilsBusiness.getParametersWS( ((SpringLdapSecurityException) ex).getParametersVector() ) );
			params[0] = BusinessException.class.getName();	
			params[1] = code;
			params[2] = txErrorKey;
			params[3] = message;
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		}  else if (ex instanceof  SpringLdapComunicationException) {			
			String code = this.getCodeValidate(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode());
			String message = this.getMessageValidate(ex.getMessage());
			businessException = new BusinessException(txErrorKey, code, message, ex);
			params[0] = BusinessException.class.getName();	
			params[1] = code;
			params[2] = txErrorKey;
			params[3] = message;
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof  BusinessException) {			
			businessException = new BusinessException(txErrorKey, ((BusinessException) ex).getMessageCode(), ex.getMessage() ,ex,((BusinessException)ex).getParameters());
			params[0] = BusinessException.class.getName();	
			params[1] = ((BusinessException)ex).getMessageCode();
			params[2] = txErrorKey;
			params[3] = ((BusinessException) ex).getMessage();
			log.error( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof  EJBException) {	
			String code = this.getCodeValidate(ErrorBusinessMessages.UNKNOW_ERROR.getCode());
			String message = this.getMessageValidate(ErrorBusinessMessages.UNKNOW_ERROR.getMessage());
			businessException = new BusinessException(txErrorKey, code, message + "[" + ex.getMessage() + "]", ex);
			params[0] = EJBException.class.getName();	
			params[1] = code;
			params[2] = txErrorKey;
			params[3] = message + "[" + ex.getMessage() + "]";
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else if (ex instanceof ReferenceProcedureException) {	
			String code = this.getCodeValidate(ErrorBusinessMessages.UNKNOW_ERROR.getCode()); 
			String message = this.getMessageValidate(ex.getMessage()); //ErrorBusinessMessages.UNKNOW_ERROR.getMessage()
			businessException = new BusinessException(txErrorKey, code, message , ex);
			params[0] = EJBException.class.getName();	
			params[1] = code;
			params[2] = txErrorKey;
			params[3] = message + "[" + ex.getMessage() + "]";
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		} else {			
			businessException = new BusinessException(txErrorKey, ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ErrorBusinessMessages.UNKNOW_ERROR.getMessage() + "["+ex.getMessage() + "]", ex);
			params[0] = Exception.class.getName();	
			params[1] = ErrorBusinessMessages.UNKNOW_ERROR.getCode();
			params[2] = txErrorKey;
			params[3] = ErrorBusinessMessages.UNKNOW_ERROR.getMessage() + "[" + ex.getMessage() + "]";
			log.fatal( UtilsBusiness.getMessageResourceString(formatMessage, params), businessException );
		}
		
		return businessException;
	}	
	
	/**
	 * 
	 * Metodo: Retorna un codigo default de error en el proceso
	 * de negocio en caso de que no pueda ser recuperado 
	 * de la Base de Datos por un TimeOut del contenedor.
	 * @param pCode
	 * @return String
	 * @author
	 */
	private String getCodeValidate(String pCode) {
		String code = pCode;
		if ( code == null ) {
			code = "BL49";
		}
		return code;
	}
	
	/**
	 * 
	 * Metodo: Retorna un mensaje default de error en el proceso
	 * de negocio en caso de que no pueda ser recuperado 
	 * de la Base de Datos por un TimeOut del contenedor.
	 * @param pMessage
	 * @return String
	 * @author 
	 */
	private String getMessageValidate(String pMessage) {
		String message = pMessage;
		if (message == null) {
			message = "Error en el proceso de negocio, comuniquese con el administrador";
		}
		return message;
	}
}
