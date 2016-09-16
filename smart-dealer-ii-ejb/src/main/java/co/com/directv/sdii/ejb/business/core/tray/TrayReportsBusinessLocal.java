package co.com.directv.sdii.ejb.business.core.tray;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderFilterTrayDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayForPdfDTO;
import co.com.directv.sdii.model.pojo.collection.WorkOrderResponse;
import co.com.directv.sdii.reports.dto.ReportWorkOrderDTO;

/**
 * 
 * Interfaz de la logica de negocio para las consultas de reportes de excel y pdf 
 * 
 * Fecha de Creación: 5/09/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TrayReportsBusinessLocal {
	
	/**
	 * 
	 * Metodo: Consulta los detalles de una WO(se le agregan algunas consultas necesarias para llenar el PDF de una wo)
	 * @param WorkOrderTrayForPdfDTO filtro para consultar la WO, en este caso solo debe llevar el pais y el codigo de WO
	 * @return WorkOrderTrayDTO Objeto con la informacion necesaria para desplegar los detalles de una WO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waltuzarra
	 * @author jnova
	 */
	public ArrayList<WorkOrderTrayForPdfDTO> getWorkorderDetailForPdf(List<String> workOrderCodes,Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de ids con los filtros de las bandejas para los reportes en excel
	 * de WorkOrders
	 * @param filter Filtro de la capa de presentación para consultar las WO
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel que cumplen con el 
	 * filtro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReport(WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * Metodo: Retorna la lista de objetos con la información necesaria para generar los reportes en excel de las bandejas
	 * @param daoResponse
	 * @param filter
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ReportWorkOrderDTO> getReportWorkOrderDTOWorkOrdersForReport(WorkOrderResponse daoResponse,Long countryId,Long idUsuario) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de objetos con la información necesaria para generar los reportes en excel de las bandejas
	 * de WorkOrders
	 * @param filter Filtro de la capa de presentación para consultar las WO
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel que cumplen con el 
	 * filtro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReportAttentionFinalization(WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de objetos con la información necesaria para generar los reportes en excel de las bandejas
	 * de WorkOrders
	 * @param List<String> woCodes codigos de WO a ser consultadas
	 * @param countryId identificador del pais
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel que cumplen con el 
	 * filtro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReportAttentionFinalization(List<String> woCodes, Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de objetos con la información necesaria para generar los reportes en excel de las bandejas
	 * de WorkOrders
	 * @param filter Filtro de la capa de presentación para consultar las WO
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel que cumplen con el 
	 * filtro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReportForAllocator(WorkOrderFilterTrayDTO filter) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de objetos con la información necesaria para generar los reportes en excel de las bandejas
	 * de WorkOrders
	 * @param List<String> woCodes codigos de WO a ser consultadas
	 * @param countryId identificador del pais
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel que cumplen con el 
	 * filtro
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReportForAllocator(List<String> woCodes, Long countryId) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la lista de objetos con la informacion necesaria para generar excel en acciones grupales
	 * @param woCodes Lista de codigos de las WO seleccionadas
	 * @param countryId identificador del pais
	 * @return List<ReportWorkOrderDTO> Lista de objetos con información para generar reportes en excel
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public WorkOrderResponse getWorkOrdersForReport(List<String> woCodes, Long countryId, Long... userId) throws BusinessException;

}
