package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad DealerTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerTypesDAOLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerType
	 * @param obj objeto que encapsula la información de un DealerType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerType(DealerType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerType
	 * @param obj objeto que encapsula la información de un DealerType
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerType(DealerType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerType
	 * @param obj información del DealerType a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerType(DealerType obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public DealerType getDealerTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerType por su identificador
	 * @param id identificador del DealerType a ser consultado
	 * @return objeto con la información del DealerType dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerType getDealerTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerType almacenados en la persistencia
	 * @return Lista con los DealerType existentes, una lista vacia en caso que no existan DealerType en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerType> getAllDealerTypes() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: <Descripcion>
	 * @param isAlloc
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<DealerType> getDealerTypesByIsAlloc(String isAlloc)throws DAOServiceException, DAOSQLException;

}