package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerCoverageVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerCoverage.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCoverageBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerCoverageVO
	 * @param obj objeto que encapsula la información de un HistoDealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerCoverageVO
	 * @param obj objeto que encapsula la información de un HistoDealerCoverageVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerCoverageVO
	 * @param obj información del HistoDealerCoverageVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerCoverageVO por su identificador
	 * @param id identificador del HistoDealerCoverageVO a ser consultado
	 * @return objeto con la información del HistoDealerCoverageVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerCoverageVO getHistoDealerCoverageByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerCoverageVO almacenados en la persistencia
	 * @return Lista con los HistoDealerCoverageVO existentes, una lista vacia en caso que no existan HistoDealerCoverageVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCoverageVO> getAllHistoDealerCoverages() throws BusinessException;

}
