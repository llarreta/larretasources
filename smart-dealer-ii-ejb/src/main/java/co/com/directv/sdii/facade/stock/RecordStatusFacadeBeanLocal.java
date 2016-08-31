package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.RecordStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad RecordStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RecordStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto RecordStatus
	 * @param obj - RecordStatusVO  objeto que encapsula la información de un RecordStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un RecordStatus
	 * @param obj - RecordStatusVO  objeto que encapsula la información de un RecordStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un RecordStatus
	 * @param obj - RecordStatusVO  información del RecordStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteRecordStatus(RecordStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un RecordStatus por su identificador
	 * @param id - Long identificador del RecordStatus a ser consultado
	 * @return objeto con la información del RecordStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public RecordStatusVO getRecordStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los RecordStatus almacenados en la persistencia
	 * @return List<RecordStatusVO> Lista con los RecordStatusVO existentes, una lista vacia en caso que no existan RecordStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<RecordStatusVO> getAllRecordStatuss() throws BusinessException;

}
