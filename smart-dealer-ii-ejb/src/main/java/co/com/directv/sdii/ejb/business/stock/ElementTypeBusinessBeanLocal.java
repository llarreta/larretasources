package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ElementType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementTypeVO
	 * @param obj objeto que encapsula la información de un ElementTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createElementType(ElementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementTypeVO
	 * @param obj objeto que encapsula la información de un ElementTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateElementType(ElementTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementTypeVO
	 * @param obj información del ElementTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteElementType(ElementTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementTypeVO por su identificador
	 * @param id identificador del ElementTypeVO a ser consultado
	 * @return objeto con la información del ElementTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ElementTypeVO getElementTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementTypeVO almacenados en la persistencia
	 * @return Lista con los ElementTypeVO existentes, una lista vacia en caso que no existan ElementTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ElementTypeVO> getAllElementTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene el tipo de elemento dado el código
	 * @param code código del tipo de elemento a ser consultado
	 * @return Tipo de elemento con el código especificado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public ElementTypeVO getElementTypeByCode(String code)throws BusinessException;
	
	/**
	 * Metodo: Obtiene el tipo de elemento activos
	 * @return List<ElementTypeVO> que se encuentran activos; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de activos
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByActive() throws BusinessException;
	
	/**
	 * Metodo: Obtiene el tipo de elemento activos
	 * @para isSerialized true si es serializado; false en otro caso
	 * @return List<ElementTypeVO> que se encuentran activos; vacio en otro caso
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de activos
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByActiveAndIsSerialized(boolean isSerialized) throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementTypeVO asociados al ElementModel con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementTypeVO> asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModel (String codeElementModel)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementModelVO> activos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModelAndActiveStatus (String codeElementModel)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO inactivos asociados al ElementClass con codeElementModel
	 * @param codeElementModel - String código del ElementModel
	 * @return List<ElementModelVO> inactivos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType inactivos dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModelAndInactiveStatus (String codeElementModel)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementTypeVO asociados al ElementModel con idElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @return List<ElementTypeVO> asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModelId (Long idElementModel)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @return List<ElementModelVO> activos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModelIdAndActiveStatus (Long idElementModel)throws BusinessException;
	
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO activos asociados al ElementClass con idElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con la información de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 */
	public ElementTypeResponse getElementTypesByElementModelIdAndActiveStatus (Long idElementModel,RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Método: Obtiene la información de todos los ElementModelVO inactivos asociados al ElementClass con idElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @return List<ElementModelVO> inactivos asociados al ElementModel
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType inactivos dado ElementModel
	 * @author gfandino
	 */
	public List<ElementTypeVO> getElementTypesByElementModelIdAndInactiveStatus (Long idElementModel)throws BusinessException;

	/**
	 * Método: Obtiene la información de todos los ElementTypeVO activos asociados al ElementModel
	 * @param idElementModel - Long id del ElementModel
	 * @param code  - String Código del tipo del elemento
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con la información de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 * 
	 */
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel, String code, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Método: Permite consultar todos los ElementType en estado activo indicando si es prepago o no
	 * @param prepaid - String indica si se consultan serializados o no serializados
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con los resultados de la consulta
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementType activos dado ElementModel
	 * @author gfandino
	 * 
	 */
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(String prepaid, RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Método encargado de retornar los ElementTypeVO de acuendo a los parametros enviados de Módelo 
	 * y Tipo de elemento (Serializado y no serializado)
	 * @param elementModelCode
	 * @param isSerialized
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public List<ElementTypeVO> getElementTypeByElementModelAndIsSerialized(
			String elementModelCode, boolean isSerialized) throws BusinessException;
	
	/**
	 * Metodo: Metodo encargado de consultar los modelos y tipos de elementos filtrados por una bodega y un modelo
	 * @param warehouseId
	 * @param elementModelId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<ElementTypeVO> getElementTypeByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws BusinessException;

	/**
	 * Método encargado de consultar los tipos del elementos activos con respecto al
	 * los filtros recibidos en al operación  
	 * @param isPrepaid
	 * @param isSerialized
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public List<ElementTypeVO> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws BusinessException;
	
	public List<ElementTypeVO> getElementTypesByModelStatusAndIsSerialized(Boolean isSerialized, String elementModelCode,Boolean elementTypeStatus) throws BusinessException;
}
