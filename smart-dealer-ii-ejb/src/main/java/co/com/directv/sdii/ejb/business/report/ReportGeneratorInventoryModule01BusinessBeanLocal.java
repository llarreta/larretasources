package co.com.directv.sdii.ejb.business.report;
import javax.ejb.Local;

import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * 
 * Interfaz para metodos de negocio con el fin de generar reportes 
 * 
 * Fecha de Creación: 14/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReportGeneratorInventoryModule01BusinessBeanLocal {
	
	public FileResponseDTO getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO filter) throws BusinessException;
	
	public FileResponseDTO getImportLogByCriteria(
			ModifyImportLogDTO modifyImportLogCriteria, Long userId) throws BusinessException;
	
	/**
	 * Operación encargada de generar el excel de lo elementos
	 * serializados del registros de importación
	 * @param filterImportLogElements
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsSerialized(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException;
	

	/**
	 * Operación encargada de generar el excel de lo elementos
	 * no serializados del registros de importación
	 * @param filterImportLogElements
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsNotSerialized(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException;
	
	public FileResponseDTO searchAdjustmentsBySearchParameters(AdjustmentRequestDTO filter) throws BusinessException ;

	/**
	 * Operación encargada de generar el excel de lo elementos del ajuste
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @author csierra
	 */	
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException;
	
}
