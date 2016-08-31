package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerBuildingVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerBuilding.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerBuildingBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerBuildingVO
	 * @param obj objeto que encapsula la información de un HistoDealerBuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerBuildingVO
	 * @param obj objeto que encapsula la información de un HistoDealerBuildingVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerBuildingVO
	 * @param obj información del HistoDealerBuildingVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerBuilding(HistoDealerBuildingVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerBuildingVO por su identificador
	 * @param id identificador del HistoDealerBuildingVO a ser consultado
	 * @return objeto con la información del HistoDealerBuildingVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerBuildingVO getHistoDealerBuildingByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerBuildingVO almacenados en la persistencia
	 * @return Lista con los HistoDealerBuildingVO existentes, una lista vacia en caso que no existan HistoDealerBuildingVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerBuildingVO> getAllHistoDealerBuildings() throws BusinessException;

}
