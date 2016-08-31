package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.VehicleVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Vehicles
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface VehiclesCRUDBeanLocal {

    public void createVehicles(VehicleVO obj) throws BusinessException;

    public VehicleVO getVehiclesByID(Long id) throws BusinessException;

    public void updateVehicles(VehicleVO obj) throws BusinessException;

    public void deleteVehicles(VehicleVO obj) throws BusinessException;

    public List<VehicleVO> getAllVehicles() throws BusinessException;

    public VehicleVO getVehicleByPlate(String plate) throws BusinessException;

    public List<String> getAllVehiclePlates() throws BusinessException ;

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
}
