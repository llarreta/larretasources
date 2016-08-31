package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TransportCompany;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransportCompanyResponse;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad TransportCompany
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TransportCompanyDAOLocal {

	/**
	 * Metodo:  persiste la información de un TransportCompany
	 * @param obj objeto que encapsula la información de un TransportCompany
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un TransportCompany
	 * @param obj objeto que encapsula la información de un TransportCompany
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un TransportCompany
	 * @param obj información del TransportCompany a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteTransportCompany(TransportCompany obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un TransportCompany por su identificador
	 * @param id identificador del TransportCompany a ser consultado
	 * @return objeto con la información del TransportCompany dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public TransportCompany getTransportCompanyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los TransportCompany almacenados en la persistencia
	 * @return Lista con los TransportCompany existentes, una lista vacia en caso que no existan TransportCompany en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 * @param countryId 
	 */
	public List<TransportCompany> getTransportCompaniesByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de todos los TransportCompany almacenados en la persistencia
	 * @param Long countryId, identificador del pais.
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginar.
	 * @return TransportCompanyResponse, Lista con los TransportCompany existentes, una lista vacia en caso que no existan TransportCompany en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 * @param countryId 
	 */
	public TransportCompanyResponse getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;
	
	

	/**
	 * Metodo: Obtiene la información de todos los TransportCompany almacenados en la persistencia
	 * @param Long countryId, identificador del pais.
	 * @param RequestCollectionInfo requestCollInfo, parametros para paginar.
	 * @return TransportCompanyResponse, Lista con los TransportCompany existentes, una lista vacia en caso que no existan TransportCompany en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 * @param countryId 
	 */
	public TransportCompanyResponse getAllTransportCompaniesByCountryId(Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene la información de una compañía transportadora dado el código
	 * @param code código del a compañía transportadora
	 * @return objeto con la información de una compañía transportadora
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public TransportCompany getTransportCompanyByCode(String code)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la compañía transportadora por código de compañía y país
	 * @param code código de compañía transportadora 
	 * @param countryId identificador de país
	 * @return compañía cuyo código coincide con el especificado, nulo en caso que no se encuentre la compañía con el código
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public TransportCompany getTransportCompanyByCodeAndCountryId(String code, Long countryId) throws DAOServiceException, DAOSQLException;;


}