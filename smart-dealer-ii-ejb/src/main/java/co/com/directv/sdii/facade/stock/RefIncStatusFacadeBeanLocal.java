package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.RefIncStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad RefIncStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefIncStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto RefIncStatus
	 * @param obj - RefIncStatusVO  objeto que encapsula la información de un RefIncStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un RefIncStatus
	 * @param obj - RefIncStatusVO  objeto que encapsula la información de un RefIncStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un RefIncStatus
	 * @param obj - RefIncStatusVO  información del RefIncStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteRefIncStatus(RefIncStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un RefIncStatus por su identificador
	 * @param id - Long identificador del RefIncStatus a ser consultado
	 * @return objeto con la información del RefIncStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public RefIncStatusVO getRefIncStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los RefIncStatus almacenados en la persistencia
	 * @return List<RefIncStatusVO> Lista con los RefIncStatusVO existentes, una lista vacia en caso que no existan RefIncStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException;

}
