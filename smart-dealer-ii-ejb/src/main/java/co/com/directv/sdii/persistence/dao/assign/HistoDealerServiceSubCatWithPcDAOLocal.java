package co.com.directv.sdii.persistence.dao.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.HistoDealerServiceSubCatWithPc;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad HistoDealerServiceSubCatWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerServiceSubCatWithPcDAOLocal {

	/**
	 * Metodo:  persiste la información de un HistoDealerServiceSubCatWithPc
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCatWithPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerServiceSubCatWithPc
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCatWithPc
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerServiceSubCatWithPc
	 * @param obj información del HistoDealerServiceSubCatWithPc a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPc obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerServiceSubCatWithPc por su identificador
	 * @param id identificador del HistoDealerServiceSubCatWithPc a ser consultado
	 * @return objeto con la información del HistoDealerServiceSubCatWithPc dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerServiceSubCatWithPc getHistoDealerServiceSubCatWithPcByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerServiceSubCatWithPc almacenados en la persistencia
	 * @return Lista con los HistoDealerServiceSubCatWithPc existentes, una lista vacia en caso que no existan HistoDealerServiceSubCatWithPc en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerServiceSubCatWithPc> getAllHistoDealerServiceSubCatWithPcs() throws DAOServiceException, DAOSQLException;


}