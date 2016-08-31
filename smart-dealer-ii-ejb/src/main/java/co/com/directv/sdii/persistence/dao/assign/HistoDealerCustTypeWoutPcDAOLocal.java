package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerCustTypeWoutPc;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad HistoDealerCustTypeWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCustTypeWoutPcDAOLocal {

	/**
	 * Metodo:  persiste la información de un HistoDealerCustTypeWoutPc
	 * @param obj objeto que encapsula la información de un HistoDealerCustTypeWoutPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerCustTypeWoutPc
	 * @param obj objeto que encapsula la información de un HistoDealerCustTypeWoutPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerCustTypeWoutPc
	 * @param obj información del HistoDealerCustTypeWoutPc a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerCustTypeWoutPc por su identificador
	 * @param id identificador del HistoDealerCustTypeWoutPc a ser consultado
	 * @return objeto con la información del HistoDealerCustTypeWoutPc dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerCustTypeWoutPc getHistoDealerCustTypeWoutPcByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerCustTypeWoutPc almacenados en la persistencia
	 * @return Lista con los HistoDealerCustTypeWoutPc existentes, una lista vacia en caso que no existan HistoDealerCustTypeWoutPc en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCustTypeWoutPc> getAllHistoDealerCustTypeWoutPcs() throws DAOServiceException, DAOSQLException;


}