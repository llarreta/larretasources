package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ReferenceStatus.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceStatusFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ReferenceStatus
	 * @param obj - ReferenceStatusVO  objeto que encapsula la información de un ReferenceStatusVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ReferenceStatus
	 * @author gfandino
	 */
	public void createReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ReferenceStatus
	 * @param obj - ReferenceStatusVO  objeto que encapsula la información de un ReferenceStatusVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ReferenceStatus
	 * @author gfandino
	 */
	public void updateReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ReferenceStatus
	 * @param obj - ReferenceStatusVO  información del ReferenceStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ReferenceStatus
	 * @author gfandino
	 */
	public void deleteReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ReferenceStatus por su identificador
	 * @param id - Long identificador del ReferenceStatus a ser consultado
	 * @return objeto con la información del ReferenceStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ReferenceStatus por ID
	 * @author gfandino
	 */
	public ReferenceStatusVO getReferenceStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ReferenceStatus almacenados en la persistencia
	 * @return List<ReferenceStatusVO> Lista con los ReferenceStatusVO existentes, una lista vacia en caso que no existan ReferenceStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException;

}
