package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.VehicleVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Vehicles
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehiclesFacadeBeanLocal {

    public void createVehicles(VehicleVO obj) throws BusinessException;

    public VehicleVO getVehiclesByID(Long id) throws BusinessException;

    public void updateVehicles(VehicleVO obj) throws BusinessException;

    public void deleteVehicles(VehicleVO obj) throws BusinessException;

    public List<VehicleVO> getAllVehicles() throws BusinessException;

    public List<String> getAllVehiclePlates() throws BusinessException;

    public VehicleVO getVehicleByPlate(String plate) throws BusinessException;

    public List<VehicleVO> getVehiclesByDealerCodeOrDepotCode(long dealerCode, String depotCode) throws BusinessException;

	public List<VehicleVO> getAllVehiclesOnlyBasicInfo()throws BusinessException;

	public List<VehicleVO> getVehiclesByDealerId(long dealerId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los vehiculos activos por id de dealer
	 * @param dealerId
	 * @return Lista de vehiculos activos asociados a un dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<VehicleVO> getActiveVehiclesByDealerId(long dealerId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los vehiculos por dealer y por placa
	 * @param dealerId
	 * @param plate
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<VehicleVO> getVehiclesByDealerIdAndPlate(Long dealerId,String plate)throws BusinessException;

	/**
	 * 
	 * Metodo: Consulta los vehiculos asociados a un dealer y en un estado especifico 
	 * @param dealerId id del dealer
	 * @param statusCode codigo del estado del vehiculo por el cual se desea filtrar
	 * @return Lista de vehiculos asociados al dealer y en el estado enviado por parametro
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<VehicleVO> getVehiclesByDealerIdAndStatusCodeOrPlate(Long dealerId, String plate)throws BusinessException;

}
