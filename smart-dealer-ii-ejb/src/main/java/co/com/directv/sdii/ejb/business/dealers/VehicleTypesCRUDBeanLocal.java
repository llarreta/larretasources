package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.VehicleTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad VehicleTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehicleTypesCRUDBeanLocal {

	public VehicleTypeVO getVehicleTypesByCode(String code) throws BusinessException;
	
	public VehicleTypeVO getVehicleTypesByID(Long id) throws BusinessException;
	
	public List<VehicleTypeVO> getAllVehicleTypes() throws BusinessException;
}
