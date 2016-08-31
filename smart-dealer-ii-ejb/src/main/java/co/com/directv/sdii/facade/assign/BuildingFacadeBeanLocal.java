package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BuildingVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Building.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface BuildingFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Building
	 * @param obj - BuildingVO  objeto que encapsula la información de un BuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createBuilding(BuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Building
	 * @param obj - BuildingVO  objeto que encapsula la información de un BuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateBuilding(BuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Building
	 * @param obj - BuildingVO  información del BuildingVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteBuilding(BuildingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Building por su identificador
	 * @param id - Long identificador del Building a ser consultado
	 * @return objeto con la información del BuildingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public BuildingVO getBuildingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Building almacenados en la persistencia
	 * @return List<BuildingVO> Lista con los BuildingVO existentes, una lista vacia en caso que no existan BuildingVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<BuildingVO> getAllBuildings() throws BusinessException;

}
