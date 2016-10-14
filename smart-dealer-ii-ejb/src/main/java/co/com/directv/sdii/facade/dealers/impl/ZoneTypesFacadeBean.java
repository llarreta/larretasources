package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ZoneTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ZoneTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ZoneTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad ZoneTypes 
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.ZoneTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ZoneTypesFacadeBeanLocal
 */
@Stateless(name="ZoneTypesFacadeBeanLocal",mappedName="ejb/ZoneTypesFacadeBeanLocal")
public class ZoneTypesFacadeBean implements ZoneTypesFacadeBeanLocal {
	
	@EJB(name="ZoneTypesCRUDBeanLocal",beanInterface=ZoneTypesCRUDBeanLocal.class)
	private ZoneTypesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad ZoneTypes 
	 * @return List<ZoneTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<ZoneTypeVO> getAllZoneTypes() throws BusinessException {
		return business.getAllZoneTypes();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad ZoneTypes.
	 * @param code
	 * @return ZoneTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ZoneTypeVO getZoneTypesByCode(String code) throws BusinessException {
		return business.getZoneTypesByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad ZoneTypes.
	 * @param id
	 * @return ZoneTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public ZoneTypeVO getZoneTypesByID(Long id) throws BusinessException {
		return business.getZoneTypesByID(id);
	}
		

}
