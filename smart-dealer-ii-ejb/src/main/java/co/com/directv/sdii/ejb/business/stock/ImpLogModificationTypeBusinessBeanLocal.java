package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ImpLogModificationType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogModificationTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ImpLogModificationTypeVO
	 * @param obj objeto que encapsula la información de un ImpLogModificationTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ImpLogModificationTypeVO
	 * @param obj objeto que encapsula la información de un ImpLogModificationTypeVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImpLogModificationTypeVO
	 * @param obj información del ImpLogModificationTypeVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ImpLogModificationTypeVO por su identificador
	 * @param id identificador del ImpLogModificationTypeVO a ser consultado
	 * @return objeto con la información del ImpLogModificationTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ImpLogModificationTypeVO getImpLogModificationTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImpLogModificationTypeVO almacenados en la persistencia
	 * @return Lista con los ImpLogModificationTypeVO existentes, una lista vacia en caso que no existan ImpLogModificationTypeVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los ImpLogModificationTypeVO almacenados en la persistencia
	 * @return Lista con los ImpLogModificationTypeVO existentes, una lista vacia en caso que no existan ImpLogModificationTypeVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene un tipo de modificación de import log por el código
	 * @param code código del tipo de modificación a ser consultado
	 * @return tipo de modificación de import log, nulo en caso que no se encuentre
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(String code)throws BusinessException;

	
}
