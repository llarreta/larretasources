package co.com.directv.sdii.ws.business.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

@WebService(name="CoreHspWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface ICoreHspWS {
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del reporte Productivity Report de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	@WebMethod(operationName="reportsProductivityReport", action="reportsProductivityReport", exclude = false)
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(@WebParam(name="filter")ReportsComplyAndScheduleFilterDTO filter, @WebParam(name="requestInfo")RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Pending Wo by date Report de core
	 * @param filter tiene los campos de filtro Lista de códigos de servicios y Código de la categoría.
	 * @param requestInfo en caso de usar paginacion
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	@WebMethod(operationName="getReportsPendingServicesByDate", action="getReportsPendingServicesByDate", exclude = false)
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(@WebParam(name="filter")ReportsPendingServicesByDateFilterDTO filter, @WebParam(name="requestInfo")RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Reporte Cumplimiento Agendamiento de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	@WebMethod(operationName="getReportsComplyAndSchedule", action="getReportsComplyAndSchedule", exclude = false)
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(@WebParam(name="filter")ReportsComplyAndScheduleFilterDTO filter, @WebParam(name="requestInfo")RequestCollectionInfo requestInfo) throws BusinessException;

	/**
	 * @author martlago / carlopez
	 * Metodo: hace la consulta del Reporte Reporte Work Order Agendadas en línea.
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return ReportsSucceedWorkOrderCSRAndFileResponseDTO
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	@WebMethod(operationName="getReportsSucceedWorkOrderCSR", action="getReportsSucceedWorkOrderCSR", exclude = false)
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(@WebParam(name="filter")ReportsSucceedWorkOrderFilterDTO filter, @WebParam(name="requestInfo")RequestCollectionInfo requestInfo) throws BusinessException;

	
	/**
	 * Metodo: Permite consultar las Work Orders rechazadas por país
	 * @param filter
	 * @param requestInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="getReportWorkOrderRejection", action="getReportWorkOrderRejection", exclude = false)
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection(@WebParam(name="filter")ReportWorkOrderRejectionFilterDTO filter,
			                                                                      @WebParam(name="requestInfo")RequestCollectionInfo requestInfo) throws BusinessException;
}
