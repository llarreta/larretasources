package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerBuildingVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerBuilding.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerBuildingFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerBuilding
	 * @param obj - HistoDealerBuildingVO  objeto que encapsula la información de un HistoDealerBuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerBuilding
	 * @param obj - HistoDealerBuildingVO  objeto que encapsula la información de un HistoDealerBuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerBuilding
	 * @param obj - HistoDealerBuildingVO  información del HistoDealerBuildingVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerBuilding por su identificador
	 * @param id - Long identificador del HistoDealerBuilding a ser consultado
	 * @return objeto con la información del HistoDealerBuildingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerBuildingVO getHistoDealerBuildingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerBuilding almacenados en la persistencia
	 * @return List<HistoDealerBuildingVO> Lista con los HistoDealerBuildingVO existentes, una lista vacia en caso que no existan HistoDealerBuildingVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerBuildingVO> getAllHistoDealerBuildings() throws BusinessException;

}
