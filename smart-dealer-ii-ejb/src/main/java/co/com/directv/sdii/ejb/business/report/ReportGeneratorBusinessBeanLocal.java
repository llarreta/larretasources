package co.com.directv.sdii.ejb.business.report;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

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
public interface ReportGeneratorBusinessBeanLocal {
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrdersForReport(WorkOrderFilterTrayDTO filter)throws BusinessException;
	
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
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(List<String> woCodes, Long countryId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados, punto de entrada bandeja manual
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrdersForReportAttentionFinalization(WorkOrderFilterTrayDTO filter)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las workorders que cumplen con el filtro y retorna un archivo en excel con los resultados, punto de entrada bandeja manual
	 * de la consulta
	 * @param WorkOrderFilterTrayDTO filtro para generar archivo con informacion de workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrdersForReportForAllocator(WorkOrderFilterTrayDTO filter)throws BusinessException;
	
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
	public FileResponseDTO getWorkOrdersForReportForAllocator(List<String> woCodes, Long countryId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta las WO a partir de la lista de codigos enviados y retorna un archivo de excel con los resultados
	 * @param woCodes Lista de WO que se van a incluir en el archivo
	 * @param countryId Pais al que pertenecen las WO
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrdersForReport(List<String> woCodes, Long countryId, Long userId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los medio de contacto del cliente asociado a una WO para generar el excel
	 * @param customerCode Codigo del cliente del cual se desean obtener los medios de contacto
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getCustomerMediaContacts(String customerCode)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los servicios asociados a una WO para generar excel
	 * @param woId Identificador de la WO
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrderServices(Long woId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los elementos asociados a una shippingOrder para generar excel
	 * @param soId Identificador de la shippingOrder de la cual se desea obtener los elementos
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getShippingOrderElements(Long soId)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta los medios de contactos de un dealer por codigo para generar excel
	 * @param dealerCode Codigo de dealer
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getDealerMediaContacts(Long dealerCode)throws BusinessException;
	
	/**
	 *  
	 * Metodo: Consulta los contacts de una WO para generar excel
	 * @param woCode Codigo de WO
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getWorkOrderContacts(String woCode)throws BusinessException;

	/**
	 * Metodo: Genera un reporte a partir de los parámetros geéricos que se enviaban al Servlet
	 * @param cmd comando a ser ejecutado con el reporte
	 * @param args argumentos a ser enviados al comando
	 * @param fileName nombre del archivo a ser generado
	 * @param reportExtension extensión con la que se quiere generar el reporte "xls" o "pdf"
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public FileResponseDTO generateReport(String cmd, String args, String fileName, String reportExtension) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera el pdf de las workorders enviadas por parametro
	 * @param workOrderCodes Ids de las workorders a las cuales se les va a generar el pdf
	 * @param countryId identificador del pais de las workorders
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO generateWorkOrderPDF(List<String> workOrderCodes, Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera el archivo de excel para la bandeja de remision
	 * @param ReferencesFilterDTO dto que contiene filtro para generar excel de remisiones
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getReferencesByFilterForExcel(ReferencesFilterDTO referenceDTO) throws BusinessException;
	
	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param String transferReasonName
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova  
	 */
	public FileResponseDTO getTransferReasonByFilter(String transferReasonName) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Consulta todas las bodegas en cualquier estado para generar el reporte en excel
	 * @param countryId
	 * @param code
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public FileResponseDTO getAllWarehousesByCountryId(Long countryId, String code) throws BusinessException;

	/**
	 * 
 	 * ialessan
	 * junio 2014
	 * Modificacion de servicos de pantalla ubicaciones, cuatro nuevos parametros.
	 * 
	 * Metodo: Consulta bodegas segun los parametros para generar el reporte en excel
	 * @param countryId
	 * @param warehouseTypeId
	 * @param dealerId
	 * @param branchId
	 * @param crewId
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author ialessan
	 */
	public FileResponseDTO getWhByWhTypeDealerBranchCrewIds(Long    countryId, 
																String  warehouseTypeId, 
																String  dealerId, 
																String  branchId,
																String  crewId 
	) throws BusinessException;
	

	/**
	 * Método que permite la generación de un excel con los reportes de los 
	 * elementos instalados en un cliente. 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	public FileResponseDTO getWareHouseElementsActualByCustomer(
			WareHouseElementClientFilterRequestDTO request)
			throws BusinessException;

	/**
	 * 
	 * Metodo: Genera el excel de las workorders enviadas por parametro
	 * @param countryId
	 * @param code
	 * @return FileResponseDTO Objeto que encapsula el archivo junto con el nombre y la extension
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jgonzmol
	 */
	public FileResponseDTO generateCrewWorkOrdersExcel(Long countryId, List<Long> workOrderIds , List<Long> crewId) throws BusinessException;

	
}
