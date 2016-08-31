package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad InconsistencyStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto InconsistencyStatusVO
	 * @param obj objeto que encapsula la información de un InconsistencyStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un InconsistencyStatusVO
	 * @param obj objeto que encapsula la información de un InconsistencyStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un InconsistencyStatusVO
	 * @param obj información del InconsistencyStatusVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un InconsistencyStatusVO por su identificador
	 * @param id identificador del InconsistencyStatusVO a ser consultado
	 * @return objeto con la información del InconsistencyStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public InconsistencyStatusVO getInconsistencyStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los InconsistencyStatusVO almacenados en la persistencia
	 * @return Lista con los InconsistencyStatusVO existentes, una lista vacia en caso que no existan InconsistencyStatusVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException;

}
