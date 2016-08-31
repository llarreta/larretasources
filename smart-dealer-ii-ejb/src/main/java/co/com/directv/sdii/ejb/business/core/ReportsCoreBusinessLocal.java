package co.com.directv.sdii.ejb.business.core;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

@Local
public interface ReportsCoreBusinessLocal {
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del reporte Productivity Report de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(ReportsComplyAndScheduleFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Pending Wo by date Report de core
	 * @param filter tiene los campos de filtro Lista de códigos de servicios y Código de la categoría.
	 * @param requestInfo en caso de usar paginacion
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(ReportsPendingServicesByDateFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Reporte Cumplimiento Agendamiento de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(ReportsComplyAndScheduleFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener los datos del reporte de BACKLOG ACTIVIDAD
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ActivityBacklogResponseDTO> getActivityBacklog(String countryCode) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener la informacion del reporte dependiendo de los datos de entrada
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void generateReport(ReportsParameterInputDTO request) throws BusinessException;
	
	/**
	 * @author martlago / carlopez
	 * Metodo: hace la consulta del Reporte Reporte Work Order Agendadas en línea.
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return ReportsSucceedWorkOrderCSRAndFileResponseDTO
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(ReportsSucceedWorkOrderFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;

	/**
	 * Metodo: Permite consultar las Work Orders rechazadas de un país
	 * @param filter
	 * @param requestInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public ReportWorkOrderRejectionAndFileResponseDTO reportWorkOrderRejection( ReportWorkOrderRejectionFilterDTO filter,
            RequestCollectionInfo requestInfo) throws BusinessException;

}
