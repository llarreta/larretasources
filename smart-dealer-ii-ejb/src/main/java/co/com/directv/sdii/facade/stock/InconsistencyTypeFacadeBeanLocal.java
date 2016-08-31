package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.InconsistencyTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad InconsistencyType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto InconsistencyType
	 * @param obj - InconsistencyTypeVO  objeto que encapsula la información de un InconsistencyTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un InconsistencyType
	 * @param obj - InconsistencyTypeVO  objeto que encapsula la información de un InconsistencyTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un InconsistencyType
	 * @param obj - InconsistencyTypeVO  información del InconsistencyTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteInconsistencyType(InconsistencyTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un InconsistencyType por su identificador
	 * @param id - Long identificador del InconsistencyType a ser consultado
	 * @return objeto con la información del InconsistencyTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public InconsistencyTypeVO getInconsistencyTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los InconsistencyType almacenados en la persistencia
	 * @return List<InconsistencyTypeVO> Lista con los InconsistencyTypeVO existentes, una lista vacia en caso que no existan InconsistencyTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<InconsistencyTypeVO> getAllInconsistencyTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene un tipo de inconsistencia por el código
	 * @param code código de la inconsistencia a ser consultada
	 * @return tipo de inconsistencia con el código especificado
	 * @throws BusinessException en caso de error al obtener el tipo de inconsistencia por el códig
	 * @author jjimenezh
	 */
	public InconsistencyTypeVO getInconsistencyTypeByCode(String code)throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de todos los InconsistencyType almacenados en la persistencia
	 * @return List<InconsistencyTypeVO> Lista con los InconsistencyTypeVO existentes, una lista vacia en caso que no existan InconsistencyTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<InconsistencyTypeVO> getActiveInconsistencyTypes() throws BusinessException;

}
