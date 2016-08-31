package co.com.directv.sdii.ws.business.core.contact.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.ContactFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.vo.ContactVO;
import co.com.directv.sdii.ws.business.core.contact.IContactWS;

/**
 * Servicio web que expone las operaciones relacionadas con Contact
 * 
 * Fecha de Creación:  Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see facade.core.ContactFacadeBeanLocal
 */
@MTOM(threshold=3072)
@WebService(serviceName="ContactService",
		endpointInterface="co.com.directv.sdii.ws.business.core.contact.IContactWS",
		targetNamespace="http://directvla.com.contract/ws/sdii/Contacts",
		portName="ContactPort")
@Stateless()
public class ContactWS implements IContactWS {

	@EJB
    private ContactFacadeBeanLocal ejbRef;
	
	@Override
	public void createContact(ContactVO objContact) throws BusinessException{
		ejbRef.createContact(objContact);
	}
	
	@Override	
	public void createTryContact(ContactDTO contactDTO) throws BusinessException{
		ejbRef.createTryContact(contactDTO);
	}
	
	@Override
	public void updateContact(ContactVO objContact) throws BusinessException{
		ejbRef.updateContact(objContact);
	}
	
	@Override
	public void deleteContact(ContactVO objContact) throws BusinessException{
		ejbRef.deleteContact(objContact);
	}
	
	@Override
	public ContactVO getContactByID(Long id) throws BusinessException{
		return ejbRef.getContactByID(id);
	}

	@Override
	public List<ContactVO> getContactsByWorkOrder(ContactDTO contactDTO) throws BusinessException {		
		return ejbRef.getContactsByWorkOrder(contactDTO);
	}

	@Override
	public List<ContactVO> getContactsTriesByWorkOrder(String woCode) throws BusinessException{
		return ejbRef.getContactsTriesByWorkOrder(woCode);
	}
	
	@Override
	public void createContactCore(ContactDTO contactDTO) throws BusinessException {
		ejbRef.createContactCore(contactDTO);		
	}
	
}
