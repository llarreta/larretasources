package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImportLogStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImportLogStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ImportLogStatus
	 * @param obj - ImportLogStatusVO  objeto que encapsula la información de un ImportLogStatusVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ImportLogStatus
	 * @author gfandino
	 */
	public void createImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ImportLogStatus
	 * @param obj - ImportLogStatusVO  objeto que encapsula la información de un ImportLogStatusVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ImportLogStatus
	 * @author gfandino
	 */
	public void updateImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ImportLogStatus
	 * @param obj - ImportLogStatusVO  información del ImportLogStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ImportLogStatus
	 * @author gfandino
	 */
	public void deleteImportLogStatus(ImportLogStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ImportLogStatus por su identificador
	 * @param id - Long identificador del ImportLogStatus a ser consultado
	 * @return objeto con la información del ImportLogStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ImportLogStatus por ID
	 * @author gfandino
	 */
	public ImportLogStatusVO getImportLogStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ImportLogStatus almacenados en la persistencia
	 * @return List<ImportLogStatusVO> Lista con los ImportLogStatusVO existentes, una lista vacia en caso que no existan ImportLogStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ImportLogStatus
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
