package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.ws.model.dto.ReportedElementForValidationDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad RefInconsistency.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefInconsistencyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto RefInconsistency
	 * @param obj - RefInconsistencyVO  objeto que encapsula la información de un RefInconsistencyVO
	 * @param isFinish Diferencia si se esta guardando inconsistencia o si se esta terminano de registrar inconsistencias
	 * @param isEmpty indica si el objeto viene con o sin valor
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author wjimenez
	 */
	public void saveRefInconsistency(RefInconsistencyVO refInconsistency,List<ReportedElementVO> reportedElements, Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un RefInconsistency
	 * @param obj - RefInconsistencyVO  objeto que encapsula la información de un RefInconsistencyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateRefInconsistency(RefInconsistencyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un RefInconsistency
	 * @param obj - RefInconsistencyVO  información del RefInconsistencyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteRefInconsistency(RefInconsistencyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un RefInconsistency por su identificador
	 * @param id - Long identificador del RefInconsistency a ser consultado
	 * @return objeto con la información del RefInconsistencyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public RefInconsistencyVO getRefInconsistencyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los RefInconsistency almacenados en la persistencia
	 * @return List<RefInconsistencyVO> Lista con los RefInconsistencyVO existentes, una lista vacia en caso que no existan RefInconsistencyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<RefInconsistencyVO> getAllRefInconsistencys() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO almacenados en la persistencia por remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> correspondiente a la remisión especificada. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de RefInconsistency por remisión
	 * @author gfandino
	 */
	public List<RefInconsistencyVO> getRefInconsistencysByReferenceID(Long refID)throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO con estado abierto almacenados en la persistencia por remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> con estado abierto correspondiente a la remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	public RefInconsistencyResponse getReferenceInconsistenciesOpenedByReferenceId(
			Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO con estado cerrado almacenados en la persistencia por remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> con estado cerrado correspondiente a la remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */	
	public RefInconsistencyResponse getReferenceInconsistenciesClosedByReferenceId(
			Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: valida que los elementos de una inconsistencia de remisión con menos elementos físicos, tengan una cantidad
	 * adecuada de acuerdo a las existencias en la remisión
	 * @param elementsToValidate
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public void validateReportedElementsForLessElementsInRefInc(
			List<ReportedElementForValidationDTO> elementsToValidate)
			throws BusinessException;
	
	/**
	 * Metodo: retorna las remisiones que se han generado por cierre de inconsistencias
	 * de menos elementos físicos
	 * @param refIncId
	 * @return
	 * @throws DAOServiceException En caso de error en la consulta
	 * @throws DAOSQLException En caso de error en la consulta
	 * @author wjimenez
	 */
	public List<ReferenceVO> getGeneratedReferencesByRefIncId(Long refIncId)
			throws BusinessException;
	
}
