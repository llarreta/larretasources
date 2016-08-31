package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ReferenceModification.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReferenceModificationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ReferenceModification
	 * @param obj - ReferenceModificationVO  objeto que encapsula la información de un ReferenceModificationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ReferenceModification
	 * @param obj - ReferenceModificationVO  objeto que encapsula la información de un ReferenceModificationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ReferenceModification
	 * @param obj - ReferenceModificationVO  información del ReferenceModificationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteReferenceModification(ReferenceModificationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ReferenceModification por su identificador
	 * @param id - Long identificador del ReferenceModification a ser consultado
	 * @return objeto con la información del ReferenceModificationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ReferenceModificationVO getReferenceModificationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ReferenceModification almacenados en la persistencia
	 * @return List<ReferenceModificationVO> Lista con los ReferenceModificationVO existentes, una lista vacia en caso que no existan ReferenceModificationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
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

}
