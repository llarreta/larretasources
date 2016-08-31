package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AdjustmentModificationVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad AdjustmentModification.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentModificationBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto AdjustmentModificationVO
	 * @param obj objeto que encapsula la información de un AdjustmentModificationVO
	 * @throws BusinessException en caso de error al ejecutar la creación de AdjustmentModification
	 * @author cduarte
	 */
	public void createAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un AdjustmentModificationVO
	 * @param obj objeto que encapsula la información de un AdjustmentModificationVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de AdjustmentModification
	 * @author cduarte
	 */
	public void updateAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AdjustmentModificationVO
	 * @param obj información del AdjustmentModificationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de AdjustmentModification
	 * @author cduarte
	 */
	public void deleteAdjustmentModification(AdjustmentModificationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentModificationVO por su identificador
	 * @param id identificador del AdjustmentModificationVO a ser consultado
	 * @return objeto con la información del AdjustmentModificationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de AdjustmentModification por ID
	 * @author cduarte
	 */
	public AdjustmentModificationVO getAdjustmentModificationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentModificationVO almacenados en la persistencia
	 * @return Lista con los AdjustmentModificationVO existentes, una lista vacia en caso que no existan AdjustmentModificationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los AdjustmentModification
	 * @author cduarte
	 */
	public List<AdjustmentModificationVO> getAllAdjustmentModifications() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los AdjustmentModificationVO almacenados en la persistencia asosiada a la remisión
	 * @param adjID - Long identificador de la remisión
	 * @return List<AdjustmentModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los AdjustmentModification por remisión
	 * @author cduarte
	 */
	public List<AdjustmentModificationVO> getAdjustmentModificationsByAdjustmentID(Long adjID)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los AdjustmentModificationVO almacenados en la persistencia asosiada a la remisión
	 * incluyendo el usuario que hizo la modificacion
	 * @param adjID - Long identificador de la remisión
	 * @return List<AdjustmentModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los AdjustmentModification por remisión
	 * @author cduarte
	 */
	public List<AdjustmentModificationVO> getAdjustmentModificationsAndUsersModificationsByAdjustmentID(Long adjID)throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los AdjustmentModificationVO almacenados en la persistencia asosiada a la remisión
	 * incluyendo el usuario que hizo la modificacion enviando datos basicos para consulta de detalles CU 34
	 * @param adjID - Long identificador de la remisión
	 * @return List<AdjustmentModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los AdjustmentModification por remisión
	 * @author cduarte
	 */
	public List<AdjustmentModificationVO> getAdjustmentModificationsAndUsersModificationsForDetailsByAdjustmentID(Long adjID)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Crea un registro en las modificaciones de remisiones por identificador de remision y codigo de modificacion
	 * @param adjId identificador de remision
	 * @param adjModificationCode codigo de mofificacion que se realiza
	 * @param userId usuario que realiza la modificacion
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void createAdjustmentModification(Long adjId , String adjModificationCode,Long userId)throws BusinessException;

}
