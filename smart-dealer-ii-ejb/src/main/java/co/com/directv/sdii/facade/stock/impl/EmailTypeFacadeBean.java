package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.common.EmailTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.EmailTypeFacadeBeanLocal;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.vo.EmailTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD EmailType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.EmailTypeFacadeLocal
 */
@Stateless(name="EmailTypeFacadeLocal",mappedName="ejb/EmailTypeFacadeLocal")
public class EmailTypeFacadeBean implements EmailTypeFacadeBeanLocal {

		
    @EJB(name="EmailTypeBusinessBeanLocal", beanInterface=EmailTypeBusinessBeanLocal.class)
    private EmailTypeBusinessBeanLocal businessEmailType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.EmailTypeFacadeLocal#getAllEmailTypes()
     */
    public List<EmailTypeVO> getAllEmailTypes() throws BusinessException {
    	return businessEmailType.getAllEmailTypes();
    }

    /* (non-Javadoc)
     * @see #{$business_package_name$}.EmailTypeFacadeLocal#getEmailTypesByID(java.lang.Long)
     */
    public EmailTypeVO getEmailTypeByID(Long id) throws BusinessException {
    	return businessEmailType.getEmailTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmailTypeFacadeLocal#createEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	public void createEmailType(EmailTypeVO obj) throws BusinessException {
		businessEmailType.createEmailType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmailTypeFacadeLocal#updateEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	public void updateEmailType(EmailTypeVO obj) throws BusinessException {
		businessEmailType.updateEmailType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.EmailTypeFacadeLocal#deleteEmailType(co.com.directv.sdii.model.vo.EmailTypeVO)
	 */
	public void deleteEmailType(EmailTypeVO obj) throws BusinessException {
		businessEmailType.deleteEmailType(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.EmailTypeFacadeBeanLocal#getEmailTypeByCode(java.lang.String)
	 */
	public EmailTypeVO getEmailTypeByCode(String code) throws BusinessException {
		return businessEmailType.getEmailTypeByCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.EmailTypeFacadeBeanLocal#getEmailTypeByCode(java.lang.String)
	 */
	@Override
	public void sendEmailByEmailTypeCode(SendEmailDTO sendEmailDTO, Long countryId) throws BusinessException {
		businessEmailType.sendEmailByEmailTypeCode(sendEmailDTO, countryId);
		
	}

	@Override
	public List<EmailTypeVO> getActiveEmailTypes() throws BusinessException {
		return businessEmailType.getActiveEmailTypes();
	}
}
