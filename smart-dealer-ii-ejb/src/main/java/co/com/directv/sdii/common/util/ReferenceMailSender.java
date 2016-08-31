package co.com.directv.sdii.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.RefInconsistency;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;

@Stateless(name="ReferenceMailSenderLocal",mappedName="ejb/ReferenceMailSenderLocal")
public class ReferenceMailSender implements ReferenceMailSenderLocal {

	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUser;
	
	@EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
	
	@EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
    private ElementTypeDAOLocal daoElementType;
	
	@EJB(name="SerializedDAOLocal", beanInterface=SerializedDAOLocal.class)
    private SerializedDAOLocal daoSerialized;
	
	private final static Logger log = UtilsBusiness.getLog4J(ReferenceMailSender.class);
	
	private void sendRefInconsistencyClosedMail(RefInconsistencyVO refInconsistencyVO, SendEmailDTO email) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		Set<String> recipients = new HashSet<String>();
		Reference reference = refInconsistencyVO.getReference();
		// informar al responsable de la bodega origen y destino sobre el cierre de la inconsistencia de la remisión  

		if(reference.getWarehouseBySourceWh() != null && !StringUtils.isBlank(reference.getWarehouseBySourceWh().getResponsibleEmail()))
			recipients.add( reference.getWarehouseBySourceWh().getResponsibleEmail() );
		
		if(reference.getWarehouseByTargetWh() != null && !StringUtils.isBlank(reference.getWarehouseByTargetWh().getResponsibleEmail())) 
			recipients.add( reference.getWarehouseByTargetWh().getResponsibleEmail() );
		
		if( !recipients.isEmpty() ) {
			email.setRecipient( recipients );
			businessEmailType.sendEmailByEmailTypeCodeAsic( email, reference.getCountryCodeId().getId());
		}
	}
	
	private void sendRefInconsistencyCreatedMail(RefInconsistency refInconsistencyVO, SendEmailDTO email, Long countryId) throws DAOServiceException, DAOSQLException, BusinessException, PropertiesException {
		
		Set<String> recipients = new HashSet<String>();
		Reference reference = refInconsistencyVO.getReference();

		// informar al responsable de la bodega origen y a los analistas logísticos DTV  
		if(reference.getWarehouseBySourceWh() != null && !StringUtils.isBlank(reference.getWarehouseBySourceWh().getResponsibleEmail()))
			recipients.add( reference.getWarehouseBySourceWh().getResponsibleEmail() );
		
		List<User> logisticsAnalystUsers = daoUser.getUsersByRoleTypeCodeAndCountryId(CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_ANALYST.getCodeEntity(), countryId);
		if(logisticsAnalystUsers != null) {
			for (User aUser : logisticsAnalystUsers) {
				if(!StringUtils.isEmpty(aUser.getEmail())) {
					recipients.add(aUser.getEmail());
				}
			}
		}
		
		if( !recipients.isEmpty() ) {
			email.setRecipient( recipients );
			businessEmailType.sendEmailByEmailTypeCodeAsic( email, reference.getCountryCodeId().getId());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.common.util.ReferenceMailSenderLocal#sendRefInconsistencyMoreElementsClosedMail(co.com.directv.sdii.model.vo.RefInconsistencyVO, co.com.directv.sdii.model.pojo.Reference, java.util.List)
	 */
	@Override
	public void sendRefInconsistencyMoreElementsClosedMail(RefInconsistencyVO refInconsistencyVO, List<ReportedElementVO> reportedElementVOs) {
		try {
			User user = daoUser.getUserById(refInconsistencyVO.getAnswerUserId());
			Reference reference = refInconsistencyVO.getReference();
			SendEmailDTO email = prepareSendEmailDTOToReferenceInconsistencyMoreElementsClosed(user, reference, reportedElementVOs);
			sendRefInconsistencyClosedMail(refInconsistencyVO, email);
		} catch (Throwable e) {
			String msg  = "no fue posible enviar el email de noticicación de cierre de inconsistencias en remisión por mas elementos físicos. id_inconsistencia = " + refInconsistencyVO.getId();
			log.error(msg, e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.common.util.ReferenceMailSenderLocal#sendRefInconsistencyLessElementsClosedMail(co.com.directv.sdii.model.vo.RefInconsistencyVO, co.com.directv.sdii.model.pojo.Reference, java.util.List)
	 */
	@Override
	public void sendRefInconsistencyLessElementsClosedMail(RefInconsistencyVO refInconsistencyVO, Reference devolutionReference, List<ReportedElementVO> reportedElementVOs) {
		try {
			User user = daoUser.getUserById(refInconsistencyVO.getAnswerUserId());
			SendEmailDTO email = prepareSendEmailDTOToReferenceInconsistencyLessElementsClosed(user, devolutionReference, reportedElementVOs);
			sendRefInconsistencyClosedMail(refInconsistencyVO, email);
		} catch (Throwable e) {
			String msg  = "no fue posible enviar el email de noticicación de cierre de inconsistencias en remisión por menos elementos físicos. id_inconsistencia = " + refInconsistencyVO.getId();
			log.error(msg, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.common.util.ReferenceMailSenderLocal#sendRefInconsistencyCreatedMail(co.com.directv.sdii.model.vo.RefInconsistencyVO, java.util.List)
	 */
	@Override
	public void sendRefInconsistencyCreatedMail(RefInconsistency refInconsistency, List<ReportedElementVO> reportedElementVOs) {
		try {
			User user = daoUser.getUserById(refInconsistency.getCreateUserId());
			SendEmailDTO email = prepareSendEmailDTOToReferenceInconsistencyCreated(user, refInconsistency, reportedElementVOs);
			Long countryId = user.getCountry().getId();
			sendRefInconsistencyCreatedMail(refInconsistency, email, countryId);
		} catch (Throwable e) {
			String msg = "no fue posible enviar el email de noticicación de creación de inconsistencias en remisión. id_inconsistencia = " + refInconsistency.getId();
			log.error(msg, e);
		}
	}
	
	private SendEmailDTO prepareSendEmailDTOToReferenceInconsistencyCreated(User user, RefInconsistency refInconsistencyVO, List<ReportedElementVO> reportedElementVOs) throws DAOServiceException, DAOSQLException {
		SendEmailDTO email = new SendEmailDTO();
    	email.setNewsType( EmailTypesEnum.REFERENCE_INCONSISTENCY.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        
        StringBuffer obs = new StringBuffer(ApplicationTextEnum.NEW_INCONSISTENCY.getApplicationTextValue()+" # ").append(refInconsistencyVO.getReference().getId()).append("\n")
        	.append(ApplicationTextEnum.TYPE_INCONSISTENCY.getApplicationTextValue()+" ").append(refInconsistencyVO.getRefIncType().getRefIncTypeCode())
        	.append(" - ").append(refInconsistencyVO.getRefIncType().getRefIncTypeName()).append("\n\n");
    	
        if(!StringUtils.isBlank(refInconsistencyVO.getRefIncComments())) {
        	obs.append(ApplicationTextEnum.OBSERVATIONS_MADE.getApplicationTextValue()+": ").append(refInconsistencyVO.getRefIncComments()).append("\n\n");
        }
        
        String strTemp="";
        if(refInconsistencyVO.getRefIncDate()!=null)
        	strTemp= UtilsBusiness.dateToString(refInconsistencyVO.getRefIncDate(),"yyyy-MM-dd HH:mm:ss");
		
        obs.append(ApplicationTextEnum.DATE_REGISTRATION_INCONSISTENCY.getApplicationTextValue()+": ").append(strTemp).append("\n\n");
        
        if(reportedElementVOs != null) {
	    	obs.append(ApplicationTextEnum.INCONSISTENT_ELEMENTS.getApplicationTextValue()+":");
	    	for (ReportedElementVO reportedElementVO : reportedElementVOs) {
	    		
	    		ElementType elementType = reportedElementVO.getElementType(); 
    			if(elementType != null) {
    				if(elementType.getId() != null && elementType.getTypeElementName() == null) {
    					elementType = daoElementType.getElementTypeByID(reportedElementVO.getElementType().getId());
    				}
    				obs.append("\n\n "+ApplicationTextEnum.NAME.getApplicationTextValue()+": ").append(elementType.getTypeElementName());
    			}
	    		
	    		if(!StringUtils.isEmpty(reportedElementVO.getSerialCode())) {
	    			obs.append("\n\n"+ApplicationTextEnum.SERIAL.getApplicationTextValue()+": ").append(reportedElementVO.getSerialCode());
	    			
	    			Serialized serialized = reportedElementVO.getSerialized();
	    			if(serialized ==  null) {
	    				serialized = daoSerialized.getSerializedBySerial(reportedElementVO.getSerialCode(),refInconsistencyVO.getReference().getCountryCodeId().getId());
	    			}
	    				
	    			if(serialized.getSerialized() != null &&  !StringUtils.isEmpty(serialized.getSerialized().getSerialCode())) {
	    				obs.append("\n\n"+ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue()+": ").append(serialized.getSerialized().getSerialCode());
	    			}
	    		}
	    		obs.append("\n\n "+ApplicationTextEnum.QUANTITY.getApplicationTextValue()+": ").append(reportedElementVO.getQty()).append("\n\n");
	    		
			}
	    }
        
        email.setNewsObservation( obs.toString() );
        
        email.setNewsSourceUser( user.getName() );
        return email;
	}
	
	/**
	 * Metodo: configura el email de informe de cierre de una inconsistencia por menos elementos
	 * @param user
	 * @param devolutionReference
	 * @param reportedElementVOs
	 * @return SendEmailDTO objeto con la configuración del mail a enviar
	 * @author wjimenez
	 */
	private SendEmailDTO prepareSendEmailDTOToReferenceInconsistencyLessElementsClosed(User user, Reference devolutionReference, List<ReportedElementVO> reportedElementVOs) {
		
		SendEmailDTO email = new SendEmailDTO();
    	email.setNewsType( EmailTypesEnum.REFERENCE_INCONSISTENCY_CLOSED.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        
        StringBuffer obs = new StringBuffer(ApplicationTextEnum.CLOSE_INCONSISTENCY_REMISSION.getApplicationTextValue()+" # ").append(devolutionReference.getParentReferenceId()).append("\n")
        	.append(ApplicationTextEnum.GENERATE_NEW_INCONSISTENCY.getApplicationTextValue()+" # ").append(devolutionReference.getId()).append(ApplicationTextEnum.CLOSE_INCONSISTENCY.getApplicationTextValue()+"\n\n");
        
        if(reportedElementVOs != null) {
        	obs.append(ApplicationTextEnum.ELEMENTS_ASSOCIATED_REMISSION.getApplicationTextValue()+":");
        	for (ReportedElementVO reportedElementVO : reportedElementVOs) {
        		obs.append("\n"+ApplicationTextEnum.NAME.getApplicationTextValue()+": ").append(reportedElementVO.getElementType().getTypeElementName());
        		if(!StringUtils.isEmpty(reportedElementVO.getSerialCode())) {
        			obs.append("\n"+ApplicationTextEnum.SERIAL.getApplicationTextValue()+": ").append(reportedElementVO.getSerialCode());
        			if(reportedElementVO.getSerialized().getSerialized() != null &&  !StringUtils.isEmpty(reportedElementVO.getSerialized().getSerialized().getSerialCode())) {
        				obs.append("\n"+ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue()+": ").append(reportedElementVO.getSerialized().getSerialized().getSerialCode());
        			}
        		}
        		obs.append("\n"+ApplicationTextEnum.QUANTITY.getApplicationTextValue()+": ").append(reportedElementVO.getQty()).append("\n");
        		
			}
        }
        
        email.setNewsObservation( obs.toString() );
        email.setNewsSourceUser( user.getName() );
        
        if(log.isDebugEnabled()) {
        	log.debug(email.getNewsObservation());
        }
        
        return email;
	}
	
	/**
	 * Metodo: configura el email de informe de cierre de una inconsistencia por mas elementos
	 * @param user
	 * @param devolutionReference
	 * @param reportedElementVOs
	 * @return SendEmailDTO objeto con la configuración del mail a enviar
	 * @author wjimenez
	 */
	private SendEmailDTO prepareSendEmailDTOToReferenceInconsistencyMoreElementsClosed(User user, Reference ref, List<ReportedElementVO> reportedElementVOs) {
		
		SendEmailDTO email = new SendEmailDTO();
    	email.setNewsType( EmailTypesEnum.REFERENCE_INCONSISTENCY_CLOSED.getEmailTypecode() );
        email.setNewsDocument( user.getIdNumber() );
        
        StringBuffer obs = new StringBuffer(ApplicationTextEnum.CLOSE_INCONSISTENCY_REMISSION.getApplicationTextValue()+" # ").append(ref.getId())        
        	.append("\n\n");
        
        if(reportedElementVOs != null) {
        	obs.append(ApplicationTextEnum.NEW_ELEMENTS_REMISSION.getApplicationTextValue()+":\n");
        	for (ReportedElementVO reportedElementVO : reportedElementVOs) {
        		obs.append("\n "+ApplicationTextEnum.NAME.getApplicationTextValue()+": ").append(reportedElementVO.getElementType().getTypeElementName());
        		if(!StringUtils.isEmpty(reportedElementVO.getSerialCode())) {
        			obs.append("\n "+ApplicationTextEnum.SERIAL.getApplicationTextValue()+": ").append(reportedElementVO.getSerialCode());
        			if(reportedElementVO.getSerialized().getSerialized() != null &&  !StringUtils.isEmpty(reportedElementVO.getSerialized().getSerialized().getSerialCode())) {
        				obs.append("\n "+ApplicationTextEnum.SERIAL_LINKED.getApplicationTextValue()+": ").append(reportedElementVO.getSerialized().getSerialized().getSerialCode());
        			}
        		}
        		obs.append("\n"+ApplicationTextEnum.QUANTITY.getApplicationTextValue()+": ").append(reportedElementVO.getQty()).append("\n");        		
			}
        }
        
        email.setNewsObservation( obs.toString() );
        email.setNewsSourceUser( user.getName() );
        
        if(log.isDebugEnabled()) {
        	log.debug(email.getNewsObservation());
        }
        
        return email;
	}
	
}
