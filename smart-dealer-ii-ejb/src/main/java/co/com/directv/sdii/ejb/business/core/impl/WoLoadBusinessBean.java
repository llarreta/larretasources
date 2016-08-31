/**
 * 
 */
package co.com.directv.sdii.ejb.business.core.impl;

import java.util.ArrayList;
import java.util.Date;
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
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoLoad;
import co.com.directv.sdii.model.pojo.WoLoadDetail;
import co.com.directv.sdii.model.vo.WoLoadDetailVO;
import co.com.directv.sdii.model.vo.WoLoadVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoLoadDAOLocal;
import co.com.directv.sdii.persistence.dao.core.WoLoadDetailDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Encapsula la lógica de negocio relacionada con la información de cargue de work orders
 * 
 * @author jjimenezh
 *
 */
@Stateless(name="WoLoadBusinessLocal",mappedName="ejb/WoLoadBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoLoadBusinessBean extends BusinessBase implements
		WoLoadBusinessLocal {

	@EJB(name="WoLoadDAOLocal",beanInterface=WoLoadDAOLocal.class)
	private WoLoadDAOLocal woLoadDao;
	
	@EJB(name="WoLoadDetailDAOLocal",beanInterface=WoLoadDetailDAOLocal.class)
	private WoLoadDetailDAOLocal woLoadDetailDao;
	
	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
	@EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDao;
	
	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
	
	private final static Logger log = UtilsBusiness.getLog4J(WoLoadBusinessBean.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal#createWoLoad(co.com.directv.sdii.model.vo.WoLoadVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WoLoadVO createWoLoad(WoLoadVO woLoad) throws BusinessException {
		log.debug("== Inicia createWoLoad/WoLoadBusinessBean ==");
		try {
			if(! BusinessRuleValidationManager.getInstance().isValid(woLoad)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			WoLoad woLoadPojo = UtilsBusiness.copyObject(WoLoad.class, woLoad);
			woLoadDao.createWoLoad(woLoadPojo);
			WoLoadVO result = UtilsBusiness.copyObject(WoLoadVO.class, woLoadPojo);
			return result;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion createWoLoad/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWoLoad/WoLoadBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WoLoadVO createWoLoad(Long woCount, String countryCode) throws BusinessException {
		log.debug("== Inicia createWoLoad/WoLoadBusinessBean ==");
		try {
			Country country = countryDao.getCountriesByCode(countryCode);
			UtilsBusiness.assertNotNull(country, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el país por el código: " + countryCode);
			WoLoad woLoadPojo = new WoLoad(new Date(), woCount, country);
			
			woLoadDao.createWoLoad(woLoadPojo);
			WoLoadVO result = UtilsBusiness.copyObject(WoLoadVO.class, woLoadPojo);
			return result;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion createWoLoad/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWoLoad/WoLoadBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal#createWoLoadDetail(co.com.directv.sdii.model.vo.WoLoadDetailVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WoLoadDetailVO createWoLoadDetail(WoLoadDetailVO woLoadDetail)
			throws BusinessException {
		log.debug("== Inicia createWoLoadDetail/WoLoadBusinessBean ==");
		try {
			if(! BusinessRuleValidationManager.getInstance().isValid(woLoadDetail)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			WoLoadDetail woLoadDetailPojo = UtilsBusiness.copyObject(WoLoadDetail.class, woLoadDetail);
			woLoadDetailDao.createWoLoadDetail(woLoadDetailPojo);
			WoLoadDetailVO result = UtilsBusiness.copyObject(WoLoadDetailVO.class, woLoadDetailPojo);
			return result;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion createWoLoadDetail/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createWoLoadDetail/WoLoadBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.core.WoLoadBusinessLocal#updateWoLoad(co.com.directv.sdii.model.vo.WoLoadVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WoLoadVO updateWoLoad(WoLoadVO woLoad) throws BusinessException {
		log.debug("== Inicia updateWoLoad/WoLoadBusinessBean ==");
		try {
			if(! BusinessRuleValidationManager.getInstance().isValid(woLoad)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			WoLoad woLoadPojo = UtilsBusiness.copyObject(WoLoad.class, woLoad);
			woLoadDao.updateWoLoad(woLoadPojo);
			WoLoadVO result = UtilsBusiness.copyObject(WoLoadVO.class, woLoadPojo);
			return result;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion updateWoLoad/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateWoLoad/WoLoadBusinessBean ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WoLoad getLastWoLoad(String countryCode) throws BusinessException {
		log.debug("== Inicia getLastWoLoad/WoLoadBusinessBean ==");
		try {						
			Country country = countryDao.getCountriesByCode(countryCode);
			if(country == null){
				List<String> params = new ArrayList<String>();
				params.add(countryCode);
				throw new BusinessException(ErrorBusinessMessages.CORE_CR065.getCode(), ErrorBusinessMessages.CORE_CR065.getMessage(), params);
			}
			WoLoad woLoad = woLoadDao.getLastWoLoad(country.getId());
			return woLoad;
		} catch (Throwable ex) {
			log.fatal("== Error al tratar de ejecutar la operacion getLastWoLoad/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getLastWoLoad/WoLoadBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendWoLoadMail(WoLoadVO woLoad, List<WoLoadDetailVO> loadDetail)
			throws BusinessException {
		log.debug("== Inicia sendWoLoadMail/WoLoadBusinessBean ==");
		try {
			
			//Cabecera del correo:
			StringBuffer mail = new StringBuffer(ApplicationTextEnum.REPORT_LOAD_WO.getApplicationTextValue());
			mail.append("\n");
			mail.append("\n");
			mail.append(ApplicationTextEnum.COUNTRY.getApplicationTextValue()+": ");
			mail.append(woLoad.getCountry().getCountryName());
			mail.append("\n");
			mail.append(ApplicationTextEnum.BEGIN_DATE.getApplicationTextValue()+": ");
			mail.append(UtilsBusiness.dateToString(woLoad.getInitDate(),UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
			mail.append("\n");
			mail.append(ApplicationTextEnum.END_DATE.getApplicationTextValue()+": ");
			mail.append(UtilsBusiness.dateToString(woLoad.getEndDate(),UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
			mail.append("\n");
			mail.append(ApplicationTextEnum.WOS_FOUND.getApplicationTextValue()+": ");
			mail.append(woLoad.getWoCount());
			mail.append("\n");
			mail.append(ApplicationTextEnum.WOS_PROCESSED_WARNING.getApplicationTextValue()+": ");
			mail.append(woLoad.getWoWarningCount());
			mail.append("\n");
			mail.append(ApplicationTextEnum.WOS_PROCESSED_ERROR.getApplicationTextValue()+": ");
			mail.append(woLoad.getWoErrorCount());
			mail.append("\n");
			mail.append("\n");
		
			//Cabecera de los resultados:
			mail.append(ApplicationTextEnum.WO_CODE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.CLIENT_CODE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.ERROR_CODE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.ERROR_MESSAGE.getApplicationTextValue());
			mail.append("\n");
			mail.append("\n");
			
			//Cuerpo con los resultados:
			for (WoLoadDetailVO woLoadDetailVO : loadDetail) {
				mail.append(woLoadDetailVO.getWoCode());
				mail.append("\t");
				mail.append("\t");
				mail.append(woLoadDetailVO.getCustomerCode());
				mail.append("\t");
				mail.append("\t");
				mail.append(woLoadDetailVO.getErrorCode() == null ? "" : woLoadDetailVO.getErrorCode());
				mail.append("\t");
				mail.append("\t");
				mail.append(woLoadDetailVO.getErrorMessage() == null ? "" : woLoadDetailVO.getErrorMessage());
				mail.append("\n");
			}
			SystemParameter sysParam = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity(), woLoad.getCountry().getId());
			UtilsBusiness.assertNotNull(sysParam, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el parámetro del sistema con el código: " + CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity() + " que determina el correo electrónico al que se le notificarán los reportes de cargue de Work orders");
			String recipientMail = sysParam.getValue();
			
			if(log.isDebugEnabled()){
				log.debug("Se enviará el correo con el informe de cargue de Wo's al siguiente correo: " + recipientMail);
			}
			
			SendEmailDTO emailDto = createEmail(recipientMail, mail.toString(), woLoad.getCountry().getId());
			businessEmailType.sendEmailByEmailTypeCodeAsic( emailDto, woLoad.getCountry().getId());
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion sendWoLoadMail/WoLoadBusinessBean");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina sendWoLoadMail/WoLoadBusinessBean ==");
		}
		
	}


	private SendEmailDTO createEmail(String recipientMail, String mailContent,
			Long id) throws BusinessException {
		
		SendEmailDTO emailDto= new SendEmailDTO();
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipientMail);
		emailDto.setRecipient(recipients);
		emailDto.setNewsType(EmailTypesEnum.WO_LOAD_REPORT.getEmailTypecode());
		emailDto.setNewsObservation(mailContent);
		emailDto.setNewsDocument(ApplicationTextEnum.WO_LOAD.getApplicationTextValue());
		emailDto.setNewsSourceUser(ApplicationTextEnum.AUTOMATIC_LOADING_PROCESS.getApplicationTextValue());
		return emailDto;
	}
	
	private SendEmailDTO createEmail(String recipientMail, String mailContent, 
			String reporte, String identificacionDocumento, User ... user) throws BusinessException, PropertiesException {
		
		SendEmailDTO emailDto= new SendEmailDTO();
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipientMail);
		emailDto.setRecipient(recipients);
		emailDto.setNewsType(reporte);
		emailDto.setNewsObservation(mailContent);
		emailDto.setNewsDocument(identificacionDocumento);
		if(user!=null && user.length>0){
			emailDto.setNewsSourceUser(user[0].getLogin());
		}
		else{
			emailDto.setNewsSourceUser(".");
		}
		
		return emailDto;
	}	

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendTrayManagementErrorMail(String woCode, Long countryId,
			String countryName, String errorCode, Date attDate, String message,
			String processCode, String processName, String idDoc, User... user) {
		log.debug("== Inicia sendTrayManagementErrorMail/WoLoadBusinessBean ==");
		try {
			//Cabecera del correo:
			StringBuffer mail = new StringBuffer(ApplicationTextEnum.ATTENTION_FINALIZATION_WOS.getApplicationTextValue());
			mail.append("\n");
			mail.append("\n");
			mail.append(ApplicationTextEnum.COUNTRY.getApplicationTextValue()+": ");
			mail.append(countryName);
			mail.append("\n");
			mail.append(ApplicationTextEnum.ATTENTION_DATE.getApplicationTextValue()+": ");
			mail.append(UtilsBusiness.dateToString(attDate,UtilsBusiness.DATE_FORMAT_YYYYMMDDHHMMSS));
			mail.append("\n");
			mail.append("\n");
		
			//Cabecera de los resultados:
			mail.append(ApplicationTextEnum.WO_CODE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.SUCCESSFULLY_PROCESSED.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.ERROR_CODE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.ERROR_MESSAGE.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.STATUS_PROCESS.getApplicationTextValue());
			mail.append("\t");
			mail.append(ApplicationTextEnum.PROCESS.getApplicationTextValue());
			mail.append("\n");
			mail.append("\n");
			
			//Cuerpo con los resultados:
			mail.append(woCode);
			mail.append("\t");
			mail.append("\t");
			mail.append("N");
			mail.append("\t");
			mail.append("\t");
			mail.append(errorCode == null ? "" : errorCode);
			mail.append("\t");
			mail.append("\t");
			mail.append(message == null ? "" : message);
			mail.append("\t");
			mail.append("\t");
			mail.append(processCode == null ? "" : processCode);
			mail.append("\t");
			mail.append("\t");
			mail.append(processName == null ? "" : processName);
			
			mail.append("\n");
			
			SystemParameter sysParam = systemParameterDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity(), countryId);
			UtilsBusiness.assertNotNull(sysParam, ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(), ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró el parámetro del sistema con el código: " + CodesBusinessEntityEnum.SYSTEM_PARAM_WO_LOAD_REPORT_RECIPIENT_MAIL.getCodeEntity() + " que determina el correo electrónico al que se le notificarán los reportes de cargue de Work orders");
			String recipientMail = sysParam.getValue();
			
			if(log.isDebugEnabled()){
				log.debug("Se enviará el correo con el informe de gestion de WO al siguiente correo: " + recipientMail);
			}
			
			SendEmailDTO emailDto = createEmail(recipientMail, mail.toString(), EmailTypesEnum.WO_MANAGEMENT.getEmailTypecode(), idDoc, user);
			businessEmailType.sendEmailByEmailTypeCodeAsic( emailDto, countryId);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion sendTrayManagementErrorMail/WoLoadBusinessBean");
		} finally {
			log.debug("== Termina sendTrayManagementErrorMail/WoLoadBusinessBean ==");
		}

		
	}	
}
