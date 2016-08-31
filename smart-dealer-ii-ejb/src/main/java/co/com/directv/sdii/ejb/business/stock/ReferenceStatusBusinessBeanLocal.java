package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ReferenceStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ReferenceStatusVO
	 * @param obj objeto que encapsula la información de un ReferenceStatusVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ReferenceStatus
	 * @author gfandino
	 */
	public void createReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceStatusVO
	 * @param obj objeto que encapsula la información de un ReferenceStatusVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ReferenceStatus
	 * @author gfandino
	 */
	public void updateReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceStatusVO
	 * @param obj información del ReferenceStatusVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ReferenceStatus
	 * @author gfandino
	 */
	public void deleteReferenceStatus(ReferenceStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ReferenceStatusVO por su identificador
	 * @param id identificador del ReferenceStatusVO a ser consultado
	 * @return objeto con la información del ReferenceStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ReferenceStatus por ID
	 * @author gfandino
	 */
	public ReferenceStatusVO getReferenceStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceStatusVO almacenados en la persistencia
	 * @return Lista con los ReferenceStatusVO existentes, una lista vacia en caso que no existan ReferenceStatusVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ReferenceStatus
	 * @author gfandino
	 */
	public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException;
	
	/**
	 * Método: Obtiene la información de la ReferenceStatusVO por su código
	 * @param code - String código del estado de la remisión
	 * @return ReferenceStatusVO estado de la remisión
	 * @throws BusinessException en caso de error al ejecutar la consulta de ReferenceStatus por código
	 */
	public ReferenceStatusVO getReferenceStatusByCode(String code)throws BusinessException;

}
