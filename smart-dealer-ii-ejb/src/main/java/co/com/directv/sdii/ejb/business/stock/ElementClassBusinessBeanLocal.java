package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementClass;
import co.com.directv.sdii.model.pojo.collection.ElementClassResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementClassVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ElementClass.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementClassBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementClassVO
	 * @param obj objeto que encapsula la información de un ElementClassVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ElementClass
	 * @author gfandino
	 */
	public void createElementClass(ElementClassVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementClassVO
	 * @param obj objeto que encapsula la información de un ElementClassVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de ElementClass
	 * @author gfandino
	 */
	public void updateElementClass(ElementClassVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementClassVO
	 * @param obj información del ElementClassVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de ElementClass
	 * @author gfandino
	 */
	public void deleteElementClass(ElementClassVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementClassVO por su identificador
	 * @param id identificador del ElementClassVO a ser consultado
	 * @return objeto con la información del ElementClassVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementClass por ID
	 * @author gfandino
	 */
	public ElementClassVO getElementClassByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementClassVO almacenados en la persistencia
	 * @return Lista con los ElementClassVO existentes, una lista vacia en caso que no existan ElementClassVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todas las ElementClass
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
	 * @return Lista con los ElementClassVO existentes con estado activo, una lista vacia en caso que no existan ElementClassVO en el sistema
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
	 */
	public ElementClassResponse getElementClassByAllStatusPage(String code,RequestCollectionInfo requestCollInfo)throws BusinessException;;

	/**
	 * Método: Obtiene la información de las clases de elemento de un elemento serializado.
	 * @param serialNumber - Numero serial de un elemento.
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta del ElementClass dado un número serial de un serializado.
	 */
	public ElementClass getElementClassByElementSerialNumber(String serialNumber) throws BusinessException;
	
}
