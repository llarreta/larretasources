package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.VehicleStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.VehicleStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.VehicleStatusVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad VehicleStatus 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.VehicleStatusCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.VehicleStatusFacadeBeanLocal
 */
@Stateless(name="VehicleStatusFacadeBeanLocal",mappedName="ejb/VehicleStatusFacadeBeanLocal")
public class VehicleStatusFacadeBean implements VehicleStatusFacadeBeanLocal {
	
	@EJB(name="VehicleStatusCRUDBeanLocal",beanInterface=VehicleStatusCRUDBeanLocal.class)
	private VehicleStatusCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad VehicleStatus 
	 * @return List<VehicleStatusVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<VehicleStatusVO> getAllVehicleStatus() throws BusinessException {		
		return business.getAllVehicleStatus();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad VehicleStatus.
	 * @param code
	 * @return VehicleStatusVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public VehicleStatusVO getVehicleStatusByCode(String code) throws BusinessException {
		return business.getVehicleStatusByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad VehicleStatus.
	 * @param id
	 * @return VehicleStatusVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public VehicleStatusVO getVehicleStatusByID(Long id) throws BusinessException {		
		return business.getVehicleStatusByID(id);
	}
		

}
