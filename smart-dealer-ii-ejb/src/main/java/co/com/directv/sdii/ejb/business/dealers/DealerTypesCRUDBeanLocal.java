package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.DealerTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerTypesCRUDBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerTypeVO
	 * @param obj objeto que encapsula la información de un DealerTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerTypeVO
	 * @param obj objeto que encapsula la información de un DealerTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerTypeVO
	 * @param obj información del DealerTypeVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public DealerTypeVO getDealerTypesByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un DealerTypeVO por su identificador
	 * @param id identificador del DealerTypeVO a ser consultado
	 * @return objeto con la información del DealerTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerTypeVO getDealerTypesByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerTypeVO almacenados en la persistencia
	 * @return Lista con los DealerTypeVO existentes, una lista vacia en caso que no existan DealerTypeVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerTypeVO> getAllDealerTypes() throws BusinessException;

	/**
	 * Metodo: <Descripcion>
	 * @param isAlloc
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<DealerTypeVO> getDealerTypesByIsAlloc(String isAlloc)throws BusinessException;
}
