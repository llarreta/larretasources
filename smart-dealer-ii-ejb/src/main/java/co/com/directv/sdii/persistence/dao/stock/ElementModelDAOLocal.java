package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementModel;
import co.com.directv.sdii.model.pojo.collection.ElementModelResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ElementModel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementModelDAOLocal {

	/**
	 * Metodo:  persiste la información de un ElementModel
	 * @param obj objeto que encapsula la información de un ElementModel
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ElementModel
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ElementModel
	 * @author gfandino
	 */
	public void createElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ElementModel
	 * @param obj objeto que encapsula la información de un ElementModel
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ElementModel
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ElementModel
	 * @author gfandino
	 */
	public void updateElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementModel
	 * @param obj información del ElementModel a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ElementModel
	 * @author gfandino
	 */
	public void deleteElementModel(ElementModel obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementModel por su identificador
	 * @param id identificador del ElementModel a ser consultado
	 * @return objeto con la información del ElementModel dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por ID
	 * @author gfandino
	 */
	public ElementModel getElementModelByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementModel almacenados en la persistencia
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ElementModelResponse,Lista con los ElementModel existentes, una lista vacia en caso que no existan ElementModel en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ElementModel
	 * @author gfandino
	 */
	public ElementModelResponse getAllElementModels(RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información del ElementModel correspondiente al código especificado
	 * @param modelCode - String
	 * @return ElementModel cuyo código es el especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por código
	 * @author gfandino
	 */
	public ElementModel getElementModelByCode(String modelCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información del ElementModel correspondiente al nombre especificado
	 * @param modelName - String
	 * @return ElementModel cuyo nombre es el especificado. Nulo en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por nombre
	 * @author gfandino
	 */
	public ElementModel getElementModelByName(String modelName)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información del ElementModel correspondiente por estado especificado
	 * @param codeEntity - String estado del modelo de elemento
	 * @return List<ElementModel> cuyo estado es el especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por estado
	 * @author gfandino
	 */
	public List<ElementModel> getElementModelsByStatus(String codeEntity)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Permite consultar todos los ElementModel dado un ElementClass
	 * @param elementClassCode - String Código del ElementClass
	 * @return List<ElementModel> asociados al ElementClass
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por ElementClass
	 * @author gfandino
	 */
	public List<ElementModel> getElementModelByElementClass(String elementClassCode)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información del ElementModel correspondiente por tipo de elemento y estado especificado
	 * @oaram elementType - Long tipo del elemento
	 * @param codeEntity - String estado del modelo de elemento
	 * @return List<ElementModel> cuyo estado es el especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por tipo de elemento y estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por tipo de elemento y estado
	 * @author jnova
	 */
	public List<ElementModel> getElementModelsByElementTypeCodeStatus(String elementTypeCode,
			String codeEntity)throws DAOServiceException, DAOSQLException;
			
			/**
	 * Método: Permite consultar todos los ElementModel en estado dado un ElementClass 
	 * @param elementClassCode - String Código del ElementClass
	 * @param elementModelStatus - String Estado
	 * @return List<ElementModel> en estado y asociados al ElementClass
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @author gfandino
	 */
	public List<ElementModel> getElementModelByElementClassAndStatus(String elementClassCode,String elementModelStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementModel dado un ElementClass
	 * @param elementClassId - Long Id del ElementClass
	 * @return List<ElementModel> asociados al ElementClass
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel por ElementClass
	 * @author gfandino
	 */
	public List<ElementModel> getElementModelByElementClassId(Long elementClassId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementModel en estado dado un ElementClass 
	 * @param elementClassId - Long ID del ElementClass
	 * @param elementModelStatus - String Estado
	 * @return List<ElementModel> en estado y asociados al ElementClass
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @author gfandino
	 */
	public List<ElementModel> getElementModelByElementClassIdAndStatus(Long elementClassId,String elementModelStatus)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Método: Permite consultar todos los ElementModel en estado dado un ElementClass 
	 * @param elementClassId - Long ID del ElementClass
	 * @param elementModelStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementBrandResponse con los elementos de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @author gfandino
	 */
	public ElementModelResponse getElementModelByElementClassIdAndStatus(Long elementClassId,String elementModelStatus,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Permite consultar todos los ElementModel en estado dado un ElementClass 
	 * @param elementClassId - Long ID del ElementClass
	 * @param code - String Código del modelo del elemento
	 * @param elementModelStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return ElementBrandResponse con los elementos de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementModel en estado por ElementClass
	 * @author gfandino
	 * 
	 */
	public ElementModelResponse getElementModelsByElementClassIdAndAllStatusPage(
			Long idElementClass, String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Método encargado de consultar los modelos activos bajo el filtro de prepago o no prepago
	 * @param isPrepaid
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<ElementModel> getElementModelsByActiveStatusAndIsPrepaid(
			String isPrepaid)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Método encargado de consultar los modelos de los elementos que existen en una bodega
	 * @param warehouseId id de la ubicacion
	 * @param elementModelId Id del modelo del elemento
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author cduarte
	 */
	public List<ElementModel> getElementModelsByWarehouseIdAndElementModelId(Long warehouseId,Long elementModelId) throws DAOServiceException, DAOSQLException;
	
}