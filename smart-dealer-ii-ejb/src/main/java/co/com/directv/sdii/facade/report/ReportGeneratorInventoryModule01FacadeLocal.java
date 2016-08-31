package co.com.directv.sdii.facade.report;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * 
 * Interfaz que expone metodos para generacion de reportes a la capa de presentacion 
 * 
 * Fecha de Creación: 14/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ReportGeneratorInventoryModule01FacadeLocal {
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getMovementQueueHspToIbsByFilter(
			MovCmdQueueFilterDTO filter) throws BusinessException;
	
	/**
	 * Operación encargada de generar el excel de registros de importación
	 * @param modifyImportLogCriteria
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
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

	/**
	 * Operación encargada de generar el excel de lo elementos del ajuste
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @author csierra
	 */	
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException;
	
	/***
	 * Operacion encargada de generar el reporte de la bandeja de ajustes
	 * @param filter, parametros de filtro de la bandeja de ajustes
	 * @return el archivo de excel con los ajustes de la bandeja
	 * @throws BusinessException
	 * @author aharker
	 */
	public FileResponseDTO searchAdjustmentsBySearchParameters(AdjustmentRequestDTO filter) throws BusinessException;
	
}
