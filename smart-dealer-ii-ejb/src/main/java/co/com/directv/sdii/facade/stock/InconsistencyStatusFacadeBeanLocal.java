package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.InconsistencyStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad InconsistencyStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface InconsistencyStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto InconsistencyStatus
	 * @param obj - InconsistencyStatusVO  objeto que encapsula la información de un InconsistencyStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un InconsistencyStatus
	 * @param obj - InconsistencyStatusVO  objeto que encapsula la información de un InconsistencyStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un InconsistencyStatus
	 * @param obj - InconsistencyStatusVO  información del InconsistencyStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteInconsistencyStatus(InconsistencyStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un InconsistencyStatus por su identificador
	 * @param id - Long identificador del InconsistencyStatus a ser consultado
	 * @return objeto con la información del InconsistencyStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public InconsistencyStatusVO getInconsistencyStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los InconsistencyStatus almacenados en la persistencia
	 * @return List<InconsistencyStatusVO> Lista con los InconsistencyStatusVO existentes, una lista vacia en caso que no existan InconsistencyStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<InconsistencyStatusVO> getAllInconsistencyStatuss() throws BusinessException;

}
