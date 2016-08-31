package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ReportedElementVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ReportedElement.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReportedElementFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ReportedElement
	 * @param obj - ReportedElementVO  objeto que encapsula la información de un ReportedElementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createReportedElement(ReportedElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ReportedElement
	 * @param obj - ReportedElementVO  objeto que encapsula la información de un ReportedElementVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateReportedElement(ReportedElementVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ReportedElement
	 * @param obj - ReportedElementVO  información del ReportedElementVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteReportedElement(ReportedElementVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ReportedElement por su identificador
	 * @param id - Long identificador del ReportedElement a ser consultado
	 * @return objeto con la información del ReportedElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ReportedElementVO getReportedElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ReportedElement almacenados en la persistencia
	 * @return List<ReportedElementVO> Lista con los ReportedElementVO existentes, una lista vacia en caso que no existan ReportedElementVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<ReportedElementVO> getAllReportedElements() throws BusinessException;

	/**
	 * Metodo: Consulta los elementos reportados como inconsistencia de remisión para una inconsistencia en particular
	 * @param refInconsistencyId identificador de la inconsistencia de remisión
	 * @param incluirSobrantes indica si los resultados deben incluir inconsistencias con cantidades positivas
	 * @param incluirFaltantes indica si los resultados deben incluir inconsistencias con cantidades negativas
	 * @return List<ReportedElementVO>
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws BusinessException;

}
