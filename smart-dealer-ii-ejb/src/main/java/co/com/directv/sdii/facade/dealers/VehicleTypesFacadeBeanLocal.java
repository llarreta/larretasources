package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.VehicleTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de VehicleTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehicleTypesFacadeBeanLocal {

    public VehicleTypeVO getVehicleTypesByCode(String code) throws BusinessException;

    public VehicleTypeVO getVehicleTypesByID(Long id) throws BusinessException;

    public List<VehicleTypeVO> getAllVehicleTypes() throws BusinessException;

}
