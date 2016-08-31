package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementTypeAndModelDTO;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementModelVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ElementModel.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementModelBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementModelVO
	 * @param obj objeto que encapsula la información de un ElementModelVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ElementModel
	 * @author gfandino
	 */
	public void createElementModel(ElementModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementModelVO
	 * @param obj objeto que encapsula la información de un ElementModelVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de ElementModel
	 * @author gfandino
	 */
	public void updateElementModel(ElementModelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementModelVO
	 * @param obj información del ElementModelVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de ElementModel
	 * @author gfandino
	 */
	public void deleteElementModel(ElementModelVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementModelVO por su identificador
	 * @param id identificador del ElementModelVO a ser consultado
	 * @return objeto con la información del ElementModelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementModel por ID
	 * @author gfandino
	 */
	public ElementModelVO getElementModelByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO almacenados en la persistencia
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ElementModelResponse, Lista con los ElementModelVO existentes, una lista vacia en caso que no existan ElementModelVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel
	 * @author gfandino
	 */
	public ElementModelResponse getAllElementModels(RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementModelVO por su código
	 * @param code - String código del ElementModelVO a ser consultado
	 * @return ElementModelVO objeto con la información del ElementModelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementModel por Código
	 * @author gfandino
	 */
	public ElementModelVO getElementModelByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO almacenados en la persistencia con estado activo
	 * @return Lista con los ElementModelVO existentes con estado activo, una lista vacia en caso que no existan ElementModelVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel con estado activo
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModelVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementModelVO existentes con estado inactivo, una lista vacia en caso que no existan ElementModelVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel con estado inactivo
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByInActiveStatus() throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO asociados al ElementClass con codeElementClass
	 * @param codeElementClass - String código del ElementClass
	 * @return List<ElementModelVO> asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClass (String codeElementClass)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con codeElementClass
	 * @param codeElementClass - String código del ElementClass
	 * @return List<ElementModelVO> activos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClassAndActiveStatus (String codeElementClass)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO inactivos asociados al ElementClass con codeElementClass
	 * @param codeElementClass - String código del ElementClass
	 * @return List<ElementModelVO> inactivos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel inactivos dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClassAndInactiveStatus (String codeElementClass)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @return List<ElementModelVO> asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClassId (Long idElementClass)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @return List<ElementModelVO> activos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClassIdAndActiveStatus (Long idElementClass)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @param requestCollInfo - RequestCollectionInfo Información de la paginación
	 * @return ElementModelResponse con los elementos de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel activos dado ElementClass
	 * @author gfandino
	 */
	public ElementModelResponse getElementModelsByElementClassIdAndActiveStatus (Long idElementClass,RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO inactivos asociados al ElementClass con idElementClass
	 * @param idElementClass - Long id del ElementClass
	 * @return List<ElementModelVO> inactivos asociados al ElementClass
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel inactivos dado ElementClass
	 * @author gfandino
	 */
	public List<ElementModelVO> getElementModelsByElementClassIdAndInactiveStatus (Long idElementClass)throws BusinessException;

	/**
	 * Método: Consulta todos los modelos de elemento
	 * @param idElementClass - Long id del ElementClass
	 * @param code - String código del modelo del elemento 
	 * @param requestCollInfo - RequestCollectionInfo Información de la paginación
	 * @return ElementModelResponse modelo de elementos paginados
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementModel dado ElementClass
	 * @author gfandino
	 *
	 */
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(
			Long idElementClass, String code, RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param isPrepaid <tipo> <descripcion>
	 * @author
	 */
	public List<ElementModelVO> getElementModelsByActiveStatusAndIsPrepaid(String isPrepaid)throws BusinessException;
	
	/**
	 * Metodo: Metodo permite consultar los tipos y modelos de elementos filtrados por una bodega y un elemento 
	 * @param warehouseId
	 * @param elementModelId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public ElementTypeAndModelDTO getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws BusinessException;

}
