
package co.com.directv.sdii.ws.business.report;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

@WebService(name="ReportGeneratorInventoryModule01WS",targetNamespace="http://report.business.ws.sdii.directv.com.co/")
public interface IReportGeneratorInventoryModule01WS {
	
	/**
	 * Metodo: Consulta los movimientos de inventarios generados entre IBS Y HSP
	 * @param filter
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName = "getMovementQueueHspToIbsByFilter", action = "getMovementQueueHspToIbsByFilter")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getMovementQueueHspToIbsByFilter(@WebParam( name = "filter")MovCmdQueueFilterDTO filter) throws BusinessException;
	
	/**
	 * Operación encargada de generar el excel de registros de importación
	 * @param modifyImportLogCriteria
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName = "getImportLogByCriteria", action = "getImportLogByCriteria")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getImportLogByCriteria(
			@WebParam( name = "filter")ModifyImportLogDTO modifyImportLogCriteria, @WebParam( name = "userId")Long userId) throws BusinessException;
	
	/**
	 * Operación encargada de generar el excel de lo elementos
	 * serializados del registros de importación
	 * @param filterImportLogElements
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName = "getImportLogItemsByImportLogIdAndIsSerialized", action = "getImportLogItemsByImportLogIdAndIsSerialized")
	@XmlMimeType("application/octet-stream")
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
	@WebMethod(operationName = "getImportLogItemsByImportLogIdAndIsNotSerialized", action = "getImportLogItemsByImportLogIdAndIsNotSerialized")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getImportLogItemsByImportLogIdAndIsNotSerialized(
			ImportLogElementsFilterDTO filterImportLogElements) throws BusinessException;
	
	/***
	 * 
	 * @param filter filtros de la bandeja de ajustes
	 * @return archivo de reporte de la bandeja de ajustes
	 * @throws BusinessException
	 * @author aharker
	 */
	@WebMethod(operationName = "searchAdjustmentsBySearchParameters", action = "searchAdjustmentsBySearchParameters")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO searchAdjustmentsBySearchParameters(@WebParam( name = "filter") AdjustmentRequestDTO filter) throws BusinessException ;
	
	/**
	 * Operación encargada de generar el excel de lo elementos del ajuste 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @author csierra
	 */
	@WebMethod(operationName = "getAdjustmentElementsForAuthorization", action = "getAdjustmentElementsForAuthorization")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException;
	
}
