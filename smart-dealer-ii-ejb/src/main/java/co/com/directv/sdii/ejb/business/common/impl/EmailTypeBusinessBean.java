package co.com.directv.sdii.ejb.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.MailMessage;
import co.com.directv.sdii.common.util.MailSenderLocal;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.EmailType;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.EmailTypeVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD EmailType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.EmailTypeDAOLocal
 * @see co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal
 */
@Stateless(name="EmailTypeBusinessBeanLocal",mappedName="ejb/EmailTypeBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmailTypeBusinessBean extends BusinessBase implements EmailTypeBusinessBeanLocal {

    @EJB(name="EmailTypeDAOLocal", beanInterface=EmailTypeDAOLocal.class)
    private EmailTypeDAOLocal daoEmailType;
    
    @EJB(name="MailSenderLocal", beanInterface=MailSenderLocal.class)
	private MailSenderLocal mailSenderLocal;
    
    @EJB(name = "UserDAOLocal", beanInterface = UserDAOLocal.class)
	private UserDAOLocal daoUser;
    
    @EJB(name = "ElementDAOLocal", beanInterface = ElementDAOLocal.class)
	private ElementDAOLocal daoElement;
    
    @EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
    
    @EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmailTypeBusinessBean.class);
    
    //Variables para crear el mail dependiendo de la novedad
    private final static String OBSERVACIONES = ApplicationTextEnum.OBSERVATIONS.getApplicationTextValue()+": ";
    private final static String USUARIO = ApplicationTextEnum.USER.getApplicationTextValue()+": ";
    private final static String ID_DOC_NOVEDAD = ApplicationTextEnum.DOCUMENT_IDENTIFICATION_NOVELTY.getApplicationTextValue()+": ";
    /** 
	 * Excepcion lanzada cuando se ejecuta un thread 
	 */
    @SuppressWarnings("unused")
	private static Throwable exception;

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#getAllEmailTypes()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EmailTypeVO> getAllEmailTypes() throws BusinessException {
        log.debug("== Inicia getAllEmailTypes/EmailTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoEmailType.getAllEmailTypes(), EmailTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllEmailTypes/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmailTypes/EmailTypeBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#getEmailTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EmailTypeVO getEmailTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getEmailTypeByID/EmailTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            EmailType objPojo = daoEmailType.getEmailTypeByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(EmailTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmailTypeByID/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmailTypeByID/EmailTypeBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#createEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createEmailType(EmailTypeVO obj) throws BusinessException {
        log.debug("== Inicia createEmailType/EmailTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	EmailType objPojo = daoEmailType.getEmailTypeByCode(obj.getEmailTypeCode());
        	if(objPojo != null){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(EmailType.class, obj);
            // El Cuerpo del mensaje debe guardarese en el campo EmailHTMLText, Solucion
            // BUG 62840
            objPojo.setEmailHTMLText(obj.getEmailTypeText());
            daoEmailType.createEmailType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createEmailType/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmailType/EmailTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#updateEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateEmailType(EmailTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateEmailType/EmailTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	EmailType objPojo = daoEmailType.getEmailTypeByCode(obj.getEmailTypeCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
            objPojo =  UtilsBusiness.copyObject(EmailType.class, obj);
            // El Cuerpo del mensaje debe guardarese en el campo EmailHTMLText, Solucion
            // BUG 63445
            objPojo.setEmailHTMLText(obj.getEmailTypeText());
            daoEmailType.updateEmailType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateEmailType/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmailType/EmailTypeBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#deleteEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteEmailType(EmailTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteEmailType/EmailTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            EmailType objPojo =  UtilsBusiness.copyObject(EmailType.class, obj);
            daoEmailType.deleteEmailType(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteEmailType/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmailType/EmailTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#getEmailTypeByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public EmailTypeVO getEmailTypeByCode(String code) throws BusinessException {
		log.debug("== Inicia getEmailTypeByCode/EmailTypeBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            EmailType objPojo = daoEmailType.getEmailTypeByCode(code);
            if (objPojo == null) {
            	return null;
            }
            return UtilsBusiness.copyObject(EmailTypeVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getEmailTypeByCode/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmailTypeByCode/EmailTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#sendEmailByEmailTypeCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void sendEmailByEmailTypeCode(SendEmailDTO sendEmailDTO, Long countryId)
			throws BusinessException {
		log.debug("== Inicia sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(sendEmailDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsDocument(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsObservation(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsSourceUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getRecipient(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if(sendEmailDTO.getRecipient().size() == 0){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
		try {
			List<MailMessage> mailMessages = new ArrayList<MailMessage>();
            EmailTypeVO emailTypeVO = getEmailTypeByCode(sendEmailDTO.getNewsType());
            for(String string : sendEmailDTO.getRecipient()){
            	MailMessage mailMessage = new MailMessage(string,emailTypeVO.getEmailTypeSubject(),buildMailText(sendEmailDTO,emailTypeVO.getEmailTypeText()));
            	mailMessages.add(mailMessage);
            }
            mailSenderLocal.send(mailMessages, countryId, false);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        }
	}
	
	private String buildMailText(SendEmailDTO sendEmailDTO , String emailTypeText){
		StringBuilder sb = new StringBuilder();
		sb.append(emailTypeText);
		sb.append('\n');
		sb.append(OBSERVACIONES);
		sb.append(sendEmailDTO.getNewsObservation());
		if(sendEmailDTO.getNewsSourceUser()!=null && sendEmailDTO.getNewsSourceUser().length()>0){
			sb.append('\n');
			sb.append(USUARIO);
			sb.append(sendEmailDTO.getNewsSourceUser());
		}
		sb.append('\n');
		sb.append(ID_DOC_NOVEDAD);
		sb.append(sendEmailDTO.getNewsDocument());		
		return sb.toString();
	}
	
	private String buildMailHTMLText(SendEmailDTO sendEmailDTO , EmailTypeVO emailTypeVO){
		String htmlFormat = emailTypeVO.getEmailHTMLText();
		htmlFormat = htmlFormat.replaceAll(ApplicationTextEnum.TYPE_NOVELTY.getApplicationTextValue(), emailTypeVO.getEmailTypeText());
		htmlFormat = htmlFormat.replaceAll(ApplicationTextEnum.NOVELTY_OBSERVATIONS.getApplicationTextValue(), sendEmailDTO.getNewsObservation());
		htmlFormat = htmlFormat.replaceAll(ApplicationTextEnum.NOVELTY_IDENTIFICATION_DOCUMENT.getApplicationTextValue(), sendEmailDTO.getNewsDocument());
		htmlFormat = htmlFormat.replaceAll(ApplicationTextEnum.USER_ORIGINATES_NOVELTY.getApplicationTextValue(), sendEmailDTO.getNewsSourceUser());
		return htmlFormat;
	}
	
	/**
	 * Metodo: Permite enviar un correo electronico de acuerdo al código enviado de manera asincrona
	 * @param sendEmailDTO Informacion necesaria para enviar correo de notificacion de novedad
	 * @param recipient Direccion a donde se va a enviar el correo electronico
	 * @throws BusinessException En caso que no se encuentre el tipo de email o que no se pueda enviar el mail
	 * @author jnova
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmailByEmailTypeCodeAsic(SendEmailDTO sendEmailDTO, final Long countryId)
			throws BusinessException {
		log.debug("== Inicia sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(sendEmailDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsDocument(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsObservation(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsSourceUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getRecipient(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if(sendEmailDTO.getRecipient().size() == 0){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
		try {
			final List<MailMessage> mailMessages = new ArrayList<MailMessage>();
            EmailTypeVO emailTypeVO = getEmailTypeByCode(sendEmailDTO.getNewsType());
            StringBuilder subject = new StringBuilder();
            //subject.append( CodesBusinessEntityEnum.DEPLOYMENT.getCodeEntity() );
            subject.append( UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_DEPLOYMENT.getCodeEntity(), countryId, systemParameterDAO) );
            subject.append("-");
            subject.append(emailTypeVO.getEmailTypeSubject());
            
            for(String string : sendEmailDTO.getRecipient()){
            	//MailMessage mailMessage = new MailMessage(string,emailTypeVO.getEmailTypeSubject(),buildMailText(sendEmailDTO,emailTypeVO.getEmailTypeText()));
            	MailMessage mailMessage = new MailMessage(string,subject.toString(),buildMailText(sendEmailDTO,emailTypeVO.getEmailTypeText()));
            	
            	if(sendEmailDTO.getAttachmentFiles()!=null){
            		mailMessage.setAttachment(sendEmailDTO.getAttachmentFiles());
            	}
            	
            	mailMessages.add(mailMessage);
            }
            
         // Envio asincrono de mails
	    	Runnable runnable = new Runnable() {
				public void run() {
					try {
						mailSenderLocal.send(mailMessages, countryId, false);
					} catch (EmailMessageException email) {
						exception = email;
					}
				}
			};
			new Thread(runnable).start();	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        }
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmailByEmailTypeCodeSinc(SendEmailDTO sendEmailDTO, final Long countryId)
			throws BusinessException {
		log.debug("== Inicia sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(sendEmailDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsDocument(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsObservation(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsSourceUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getRecipient(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if(sendEmailDTO.getRecipient().size() == 0){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
		try {
			final List<MailMessage> mailMessages = new ArrayList<MailMessage>();
            EmailTypeVO emailTypeVO = getEmailTypeByCode(sendEmailDTO.getNewsType());
            StringBuilder subject = new StringBuilder();
            //subject.append( CodesBusinessEntityEnum.DEPLOYMENT.getCodeEntity() );
            subject.append( UtilsBusiness.getSystemParameter(CodesBusinessEntityEnum.SYSTEM_PARAM_DEPLOYMENT.getCodeEntity(), countryId, systemParameterDAO) );
            subject.append("-");
            subject.append(emailTypeVO.getEmailTypeSubject());
            
            for(String string : sendEmailDTO.getRecipient()){
            	//MailMessage mailMessage = new MailMessage(string,emailTypeVO.getEmailTypeSubject(),buildMailText(sendEmailDTO,emailTypeVO.getEmailTypeText()));
            	MailMessage mailMessage = new MailMessage(string,subject.toString(),buildMailText(sendEmailDTO,emailTypeVO.getEmailTypeText()));
            	
            	if(sendEmailDTO.getAttachmentFiles()!=null){
            		mailMessage.setAttachment(sendEmailDTO.getAttachmentFiles());
            	}
            	
            	mailMessages.add(mailMessage);
            }
            mailSenderLocal.send(mailMessages, countryId, false);	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        }
	}

	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#sendEmailByEmailTypeCodeAsicFormated(co.com.directv.sdii.model.dto.SendEmailDTO, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void sendEmailByEmailTypeCodeAsicFormated(SendEmailDTO sendEmailDTO, final Long countryId) throws BusinessException {
		log.debug("== Inicia sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(sendEmailDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsDocument(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsObservation(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsSourceUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getRecipient(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if(sendEmailDTO.getRecipient().size() == 0){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
		try {
			final List<MailMessage> mailMessages = new ArrayList<MailMessage>();
            EmailTypeVO emailTypeVO = getEmailTypeByCode(sendEmailDTO.getNewsType());
            for(String string : sendEmailDTO.getRecipient()){
            	MailMessage mailMessage = new MailMessage(string,emailTypeVO.getEmailTypeSubject(),buildMailText(sendEmailDTO,""));
            	mailMessages.add(mailMessage);
            }
            
         // Envio asincrono de mails
	    	Runnable runnable = new Runnable() {
				public void run() {
					try {
						mailSenderLocal.send(mailMessages, countryId, false);
					} catch (EmailMessageException email) {
						exception = email;
					}
				}
			};
			new Thread(runnable).start();	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailByEmailTypeCode/EmailTypeBusinessBean ==");
        }
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void sendEmailByNewness(SendEmailDTO sendEmailDTO, final Long countryId)
			throws BusinessException {
		log.debug("== Inicia sendEmailByNewness/EmailTypeBusinessBean ==");
		UtilsBusiness.assertNotNull(sendEmailDTO, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsDocument(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsObservation(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsSourceUser(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getNewsType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(sendEmailDTO.getRecipient(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        if(sendEmailDTO.getRecipient().size() == 0){
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
		try {
			final List<MailMessage> mailMessages = new ArrayList<MailMessage>();
            EmailTypeVO emailTypeVO = getEmailTypeByCode(sendEmailDTO.getNewsType());
            for(String string : sendEmailDTO.getRecipient()){
            	MailMessage mailMessage = new MailMessage(string,emailTypeVO.getEmailTypeSubject(),buildMailHTMLText(sendEmailDTO,emailTypeVO));
            	mailMessages.add(mailMessage);
            }
            
         // Envio asincrono de mails
	    	Runnable runnable = new Runnable() {
				public void run() {
					try {
						mailSenderLocal.send(mailMessages, countryId, true);
					} catch (EmailMessageException email) {
						exception = email;
					}
				}
			};
			new Thread(runnable).start();	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendEmailByNewness/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendEmailByNewness/EmailTypeBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#sendMailByEmailType(java.utul.List<ElementVO>, java.util.List, Dealer, EmailTypesEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void sendMailByEmailType(List<ElementVO> allElements, List<User> users, Dealer dealer, EmailTypesEnum ete ) throws BusinessException {
	      
		String documento = "";
		for (ElementVO elementVO: allElements){
			documento += elementVO.getId();
		}
		if (documento == null || documento.length() == 0){
			documento = "N/A";
		}
		
		for(User usuario : users){
			SendEmailDTO email = new SendEmailDTO();
			email.setNewsType(ete.getEmailTypecode());
			
			email.setNewsDocument(documento);
			email.setNewsObservation(ete.getDescription());
			email.setNewsSourceUser(usuario.getName());
			List<String> recipients = new ArrayList<String>();
			recipients.add(usuario.getEmail());
			email.setRecipient(recipients);
			this.sendEmailByNewness(email, dealer.getPostalCode().getCity().getState().getCountry().getId());
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#sendMailByEmailType(java.utul.List<ElementVO>, java.util.List, Dealer, EmailTypesEnum)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void sendMailByEmailType(List<ElementVO> allElements, 
			                        List<User> users, 
			                        Dealer dealer, 
			                        EmailTypesEnum ete,
			                        UserVO userChange,
			                        WarehouseVO warehouseSource) throws BusinessException {
	    
		log.debug("== inicio sendMailByEmailType/EmailTypeBusinessBean ==");
		
		try {
		
				String userName="";
				log.debug("== userChange ["+userChange+"]");
				
				String documento = "<br><table BORDER='1'> <tr><th>"+ApplicationTextEnum.ELEMENT.getApplicationTextValue()+"</th><th>"+ApplicationTextEnum.QUANTITY.getApplicationTextValue()+"</th><th>"+ApplicationTextEnum.SERIAL.getApplicationTextValue()+"</th></tr>";
				if(userChange != null){
					userName=userChange.getName();
					if (userName == null || userName.length() == 0){
						  if (userChange.getId() != null && userChange.getId().longValue() != 0){
							User user = daoUser.getUserById(userChange.getId() );
							userName = user.getName();
						  }else {
							  userName = ApplicationTextEnum.EMPTY.getApplicationTextValue();
						  }
						}
				}else{
					userName=ApplicationTextEnum.EMPTY.getApplicationTextValue();
				}
				
				for (ElementVO elementVO: allElements){
					ElementVO element= UtilsBusiness.copyObject(ElementVO.class,daoElement.getElementByID(elementVO.getId()));
					
					
					String serial = "";
					String vinculado = null;
					if (  element.getIsSerialized().equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) ){
						Serialized serialized = daoSerialized.getSerializedByID( element.getId() );
						serial = serialized.getSerialCode();
						if ( elementVO.getMovedQuantity() == null){
							elementVO.setMovedQuantity(1D);
						}
						//Mira si tiene vinculado para concatenarle la información
						if ( serialized.getSerialized() != null ){
							vinculado = "<tr><td>" 
							+ serialized.getSerialized().getElement().getElementType().getTypeElementCode() 
							+ " - " + serialized.getSerialized().getElement().getElementType().getTypeElementName() 
							+ " </td><td> " 
							+ elementVO.getMovedQuantity() 
							+ "</td><td>"+ serialized.getSerialized().getSerialCode() +"</td></tr>";
						}
					}
					
					documento += "<tr><td>" + element.getElementType().getTypeElementCode() + " - " + element.getElementType().getTypeElementName() + " </td><td> " 
					           + elementVO.getMovedQuantity() 
					           + "</td><td>"+ serial +"</td></tr>";
					//Concatena informaciín de vinculado
					if ( vinculado != null )
						documento += vinculado;
				}
				documento += "</table><br><br>";
				documento += "<b>"+ApplicationTextEnum.DESTINATION.getApplicationTextValue()+": </b>" + warehouseSource.getDealerId().getDepotCode() + " - " 
				           + warehouseSource.getDealerId().getDealerName() + " - " 
				           + warehouseSource.getWarehouseType().getWhTypeName();
				
				for(User usuario : users){
					SendEmailDTO email = new SendEmailDTO();
					email.setNewsType(ete.getEmailTypecode());
					
					email.setNewsDocument(documento);
					email.setNewsObservation(ete.getDescription());
					email.setNewsSourceUser(userName);
					List<String> recipients = new ArrayList<String>();
					recipients.add(usuario.getEmail());
					email.setRecipient(recipients);
					this.sendEmailByNewness(email, dealer.getPostalCode().getCity().getState().getCountry().getId());
				}
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación sendMailByEmailType/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina sendMailByEmailType/EmailTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.EmailTypeBusinessBeanLocal#getActiveEmailTypes()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<EmailTypeVO> getActiveEmailTypes() throws BusinessException {
		log.debug("== Inicia getActiveEmailTypes/EmailTypeBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoEmailType.getActiveEmailTypes(), EmailTypeVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getActiveEmailTypes/EmailTypeBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getActiveEmailTypes/EmailTypeBusinessBean ==");
        }
	}
}
 