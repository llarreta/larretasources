package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.VehicleTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.VehicleTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.VehicleTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad VehicleTypes 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.VehicleTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.VehicleTypesFacadeBeanLocal
 */
@Stateless(name="VehicleTypesFacadeBeanLocal",mappedName="ejb/VehicleTypesFacadeBeanLocal")
public class VehicleTypesFacadeBean implements VehicleTypesFacadeBeanLocal {
	
	@EJB(name="VehicleTypesCRUDBeanLocal",beanInterface=VehicleTypesCRUDBeanLocal.class)
	private VehicleTypesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad VehicleTypes 
	 * @return List<VehicleTypesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<VehicleTypeVO> getAllVehicleTypes() throws BusinessException {
		return business.getAllVehicleTypes();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad VehicleTypes.
	 * @param code
	 * @return VehicleTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public VehicleTypeVO getVehicleTypesByCode(String code) throws BusinessException {
		return business.getVehicleTypesByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad VehicleTypes.
	 * @param id
	 * @return VehicleTypesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public VehicleTypeVO getVehicleTypesByID(Long id) throws BusinessException {
		return business.getVehicleTypesByID(id);
	}
		

}
