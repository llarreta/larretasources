package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerServiceSubCategory.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerServiceSubCategoryFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerServiceSubCategory
	 * @param obj - HistoDealerServiceSubCategoryVO  objeto que encapsula la información de un HistoDealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerServiceSubCategory
	 * @param obj - HistoDealerServiceSubCategoryVO  objeto que encapsula la información de un HistoDealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerServiceSubCategory
	 * @param obj - HistoDealerServiceSubCategoryVO  información del HistoDealerServiceSubCategoryVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerServiceSubCategory por su identificador
	 * @param id - Long identificador del HistoDealerServiceSubCategory a ser consultado
	 * @return objeto con la información del HistoDealerServiceSubCategoryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerServiceSubCategoryVO getHistoDealerServiceSubCategoryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerServiceSubCategory almacenados en la persistencia
	 * @return List<HistoDealerServiceSubCategoryVO> Lista con los HistoDealerServiceSubCategoryVO existentes, una lista vacia en caso que no existan HistoDealerServiceSubCategoryVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerServiceSubCategoryVO> getAllHistoDealerServiceSubCategorys() throws BusinessException;

}
