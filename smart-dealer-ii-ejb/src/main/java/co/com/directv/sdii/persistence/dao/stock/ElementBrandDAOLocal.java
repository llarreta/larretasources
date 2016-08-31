package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementBrand;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad ElementBrand
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementBrandDAOLocal {

	/**
	 * Metodo:  persiste la información de un ElementBrand
	 * @param obj objeto que encapsula la información de un ElementBrand
	 * @throws DAOServiceException en caso de error al ejecutar la creación de ElementBrand
	 * @throws DAOSQLException en caso de error al ejecutar la creación de ElementBrand
	 * @author gfandino
	 */
	public void createElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un ElementBrand
	 * @param obj objeto que encapsula la información de un ElementBrand
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de ElementBrand
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de ElementBrand
	 * @author gfandino
	 */
	public void updateElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementBrand
	 * @param obj información del ElementBrand a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de ElementBrand
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de ElementBrand
	 * @author gfandino
	 */
	public void deleteElementBrand(ElementBrand obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un ElementBrand por su identificador
	 * @param id identificador del ElementBrand a ser consultado
	 * @return objeto con la información del ElementBrand dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand por ID
	 * @author gfandino
	 */
	public ElementBrand getElementBrandByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrand almacenados en la persistencia
	 * @return Lista con los ElementBrand existentes, una lista vacia en caso que no existan ElementBrand en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los ElementBrand
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los ElementBrand
	 * @author gfandino
	 */
	public List<ElementBrand> getAllElementBrands() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de ElementBrand almacenados en la persistencia correspondiente al código
	 * @param code - String código del ElementBrand
	 * @return ElementBrand correspondiente al código ingresado; nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand por código
	 * @author gfandino
	 */
	public ElementBrand getElementBrandByCode(String code)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de ElementBrand almacenados en la persistencia correspondiente al nombre
	 * @param name - String código del ElementBrand
	 * @return ElementBrand correspondiente al nombre ingresado; nulo en caso contrario
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand por nombre
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand por nombre
	 * @author gfandino
	 */
	public ElementBrand getElementBrandByName(String name)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información del ElementBrand correspondiente por estado especificado
	 * @param codeEntity - String estado del modelo de elemento
	 * @return List<ElementBrand> cuyo estado es el especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand por estado
	 * @author gfandino
	 */
	public List<ElementBrand> getElementBrandByStatus(String codeEntity)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información del ElementBrand correspondiente por estado especificado
	 * @param codeEntity - String estado del modelo de elemento
	 * @return List<ElementBrand> cuyo estado es el especificado, vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand por estado
	 * @author gfandino
	 */
	public ElementBrandResponse getElementBrandByStatus(String codeEntity,RequestCollectionInfo requestCollInfo )throws DAOServiceException, DAOSQLException;

	/**
	 * Método: Obtiene la información de todas las marcas 
	 * @param code - String Código de la marca de elemento
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return ElementBrandResponse con la paginación
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de ElementBrand 
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de ElementBrand 
	 * @author gfandino
	 * 
	 */
	public ElementBrandResponse getElementBrandByAllStatuPage(
			String code, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;;



}