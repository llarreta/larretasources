package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImpLogModification.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogModificationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ImpLogModification
	 * @param obj - ImpLogModificationVO  objeto que encapsula la información de un ImpLogModificationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createImpLogModification(ImpLogModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ImpLogModification
	 * @param obj - ImpLogModificationVO  objeto que encapsula la información de un ImpLogModificationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogModification(ImpLogModificationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ImpLogModification
	 * @param obj - ImpLogModificationVO  información del ImpLogModificationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteImpLogModification(ImpLogModificationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ImpLogModification por su identificador
	 * @param id - Long identificador del ImpLogModification a ser consultado
	 * @return objeto con la información del ImpLogModificationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ImpLogModificationVO getImpLogModificationByID(Long id) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Permite consultar la información de un ImpLogModification por el identificador del registro de importacion.
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ImpLogModificationVO> getImpLogModificationByImportLogID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ImpLogModification almacenados en la persistencia
	 * @return List<ImpLogModificationVO> Lista con los ImpLogModificationVO existentes, una lista vacia en caso que no existan ImpLogModificationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ImpLogModificationVO> getAllImpLogModifications() throws BusinessException;

	

}
