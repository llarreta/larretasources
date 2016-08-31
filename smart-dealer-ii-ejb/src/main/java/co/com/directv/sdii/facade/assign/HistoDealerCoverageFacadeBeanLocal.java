package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerCoverageVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerCoverage.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCoverageFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerCoverage
	 * @param obj - HistoDealerCoverageVO  objeto que encapsula la información de un HistoDealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerCoverage
	 * @param obj - HistoDealerCoverageVO  objeto que encapsula la información de un HistoDealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerCoverage
	 * @param obj - HistoDealerCoverageVO  información del HistoDealerCoverageVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerCoverage por su identificador
	 * @param id - Long identificador del HistoDealerCoverage a ser consultado
	 * @return objeto con la información del HistoDealerCoverageVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerCoverageVO getHistoDealerCoverageByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerCoverage almacenados en la persistencia
	 * @return List<HistoDealerCoverageVO> Lista con los HistoDealerCoverageVO existentes, una lista vacia en caso que no existan HistoDealerCoverageVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCoverageVO> getAllHistoDealerCoverages() throws BusinessException;

}
