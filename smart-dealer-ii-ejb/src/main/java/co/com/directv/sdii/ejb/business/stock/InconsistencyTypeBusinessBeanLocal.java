package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad InconsistencyType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto InconsistencyTypeVO
	 * @param obj objeto que encapsula la información de un InconsistencyTypeVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void createInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un InconsistencyTypeVO
	 * @param obj objeto que encapsula la información de un InconsistencyTypeVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void updateInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un InconsistencyTypeVO
	 * @param obj información del InconsistencyTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un InconsistencyTypeVO por su identificador
	 * @param id identificador del InconsistencyTypeVO a ser consultado
	 * @return objeto con la información del InconsistencyTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public InconsistencyTypeVO getInconsistencyTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los InconsistencyTypeVO almacenados en la persistencia
	 * @return Lista con los InconsistencyTypeVO existentes, una lista vacia en caso que no existan InconsistencyTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los InconsistencyTypeVO almacenados en la persistencia
	 * @return Lista con los InconsistencyTypeVO existentes, una lista vacia en caso que no existan InconsistencyTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes() throws BusinessException;

	
	/**
	 * Metodo: Obtiene un tipo de inconsistencia por el código
	 * @param code código de la inconsistencia a ser consultada
	 * @return tipo de inconsistencia con el código especificado
	 * @throws BusinessException en caso de error al obtener el tipo de inconsistencia por el códig
	 * @author jjimenezh
	 */
	public InconsistencyTypeVO getInconsistencyTypeByCode(String code)throws BusinessException;
	
}
