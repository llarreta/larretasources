package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ImpLogConfirmation.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ImpLogConfirmationFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ImpLogConfirmation
	 * @param obj - ImpLogConfirmationVO  objeto que encapsula la información de un ImpLogConfirmationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jalopez
	 */
	public void createImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ImpLogConfirmation
	 * @param obj - ImpLogConfirmationVO  objeto que encapsula la información de un ImpLogConfirmationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ImpLogConfirmation
	 * @param obj - ImpLogConfirmationVO  información del ImpLogConfirmationVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ImpLogConfirmation por su identificador
	 * @param id - Long identificador del ImpLogConfirmation a ser consultado
	 * @return objeto con la información del ImpLogConfirmationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ImpLogConfirmationVO getImpLogConfirmationByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ImpLogConfirmation almacenados en la persistencia
	 * @return List<ImpLogConfirmationVO> Lista con los ImpLogConfirmationVO existentes, una lista vacia en caso que no existan ImpLogConfirmationVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ImpLogConfirmationVO> getAllImpLogConfirmations() throws BusinessException;

}
