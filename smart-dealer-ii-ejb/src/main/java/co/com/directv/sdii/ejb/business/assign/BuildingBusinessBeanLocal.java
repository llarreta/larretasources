package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BuildingVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Building.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface BuildingBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto BuildingVO
	 * @param obj objeto que encapsula la información de un BuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createBuilding(BuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un BuildingVO
	 * @param obj objeto que encapsula la información de un BuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateBuilding(BuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un BuildingVO
	 * @param obj información del BuildingVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteBuilding(BuildingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un BuildingVO por su identificador
	 * @param id identificador del BuildingVO a ser consultado
	 * @return objeto con la información del BuildingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public BuildingVO getBuildingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los BuildingVO almacenados en la persistencia
	 * @return Lista con los BuildingVO existentes, una lista vacia en caso que no existan BuildingVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<BuildingVO> getAllBuildings() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de un BuildingVO por su código
	 * @param buildingCode
	 * @return objeto con la información del BuildingVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public BuildingVO getBuildingByCode(Long buildingCode) throws BusinessException;

	/**
	 * Método: Encargado de obtener la información de la relacion entre <br/>
	 * la compañía y el edificio, y también actualiza la información del edificio en caso de haber sido <br/>
	 * modificada desde ESB y guardarla en HSP.
	 * @param countryId identificador del pais
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void updateDealerBuilding(Long countryId) throws BusinessException;
}
