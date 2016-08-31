package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CoverageTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad CoverageType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoverageTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto CoverageType
	 * @param obj - CoverageTypeVO  objeto que encapsula la información de un CoverageTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un CoverageType
	 * @param obj - CoverageTypeVO  objeto que encapsula la información de un CoverageTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un CoverageType
	 * @param obj - CoverageTypeVO  información del CoverageTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteCoverageType(CoverageTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un CoverageType por su identificador
	 * @param id - Long identificador del CoverageType a ser consultado
	 * @return objeto con la información del CoverageTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public CoverageTypeVO getCoverageTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los CoverageType almacenados en la persistencia
	 * @return List<CoverageTypeVO> Lista con los CoverageTypeVO existentes, una lista vacia en caso que no existan CoverageTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException;

}
