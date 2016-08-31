package co.com.directv.sdii.facade.core.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.core.ContactBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.ContactFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.vo.ContactVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Contact
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see facade.core.ContactFacadeLocal
 */
@Stateless(name="ContactFacadeLocal",mappedName="ejb/ContactFacadeLocal")
public class ContactFacadeBean implements ContactFacadeBeanLocal {

		
    @EJB(name="ContactBusinessBeanLocal", beanInterface=ContactBusinessBeanLocal.class)
    private ContactBusinessBeanLocal businessContact;
        
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ContactFacadeLocal#getContactsByID(java.lang.Long)
     */
    public ContactVO getContactByID(Long id) throws BusinessException {
    	return businessContact.getContactByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ContactFacadeLocal#createContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	public void createContact(ContactVO obj) throws BusinessException {
		businessContact.createContact(obj);
	}
	
	public void createTryContact(ContactDTO contactDTO) throws BusinessException {
		businessContact.createTryContact(contactDTO);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ContactFacadeLocal#updateContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	public void updateContact(ContactVO obj) throws BusinessException {
		businessContact.updateContact(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ContactFacadeLocal#deleteContact(co.com.directv.sdii.model.vo.ContactVO)
	 */
	public void deleteContact(ContactVO obj) throws BusinessException {
		businessContact.deleteContact(obj);
	}

	@Override
	public List<ContactVO> getContactsByWorkOrder(ContactDTO contactDTO) throws BusinessException {
		return businessContact.getContactsWorkOrderByDealer(contactDTO);
	}

	@Override
	public List<ContactVO> getContactsTriesByWorkOrder(String woCode) throws BusinessException{
		return businessContact.getContactsTriesByWorkOrder(woCode);
	}
	
	@Override
	public void createContactCore(ContactDTO contactDTO) throws BusinessException {
		businessContact.createContactCore(contactDTO);		
	}
}
