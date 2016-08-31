package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.CoverageType;
import co.com.directv.sdii.model.vo.CoverageTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad CoverageType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoverageTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto CoverageTypeVO
	 * @param obj objeto que encapsula la información de un CoverageTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un CoverageTypeVO
	 * @param obj objeto que encapsula la información de un CoverageTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un CoverageTypeVO
	 * @param obj información del CoverageTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un CoverageTypeVO por su identificador
	 * @param id identificador del CoverageTypeVO a ser consultado
	 * @return objeto con la información del CoverageTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public CoverageTypeVO getCoverageTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los CoverageTypeVO almacenados en la persistencia
	 * @return Lista con los CoverageTypeVO existentes, una lista vacia en caso que no existan CoverageTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException;
	
	/**
	 * Metodo: Obtiene directamente el objeto que representa el tipo de covertura permanente
	 * @return CoverageType
	 * @throws BusinessException
	 * @author
	 */
	public CoverageTypeVO getCoverageTypePermanent() throws BusinessException;

	/**
	 * Metodo: Obtiene directamente el objeto que representa el tipo de covertura permanente
	 * @return CoverageType
	 * @throws BusinessException
	 * @author
	 */
	public CoverageTypeVO getCoverageTypeOccasional() throws BusinessException;

}
