package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad AdjustmentElementsStatus
 * 
 * Fecha de Creación: Mar 16, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentElementsStatusDAOLocal {

	/**
	 * Metodo:  persiste la información de un AdjustmentElementsStatus
	 * @param obj objeto que encapsula la información de un AdjustmentElementsStatus
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 */
	public void createAdjustmentElementsStatus(AdjustmentElementsStatus obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentElementsStatus por identificador
	 * @param id identificador del AdjustmentElementsStatus a ser consultado
	 * @return objeto con la información del AdjustmentElementsStatus dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentElementsStatus getAdjustmentElementsStatusByID(Long id) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la información de un AdjustmentElementsStatus por codigo
	 * @param code codigo del AdjustmentElementsStatus a ser consultado
	 * @return objeto con la información del AdjustmentElementsStatus dado su codigo
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentElementsStatus getAdjustmentElementsStatusByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public AdjustmentElementsStatus getAdjustmentElementsStatusByCodeMassive(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentElementsStatus
	 * @return Lista con los AdjustmentElementsStatus existentes, una lista vacia en caso que no existan AdjustmentElementsStatus en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
    public List<AdjustmentElementsStatus> getAllAdjustmentElementsStatus() throws DAOServiceException, DAOSQLException;
	
    public AdjustmentElementsStatus getAdjustmentElementsStatusByCodeRequired(String code) throws DAOServiceException, DAOSQLException;
}