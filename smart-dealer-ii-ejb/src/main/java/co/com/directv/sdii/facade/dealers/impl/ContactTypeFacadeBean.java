package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ContactTypeCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ContactTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ContactTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad ContactType 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.ContactTypeCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ContactTypeFacadeBeanLocal
 */
@Stateless(name="ContactTypeFacadeBeanLocal",mappedName="ejb/ContactTypeFacadeBeanLocal")
public class ContactTypeFacadeBean implements ContactTypeFacadeBeanLocal {
	
	@EJB(name="ContactTypeCRUDBeanLocal",beanInterface=ContactTypeCRUDBeanLocal.class)
	private ContactTypeCRUDBeanLocal business;
	
	/**
	 * Metodo: Retorna la lista de todos los tipos de contacto
	 * @return  List<ContactTypeVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ContactTypeVO> getAllContactType() throws BusinessException {
		return business.getAllContactType();
	}

	/**
	 * 
	 * Retorna un tipo de contacto de acuerdo con su codigo
	 * @param code
	 * @return ContactTypeVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ContactTypeVO getContactTypeByCode(String code) throws BusinessException {
		return business.getContactTypeByCode(code);
	}

	/**
	 * 
	 * Retorna un tipo de contacto de acuerdo con su ID
	 * @param id
	 * @return ContactTypeVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ContactTypeVO getContactTypeByID(Long id) throws BusinessException {
		return business.getContactTypeByID(id);
	}
		

}
