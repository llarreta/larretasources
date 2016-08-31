package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.MembershipTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.MembershipTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MembershipTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad MembershipTypes 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.MembershipTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.MembershipTypesFacadeBeanLocal
 */
@Stateless(name="MembershipTypesFacadeBeanLocal",mappedName="ejb/MembershipTypesFacadeBeanLocal")
public class MembershipTypesFacadeBean implements MembershipTypesFacadeBeanLocal {
	
	@EJB(name="MembershipTypesCRUDBeanLocal",beanInterface=MembershipTypesCRUDBeanLocal.class)
	private MembershipTypesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad MembershipTypes 
	 * @return List<MembershipTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<MembershipTypeVO> getAllMembershipTypes() throws BusinessException {
		return business.getAllMembershipTypes();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad MembershipTypes.
	 * @param code
	 * @return MembershipTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public MembershipTypeVO getMembershipTypesByCode(String code) throws BusinessException {
		return business.getMembershipTypesByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad MembershipTypes.
	 * @param id
	 * @return MembershipTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public MembershipTypeVO getMembershipTypesByID(Long id) throws BusinessException {
		return business.getMembershipTypesByID(id);
	}
		

}
