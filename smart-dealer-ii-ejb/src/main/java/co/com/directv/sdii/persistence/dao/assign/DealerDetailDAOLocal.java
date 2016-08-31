package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerDetail;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad DealerDetail
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerDetailDAOLocal {

	/**
	 * Metodo:  persiste la información de un DealerDetail
	 * @param obj objeto que encapsula la información de un DealerDetail
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un DealerDetail
	 * @param obj objeto que encapsula la información de un DealerDetail
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerDetail
	 * @param obj información del DealerDetail a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerDetail(DealerDetail obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un DealerDetail por su identificador
	 * @param id identificador del DealerDetail a ser consultado
	 * @return objeto con la información del DealerDetail dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerDetail getDealerDetailByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerDetail almacenados en la persistencia
	 * @return Lista con los DealerDetail existentes, una lista vacia en caso que no existan DealerDetail en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerDetail> getAllDealerDetails() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerDetail almacenados en la persistencia por país
	 * @return Lista con los DealerDetail existentes, una lista vacia en caso que no existan DealerDetail en el sistema por país
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public List<DealerDetail> getAllDealerDetailsByCountry(Long idCountry) throws DAOServiceException, DAOSQLException;
	
	


}