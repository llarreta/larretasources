package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Supplier;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Supplier
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SupplierDAOLocal {

	/**
	 * Metodo:  persiste la información de un Supplier
	 * @param obj objeto que encapsula la información de un Supplier
	 * @throws DAOServiceException en caso de error al ejecutar la creación de Supplier
	 * @throws DAOSQLException en caso de error al ejecutar la creación de Supplier
	 * @author gfandino 
	 */
	public void createSupplier(Supplier obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Supplier
	 * @param obj objeto que encapsula la información de un Supplier
	 * @throws DAOServiceException en caso de error al ejecutar la actualización de Supplier
	 * @throws DAOSQLException en caso de error al ejecutar la actualización de Supplier
	 * @author gfandino
	 */
	public void updateSupplier(Supplier obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Supplier
	 * @param obj información del Supplier a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la eliminación de Supplier
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la eliminación de Supplier
	 * @author gfandino
	 */
	public void deleteSupplier(Supplier obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Supplier por su identificador
	 * @param id identificador del Supplier a ser consultado
	 * @return objeto con la información del Supplier dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por ID
	 * @author gfandino
	 */
	public Supplier getSupplierByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Supplier almacenados en la persistencia
	 * @param Long countryId, id del pais
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return SuppliersResponse, Lista con los Supplier existentes, una lista vacia en caso que no existan Supplier en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los Supplier
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los Supplier
	 * @author
	 */
	public SuppliersResponse getAllSuppliers(Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un Supplier por su código
	 * @param code - String
	 * @return Supplier correspondiente al código especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por código
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por código
	 * @author gfandino
	 */
	public Supplier getSupplierByCode(String code)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Supplier por país
	 * @param countryId - Long
	 * @return List<Supplier> Lista de Supplier correpondientes al país especificado. Vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por país
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por país
	 * @author gfandino
	 */
	public List<Supplier> getSupplierByCountryId(Long countryId)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de Supplier por su nit
	 * @param nit - String
	 * @return Supplier correspondiente al nit especificado, nulo en caso contrario.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por nit
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por nit
	 * @author gfandino
	 */
	public Supplier getSupplierByNit(String nit)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Supplier por estado
	 * @param codeStatus - String código del estado del proveedor
	 * @return Supplier correspondientes al estado especificado, vacio en caso contrario.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por estado
	 * @author gfandino
	 */
	public List<Supplier> getSupplierByStatus(String codeStatus)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de los Supplier por estado y país
	 * @param codeStatus - String código del estado del proveedor
	 * @param countryId - Long identificador del país consultado
	 * @return Supplier correspondientes al estado y país especificados, vacio en caso contrario.
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de Supplier por estado y país
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de Supplier por estado y país
	 * @author gfandino
	 */
	public List<Supplier> getSupplierByStatusAndCountry(String codeStatus,
			Long countryId)throws DAOServiceException, DAOSQLException;


}