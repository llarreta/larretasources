package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ImportLogStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ImportLogStatusVO
	 * @param obj objeto que encapsula la información de un ImportLogStatusVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ImportLogStatus
	 * @author gfandino
	 */
	public void createImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ImportLogStatusVO
	 * @param obj objeto que encapsula la información de un ImportLogStatusVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de ImportLogStatus
	 * @author gfandino
	 */
	public void updateImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ImportLogStatusVO
	 * @param obj información del ImportLogStatusVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de ImportLogStatus
	 * @author gfandino
	 */
	public void deleteImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ImportLogStatusVO por su identificador
	 * @param id identificador del ImportLogStatusVO a ser consultado
	 * @return objeto con la información del ImportLogStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por ID
	 * @author gfandino
	 */
	public ImportLogStatusVO getImportLogStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ImportLogStatusVO almacenados en la persistencia
	 * @return Lista con los ImportLogStatusVO existentes, una lista vacia en caso que no existan ImportLogStatusVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ImportLogStatus
	 * @author gfandino
	 */
	public List<ImportLogStatusVO> getAllImportLogStatuss() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ImportLogStatusVO por su código
	 * @param code - String código del ImportLogStatusVO a ser consultado
	 * @return ImportLogStatusVO objeto con la información del ImportLogStatusVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ImportLogStatus por Código
	 * @author gfandino
	 */
	public ImportLogStatusVO getImportLogStatusByCode(String code) throws BusinessException;

}
