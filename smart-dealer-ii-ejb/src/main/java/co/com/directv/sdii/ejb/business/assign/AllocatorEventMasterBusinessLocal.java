/**
 * Creado 2/11/2010 14:18:23
 */
package co.com.directv.sdii.ejb.business.assign;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllocatorEventMaster;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;

/**
 * Encapsula la lógica encargada de registrar los resultados la asignacion de la Work Order
 * 
 * Fecha de Creación: 29/03/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface AllocatorEventMasterBusinessLocal {

	
	/**
	 * Registra la información del cargue de una Work order
	 * 
	 * @param allocatorEventMaster información del cargue a ser generada
	 * @return
	 * @throws BusinessException
	 */
	public AllocatorEventMasterVO createAllocatorEventMaster(AllocatorEventMasterVO allocatorEventMaster) throws BusinessException;
	
	/**
	 * Registra la información del cargue de una Work order
	 * @param woCount número de work orders a ser cargadas
	 * @param countryId id del pais del evento de asignador que se corrio
	 * @return Objeto persistido con la información del cargue
	 * @throws BusinessException en caso de error en la creación
	 */
	public AllocatorEventMasterVO createAllocatorEventMaster(Long woCount,Long countryId) throws BusinessException;
	
	
	/**
	 * Actualiza la información del cargue de una Work order
	 * 
	 * @param allocatorEventMaster información del cargue a ser generada
	 * @return
	 * @throws BusinessException
	 */
	public AllocatorEventMasterVO updateAllocatorEventMaster(AllocatorEventMasterVO allocatorEventMaster) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene el ultimo allocator event master
	 * @param countryId
	 * @return allocatorEventMaster
	 * @throws BusinessException
	 * @author 
	 */
	 public AllocatorEventMaster getLastAllocatorEventMaster(Long countryId) throws BusinessException;
	 
	 /**
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public Long getMaxAllocatorEventMaster() throws BusinessException;
	 
}