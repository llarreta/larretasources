package co.com.directv.sdii.ws.business.report;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderCanceledFilterDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@WebService(name="ReportGeneratorWS",targetNamespace="http://report.business.ws.sdii.directv.com.co/")
public interface IReportGeneratorWS {
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForReport", action = "getWorkOrdersForReport")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReport(@WebParam( name = "filter")WorkOrderFilterTrayDTO filter)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author aquevedo
	 */
	@WebMethod(operationName = "getWorkOrdersForReportAttentionFinalization", action = "getWorkOrdersForReportAttentionFinalization")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(@WebParam( name = "filter")WorkOrderFilterTrayDTO filter)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author aquevedo
	 */
	@WebMethod(operationName = "getWorkOrdersForReportForAllocator", action = "getWorkOrdersForReportForAllocator")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReportForAllocator(@WebParam( name = "filter")WorkOrderFilterTrayDTO filter)throws BusinessException;
	
	
	/**
	 * 
	 * Metodo: Consulta las WO a partir de la lista de codigos enviados y retorna un archivo de excel con los resultados
	 * @param woCodes Lista de WO que se van a incluir en el archivo
	 * @param countryId Pais al que pertenecen las WO
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForReportForGroupAction", action = "getWorkOrdersForReportForGroupAction")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReportForGroupAction(@WebParam( name = "woCodes") List<String> woCodes, @WebParam( name = "countryId") Long countryId, @WebParam( name = "userId") Long userId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los medio de contacto del cliente asociado a una WO para generar el excel
	 * @param customerCode Codigo del cliente del cual se desean obtener los medios de contacto
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getCustomerMediaContacts", action = "getCustomerMediaContacts")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getCustomerMediaContacts(@WebParam( name = "customerCode")String customerCode)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los servicios asociados a una WO para generar excel
	 * @param woId Identificador de la WO
	 * @returnFileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrderServices", action = "getWorkOrderServices")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrderServices(@WebParam( name = "woId")Long woId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una shippingOrder para generar excel
	 * @param soId Identificador de la shippingOrder de la cual se desea obtener los elementos
	  * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getShippingOrderElements", action = "getShippingOrderElements")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getShippingOrderElements(@WebParam( name = "soId")Long soId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los medios de contactos de un dealer por codigo
	 * @param dealerCode Codigo de dealer
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getDealerMediaContacts", action = "getDealerMediaContacts")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getDealerMediaContacts(@WebParam( name = "dealerCode")Long dealerCode)throws BusinessException;
	
	/**
	 *  
	 * Metodo: Consulta los contacts de una WO para generar excel
	 * @param woCode Codigo de WO
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrderContacts", action = "getWorkOrderContacts")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrderContacts(@WebParam( name = "woCode")String woCode)throws BusinessException;
	
	/**
	 * Metodo: Genera un reporte a partir de los parámetros genéricos
	 * @param cmd nombre del comando que genera el reporte
	 * @param args argumentos que recibe el comando
	 * @param fileName nombre del archivo a ser generado
	 * @param reportExtension extensión del archivo a ser generado "xls" o "pdf"
	 * @return Respuesta que encapsula el reporte
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "generateReport", action = "generateReport")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO generateReport(@WebParam( name = "cmd") String cmd, @WebParam( name = "args") String args, @WebParam( name = "fileName") String fileName, @WebParam( name = "reportExtension") String reportExtension) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera el pdf de las workorders enviadas por parametro
	 * @param workOrderCodes Ids de las workorders a las cuales se les va a generar el pdf
	 * @param countryId identificador del pais de las workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "generateWorkOrderPDF", action = "generateWorkOrderPDF")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO generateWorkOrderPDF(@WebParam( name = "workOrderCodes") List<String> workOrderCodes,@WebParam( name = "countryId")  Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera el archivo de excel para la bandeja de remision
	 * @param ReferencesFilterDTO dto que contiene filtro para generar excel de remisiones
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getReferencesByFilterForExcel", action = "getReferencesByFilterForExcel")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getReferencesByFilterForExcel(@WebParam( name = "referenceDTO") ReferencesFilterDTO referenceDTO) throws BusinessException;
	
	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param String transferReasonName
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova  
	 */
	@WebMethod(operationName = "getTransferReasonByFilter", action = "getTransferReasonByFilter")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getTransferReasonByFilter(@WebParam( name = "transferReasonName")String transferReasonName) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta todas las bodegas en cualquier estado para generar el reporte en excel
	 * @param countryId
	 * @param code
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getAllWarehousesByCountryId", action = "getAllWarehousesByCountryId")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getAllWarehousesByCountryId(@WebParam( name = "countryId")Long countryId,@WebParam( name = "code") String code) throws BusinessException;

	/**
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
	 * 
	 * Metodo: Consulta las bodegas segun los parametros para generar el reporte en excel
	 * @param countryId
	 * @param bodegaTipoId
	 * @param companiaId
	 * @param sucursalId
	 * @param cuadrillaId 
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author ialessan
	 */
	@WebMethod(operationName = "getWhByWhTypeDealerBranchCrewIds", action = "getWhByWhTypeDealerBranchCrewIds")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWhByWhTypeDealerBranchCrewIds( @WebParam(name="countryId")Long countryId,
																 @WebParam(name="bodegaTipoId")String warehouseTypeId,
																 @WebParam(name="companiaId")String dealerId,
																 @WebParam(name="sucursalId")String branchId,
																 @WebParam(name="cuadrillaId")String crewId
	) throws BusinessException;

	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados, punto de entrada bandeja manual
	 * de la consulta
	 * @param List<String> woCodes codigos de WO a ser consultadas
	 * @param countryId identificador del pais
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForReportForAllocatorGroupAction", action = "getWorkOrdersForReportForAllocatorGroupAction")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReportForAllocatorGroupAction(@WebParam( name = "woCodes")List<String> woCodes,@WebParam( name = "countryId") Long countryId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados, punto de entrada bandeja manual
	 * de la consulta
	 * @param List<String> woCodes codigos de WO a ser consultadas
	 * @param countryId identificador del pais
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getWorkOrdersForReportAttentionFinalizationGroupAction", action = "getWorkOrdersForReportAttentionFinalizationGroupAction")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWorkOrdersForReportAttentionFinalizationGroupAction(@WebParam( name = "woCodes")List<String> woCodes,@WebParam( name = "countryId") Long countryId)throws BusinessException;
	
	
	/**
	 * Método que permite la generación de un excel con los reportes de los 
	 * elementos instalados en un cliente. 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "getWareHouseElementsActualByCustomer", action = "getWareHouseElementsActualByCustomer")
	@XmlMimeType("application/octet-stream")
	public FileResponseDTO getWareHouseElementsActualByCustomer(@WebParam( name = "request")WareHouseElementClientFilterRequestDTO request)throws BusinessException;
	
	/**
	 * Metodo que se encarga de consultar las work orders canceladas, dados filtros de: dealer,sucursal, tipo de servicio, servicio, codigo de work order
	 * @param filter filtros de la consulta
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@WebMethod(operationName = "getCanceledWorkOrdersReport", action = "getCanceledWorkOrdersReport")
	public FileResponseDTO getCanceledWorkOrdersReport(@WebParam(name = "filter") WorkOrderCanceledFilterDTO filter) throws BusinessException;
	
	/**
	 * Metodo: Genera un reporte excel segun las WO's y Id's seleccionadas
	 * @param filter filtros de la consulta
	 * @param
	 * @param
	 * @return archivo de resultado de la consulta de work orders canceladas
	 * @throws BusinessException
	 * @author Aharker
	 */
	@WebMethod(operationName = "generateCrewWorkOrdersExcel", action = "generateCrewWorkOrdersExcel")
	public FileResponseDTO generateCrewWorkOrdersExcel(@WebParam(name="countryId")Long countryId, @WebParam(name="workOrderIds")List<Long> workOrderIds, @WebParam(name="crewIds")List<Long> crewIds) throws BusinessException;

}
