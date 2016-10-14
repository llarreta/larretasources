package co.com.directv.sdii.ejb.business.dealers;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CrewOff;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.vo.CrewOffVO;
import co.com.directv.sdii.model.vo.CrewVO;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Crews
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewsCRUDBeanLocal {

    public void createCrews(CrewVO obj) throws BusinessException;

    public CrewVO getCrewsByID(Long id) throws BusinessException;

    public void validateDetachedEmployee(CrewVO obj, Employee employeeOldResponsibleForCrew, List<Employee> employeesOldsNotResponsibleCrew ) throws BusinessException;
    /**
     * 
     * Metodo: Actualiza una cuadrilla
     * @param obj objeto que encapsula informacion dela cuadrilla
     * @param description en caso que se inactive, contiene la informacion del motivo de inactivacion
     * @throws BusinessException <tipo> <descripcion>
     * @author jnova
     */
    public void updateCrews(CrewVO obj, String description, UserVO user) throws BusinessException;

    public void deleteCrews(CrewVO obj) throws BusinessException;

    public List<CrewVO> getAllCrews() throws BusinessException;

    public void deactivateCrew(CrewVO crewVO, String description) throws BusinessException;

    public List<CrewVO> getCrewsByPlateOrDocument(String plate, String document) throws BusinessException;
    
    public List<CrewVO> getCrewsByPlateOrDocumentOrDealer(String plate, String document, Long dealer) throws BusinessException;

	public List<CrewVO> getCrewsByDealerIdStartDateAndEndDate(Long dealerId, Date startDate, Date endDate)throws BusinessException;

	public List<CrewVO> getAllCrewsOnlyBasicInfo()throws BusinessException;

	public List<CrewVO> getCrewsByVehicleId(long vehicleId)throws BusinessException;
	
	public List<CrewVO> getCrewsByDealerId(Long dealerId) throws BusinessException;
	
	public List<CrewVO> getCrewsByResponsibleNameAndDealerId(String responsibleName, Long idDealer) throws BusinessException ;
	
	/**
	 * Metodo: permite consultar las cuadrillas por el id del dealer y el tipo de cuadrilla
	 * @param dealerId Long id del dealer
	 * @param crewType Long tipo de cuadrilla
	 * @return List<Crew> Lista de cuadrillas que cumple con el filtro
	 * @throws BusinessException Error en la busqueda de las cuadrillas por el filtro seleccionado
	 * @author jnova
	 */
	public List<CrewVO> getCrewsByDealerIdAndType(Long dealerId , Long crewType) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las cuadrillas de un dealer, por tipo de cuadrilla, id de dealer y nombre del responsable de la cuadrilla
	 * @param dealerId
	 * @param crewType
	 * @param responsableName
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<CrewVO> getCrewsByDealerIdAndTypeAndResponsableName(Long dealerId , Long crewType,String responsableName) throws BusinessException;
	
	public CrewVO getCrewAmountByDealerId(Long crewId,Long dealerId) throws BusinessException;

	public List<CrewVO> getActiveCrewsByDealerId(Long dealerId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna un listado de cuadrillas desactivadas
	 * @param  crewOff CrewOff
	 * @return List<CrewOffVO>
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public List<CrewOffVO> getCrewOffByCrew(CrewOff crewOff) throws BusinessException;
}
