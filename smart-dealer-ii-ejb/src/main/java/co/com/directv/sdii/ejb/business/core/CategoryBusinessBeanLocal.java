package co.com.directv.sdii.ejb.business.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CategoryVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Category.
 * 
 * Fecha de Creación: Oct 7, 2014
 * @author ssanabri
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CategoryBusinessBeanLocal {

	/**
	 * Metodo: Obtiene la información de todos los Categories padres 
	 * @return Lista con los Categories padres, una lista vacia en caso que no existan Category en el sistema.
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación	
	 * @author
	 */
	public List<CategoryVO> getParentCategories() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los categories para el padre enviado por parametro.
	 * @param parentId el id de la categoria padre. Es obligatorio colocarlo, caso contrario se devolvera una BusinessException.
	 * @return Lista con los Category existentes, una lista vacia en caso que no existan Category en el sistema.
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación	
	 * @author
	 */
	public List<CategoryVO> getChildrenCategories(Long parentId) throws BusinessException;
}
