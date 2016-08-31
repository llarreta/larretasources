package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCategoryVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerServiceSubCategory.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerServiceSubCategoryBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerServiceSubCategoryVO
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerServiceSubCategoryVO
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCategoryVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerServiceSubCategoryVO
	 * @param obj información del HistoDealerServiceSubCategoryVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerServiceSubCategory(HistoDealerServiceSubCategoryVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerServiceSubCategoryVO por su identificador
	 * @param id identificador del HistoDealerServiceSubCategoryVO a ser consultado
	 * @return objeto con la información del HistoDealerServiceSubCategoryVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerServiceSubCategoryVO getHistoDealerServiceSubCategoryByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerServiceSubCategoryVO almacenados en la persistencia
	 * @return Lista con los HistoDealerServiceSubCategoryVO existentes, una lista vacia en caso que no existan HistoDealerServiceSubCategoryVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerServiceSubCategoryVO> getAllHistoDealerServiceSubCategorys() throws BusinessException;

}
