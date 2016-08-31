package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.NotSerPartialRetirementIdVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad NotSerPartialRetirementId.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface NotSerPartialRetirementIdFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto NotSerPartialRetirementId
	 * @param obj - NotSerPartialRetirementIdVO  objeto que encapsula la información de un NotSerPartialRetirementIdVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un NotSerPartialRetirementId
	 * @param obj - NotSerPartialRetirementIdVO  objeto que encapsula la información de un NotSerPartialRetirementIdVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un NotSerPartialRetirementId
	 * @param obj - NotSerPartialRetirementIdVO  información del NotSerPartialRetirementIdVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteNotSerPartialRetirementId(NotSerPartialRetirementIdVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un NotSerPartialRetirementId por su identificador
	 * @param id - Long identificador del NotSerPartialRetirementId a ser consultado
	 * @return objeto con la información del NotSerPartialRetirementIdVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public NotSerPartialRetirementIdVO getNotSerPartialRetirementIdByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los NotSerPartialRetirementId almacenados en la persistencia
	 * @return List<NotSerPartialRetirementIdVO> Lista con los NotSerPartialRetirementIdVO existentes, una lista vacia en caso que no existan NotSerPartialRetirementIdVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<NotSerPartialRetirementIdVO> getAllNotSerPartialRetirementIds() throws BusinessException;

}
