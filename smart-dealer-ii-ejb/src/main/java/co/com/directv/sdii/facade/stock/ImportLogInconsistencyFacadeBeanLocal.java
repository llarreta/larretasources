package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImportLogInconsistency.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImportLogInconsistencyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ImportLogInconsistency
	 * @param obj - ImportLogInconsistencyVO  objeto que encapsula la información de un ImportLogInconsistencyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ImportLogInconsistency
	 * @param obj - ImportLogInconsistencyVO  objeto que encapsula la información de un ImportLogInconsistencyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ImportLogInconsistency
	 * @param obj - ImportLogInconsistencyVO  información del ImportLogInconsistencyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteImportLogInconsistency(ImportLogInconsistencyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ImportLogInconsistency por su identificador
	 * @param id - Long identificador del ImportLogInconsistency a ser consultado
	 * @return objeto con la información del ImportLogInconsistencyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ImportLogInconsistencyVO getImportLogInconsistencyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ImportLogInconsistency almacenados en la persistencia
	 * @return List<ImportLogInconsistencyVO> Lista con los ImportLogInconsistencyVO existentes, una lista vacia en caso que no existan ImportLogInconsistencyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ImportLogInconsistencyVO> getAllImportLogInconsistencys() throws BusinessException;
	
}
