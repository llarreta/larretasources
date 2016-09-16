package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementTypeVO;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ElementType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementTypeDAOLocal {

	/**
	 * Metodo:  persiste la información de un ElementType
	 * @param obj objeto que encapsula la información de un ElementType
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ElementType
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ElementType
	 * @author jjimenezh
	 */
	public void createElementType(ElementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ElementType
	 * @param obj objeto que encapsula la información de un ElementType
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ElementType
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ElementType
	 * @author gfandino
	 */
	public void updateElementType(ElementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementType
	 * @param obj información del ElementType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ElementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ElementType
	 * @author jjimenezh
	 */
	public void deleteElementType(ElementType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementType por su identificador
	 * @param id identificador del ElementType a ser consultado
	 * @return objeto con la información del ElementType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType por ID
	 * @author jjimenezh
	 */
	public ElementType getElementTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementType almacenados en la persistencia
	 * @return Lista con los ElementType existentes, una lista vacia en caso que no existan ElementType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ElementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ElementType
	 * @author jjimenezh
	 */
	public List<ElementType> getAllElementTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un ElementType dado su código
	 * @param code código por el cual se consultará el tipo de elemento
	 * @return Tipo de elemento dado el código
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType por código
	 * @author jjimenezh
	 */
	public ElementType getElementTypeByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de un ElementType dado su estado
	 * @param status - String estado del elemento
	 * @return List<ElementType> correspondiente al estado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType por estado
	 * @author gfandino
	 */
	public List<ElementType> getElementTypesByIsActive(String status)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la informaciÃ³n de un ElementType dado su estado
	 * @param status - String estado del elemento
	 * @param isSerialized - boolean true si es serializado; false en otro caso
	 * @return List<ElementType> correspondiente al estado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType por estado
	 * @author gfandino
	 */
	public List<ElementType> getElementTypesByIsActiveAndIsSerialized(String status,boolean isSerialized)
		throws DAOServiceException, DAOSQLException;
	
	
	
	//nuevooooooooooooooos
	
	/**
	 * Método: Permite consultar todos los ElementType dado un ElementModel
	 * @param elementModelCode - String Código del ElementModel
	 * @return List<ElementType> asociados al ElementModel
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @author gfandino
	 */
	public List<ElementType> getElementTypeByElementModel(String elementModelCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementType en estado dado un ElementModel 
	 * @param elementModelCode - String Código del ElementModel
	 * @param elementTypeStatus - String Estado
	 * @return List<ElementType> en estado y asociados al ElementModel
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @author gfandino
	 */
	public List<ElementType> getElementTypeByElementModelAndStatus(String elementModelCode,String elementTypeStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementType dado su nombre
	 * @param name nombre por el cual se consultará el tipo de elemento
	 * @return Tipo de elemento dado el nombre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType por nombre
	 * @author gfandino
	 */
	public ElementType getElementTypeByName(String name)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementType dado un ElementModel
	 * @param elementModelId - Long Id del ElementModel
	 * @return List<ElementType> asociados al ElementModel
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @author gfandino
	 */
	public List<ElementType> getElementTypeByElementModelId(Long elementModelId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementType en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param elementTypeStatus - String Estado
	 * @return List<ElementType> en estado y asociados al ElementModel
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @author gfandino
	 */
	public List<ElementType> getElementTypeByElementModelIdAndStatus(Long elementModelId,String elementTypeStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementType en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param elementTypeStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @author gfandino
	 */
	public ElementTypeResponse getElementTypeByElementModelIdAndStatus(Long elementModelId,String elementTypeStatus,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Permite consultar todos los ElementType en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param code - String código del tipo del elemento
	 * @param elementTypeStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @author gfandino
	 * 
	 */
	public ElementTypeResponse getElementTypesByElementModelIdAndAllStatusPage(
			Long idElementModel, String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementType en estado activo indicando si es prepago o no
	 * @param prepaid - String indica si se consultan serializados o no serializados
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementTypeResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementType en estado por ElementModel
	 * @author gfandino
	 * 
	 */
	public ElementTypeResponse getElementTypesByPrepaidIdAndActiveStatusPage(String prepaid, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * 
	 * Metodo: Retorna los tipos de elementos segun el id de la unidad de medida.
	 * @param id
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ElementType> getElementTypeByMeasureUnitID(Long id)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método:  Método encargado de retornar los ElementTypeVO de acuendo a los parametros enviados de Módelo 
	 * y Tipo de elemento (Serializado y no serializado)
	 * @param elementModelCode - String Código del ElementModel
	 * @return List<ElementType> asociados al ElementModel
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementTypepor ElementModel
	 * @author gfandino
	 */
	public List<ElementType> getElementTypeByElementModelAndIsSerialized(String elementModelCode, boolean isSerialized)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Metodo encargado de retornar los ElementType existentes en una ubicacion
	 * @param warehouseId
	 * @param elementModelId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<ElementType> getElementTypeByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId)
	throws DAOServiceException, DAOSQLException;	
	
	/**
	 * 
	 * @param isPrepaid
	 * @param isSerialized
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<ElementType> getElementTypeBySerorNotSerAndPrepaidorNotPrepaid(
			String isPrepaid, String isSerialized) throws  DAOServiceException, DAOSQLException;
	
	public List<ElementType> getElementTypesByModelStatusAndIsSerialized(Boolean isSerialized, String elementModelCode,Boolean elementTypeStatus)throws DAOServiceException, DAOSQLException;

	public List<ElementType> getElementsTypeByCode(List<String> listaElementTypeCode) throws DAOServiceException, DAOSQLException;
}