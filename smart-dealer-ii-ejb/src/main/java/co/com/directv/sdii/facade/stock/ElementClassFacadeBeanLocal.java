package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementClassVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ElementClass.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementClassFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ElementClass
	 * @param obj - ElementClassVO  objeto que encapsula la información de un ElementClassVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ElementClass
	 * @author gfandino
	 */
	public void createElementClass(ElementClassVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ElementClass
	 * @param obj - ElementClassVO  objeto que encapsula la información de un ElementClassVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ElementClass
	 * @author gfandino
	 */
	public void updateElementClass(ElementClassVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ElementClass
	 * @param obj - ElementClassVO  información del ElementClassVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ElementClass
	 * @author gfandino
	 */
	public void deleteElementClass(ElementClassVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ElementClass por su identificador
	 * @param id - Long identificador del ElementClass a ser consultado
	 * @return objeto con la información del ElementClassVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ElementClass por ID
	 * @author gfandino
	 */
	public ElementClassVO getElementClassByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ElementClass almacenados en la persistencia
	 * @return List<ElementClassVO> Lista con los ElementClassVO existentes, una lista vacia en caso que no existan ElementClassVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ElementClass
	 * @author gfandino
	 */
	public List<ElementClassVO> getAllElementClasss() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementClassVO por su código
	 * @param code - String Código del ElementClassVO a ser consultado
	 * @return ElementClassVO objeto con la información del ElementClassVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementClass por código
	 * @author gfandino
	 */
	public ElementClassVO getElementClassByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementClassResponse con los datos asociados a la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado activo
	 * @author gfandino
	 */
	public List<ElementClassVO> getElementClassByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementClassResponse con los datos asociados a la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado activo
	 * @author gfandino
	 */
	public ElementClassResponse getElementClassByActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementClassVO existentes con estado inactivo, una lista vacia en caso que no existan ElementClassVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass con estado inactivo
	 * @author gfandino
	 */
	public List<ElementClassVO> getElementClassByInactiveStatus() throws BusinessException;

	/**
	 * Método: Obtiene la información de las clases de elemento en cualquier estado
	 * @param code - String código de la clase de elemento
	 * @param requestCollInfo - RequestCollectionInfo información d epaginación
	 * @return ElementClassResponse clases de elemento paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass en cualquier estado
	 * @author gfandino
	 * @param code 
	 */
	public ElementClassResponse getElementClassByAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws BusinessException;

}
