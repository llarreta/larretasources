package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ReferenceModification.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceModificationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ReferenceModificationVO
	 * @param obj objeto que encapsula la información de un ReferenceModificationVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ReferenceModification
	 * @author gfandino
	 */
	public void createReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ReferenceModificationVO
	 * @param obj objeto que encapsula la información de un ReferenceModificationVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ReferenceModification
	 * @author gfandino
	 */
	public void updateReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ReferenceModificationVO
	 * @param obj información del ReferenceModificationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ReferenceModification
	 * @author gfandino
	 */
	public void deleteReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ReferenceModificationVO por su identificador
	 * @param id identificador del ReferenceModificationVO a ser consultado
	 * @return objeto con la información del ReferenceModificationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ReferenceModification por ID
	 * @author gfandino
	 */
	public ReferenceModificationVO getReferenceModificationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ReferenceModificationVO almacenados en la persistencia
	 * @return Lista con los ReferenceModificationVO existentes, una lista vacia en caso que no existan ReferenceModificationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ReferenceModification
	 * @author gfandino
	 */
	public List<ReferenceModificationVO> getAllReferenceModifications() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceModificationVO almacenados en la persistencia asosiada a la remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<ReferenceModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los ReferenceModification por remisión
	 * @author gfandino
	 */
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(Long refID)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceModificationVO almacenados en la persistencia asosiada a la remisión
	 * incluyendo el usuario que hizo la modificacion
	 * @param refID - Long identificador de la remisión
	 * @return List<ReferenceModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los ReferenceModification por remisión
	 * @author jnova
	 */
	public List<ReferenceModificationVO> getReferenceModificationsAndUsersModificationsByReferenceID(Long refID)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ReferenceModificationVO almacenados en la persistencia asosiada a la remisión
	 * incluyendo el usuario que hizo la modificacion enviando datos basicos para consulta de detalles CU 34
	 * @param refID - Long identificador de la remisión
	 * @return List<ReferenceModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los ReferenceModification por remisión
	 * @author jnova
	 */
	public List<ReferenceModificationVO> getReferenceModificationsAndUsersModificationsForDetailsByReferenceID(Long refID)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea un registro en las modificaciones de remisiones por identificador de remision y codigo de modificacion
	 * @param refId identificador de remision
	 * @param refModificationCode codigo de mofificacion que se realiza
	 * @param userId usuario que realiza la modificacion
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void createReferenceModification(Long refId , String refModificationCode,Long userId)throws BusinessException;

}
