package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.pojo.Element;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.collection.BuildingElementHistoryResponse;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Element
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementDAOLocal {

	/**
	 * Metodo:  persiste la información de un Element
	 * @param obj objeto que encapsula la información de un Element
	 * @throws DAOServiceException en caso de error al ejecutar la creación de Element
	 * @throws DAOSQLException en caso de error al ejecutar la creación de Element
	 * @author gfandino
	 */
	public void createElement(Element obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Element
	 * @param obj objeto que encapsula la información de un Element
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de Element
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de Element
	 * @author gfandino
	 */
	public void updateElement(Element obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Element
	 * @param obj información del Element a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de Element
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de Element
	 * @author gfandino
	 */
	public void deleteElement(Element obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Element por su identificador
	 * @param id identificador del Element a ser consultado
	 * @return objeto con la información del Element dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public Element getElementByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Element almacenados en la persistencia
	 * @return Lista con los Element existentes, una lista vacia en caso que no existan Element en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los Element
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los Element
	 * @author gfandino
	 */
	public List<Element> getAllElements(Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información los Element almacenados en la persistencia correspondientes <br>
	 * al identificador ElementType especificado
	 * @param idElementType - Long identificado del ElementType
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ElementResponse, List<Element> Lista de Elementos asociados al ElementType especificado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Element por ElementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Element por ElementType
	 * @author gfandino
	 */
	public ElementResponse getElementsByElementType(Long idElementType, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información del elemento que tiene el tipo de código especificado y no es serializado
	 * @param codeElement - String
	 * @return Element correspondiente al código de elemento (tipo de elemento)
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Element por ElementType
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Element por ElementType
	 * @author gfandino
	 */
	public Element getElementsByElementTypeCodeAndIsSerialized(String codeElement,boolean isSerialized,Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene una lista de objetos que mapean la consulta de historia de elementos
	 * @param ibsCode String Codigo del edificio
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return BuildingElementHistoryResponse, List<Object[]> Lista con datos necesarios para responder a la consulta de historia de elementos serializados
	 * @throws DAOServiceException En caso de error al realizar la consulta
	 * @throws DAOSQLException En caso de error al realizar la consulta
	 * @author cduarte
	 */
	public BuildingElementHistoryResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información del elemento que tiene el tipo de código especificado y no es serializado
	 * @param codeElement String Codigo del elemento
	 * @param serialCode String Serial del elemento
	 * @param warehouseId Long Id de la bodega en la que se va a buscar el elemento
	 * @return List<Element> Lista de elementos serializados que cumplen con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Element por ElementType y serialCode
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Element por ElementType y serialCode
	 * @author jnova
	 */
	public List<Serialized> getSerializedElementsByElementTypeCodeAndSerial(String codeElement , String serialCode , Long warehouseId)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: consulta un elementos por su serial y tipo de elemento. En caso dado, tambien agrega al filtro el RID
	 * y el serial del vinculado
	 * @param serial String Serial del elemento a buscar
	 * @param typeCode String Codigo del tipo de elemento
	 * @param Rid String Rid del elemento
	 * @param LinkedSerial String Serial del elementos vinculado
	 * @return Element Elemento que cumple con el filtro
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Element por ElementType y serialCode
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Element por ElementType y serialCode
	 * @author jnova
	 */
	public Element getElementBySerialAndTypeAndRidAndLinkedSerial(String serial, String typeCode, String Rid, String LinkedSerial,Long countryId) throws DAOServiceException, DAOSQLException;	
	
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink( ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO )throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Método encargado de contar los elementos 
	 * segpun el tipo de elemento
	 * @param elementTypeId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Long countElementByElementType(Long elementTypeId) throws DAOServiceException, DAOSQLException;
	
	
	/**
	 * 
	 * Metodo: Método encargado de contar los elementos en el sistema por medio del elementType 
	 * segpun el tipo de elemento
	 * @param elementTypeId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public Long countElementByElementModel(Long elementModelId) throws DAOServiceException, DAOSQLException;
	
}