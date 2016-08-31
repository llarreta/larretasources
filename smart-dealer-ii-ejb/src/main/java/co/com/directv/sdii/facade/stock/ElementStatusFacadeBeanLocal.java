package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ElementStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ElementStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ElementStatus
	 * @param obj - ElementStatusVO  objeto que encapsula la información de un ElementStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createElementStatus(ElementStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ElementStatus
	 * @param obj - ElementStatusVO  objeto que encapsula la información de un ElementStatusVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateElementStatus(ElementStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ElementStatus
	 * @param obj - ElementStatusVO  información del ElementStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteElementStatus(ElementStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ElementStatus por su identificador
	 * @param id - Long identificador del ElementStatus a ser consultado
	 * @return objeto con la información del ElementStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ElementStatusVO getElementStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ElementStatus almacenados en la persistencia
	 * @return List<ElementStatusVO> Lista con los ElementStatusVO existentes, una lista vacia en caso que no existan ElementStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ElementStatusVO> getAllElementStatuss() throws BusinessException;

}
