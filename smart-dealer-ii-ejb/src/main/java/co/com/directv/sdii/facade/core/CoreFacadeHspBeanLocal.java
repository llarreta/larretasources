package co.com.directv.sdii.facade.core;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionFilterDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleDTO;
import co.com.directv.sdii.model.dto.ReportsComplyAndScheduleFilterDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateDTO;
import co.com.directv.sdii.model.dto.ReportsPendingServicesByDateFilterDTO;
import co.com.directv.sdii.model.dto.ReportsProductivityReportAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderCSRAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsSucceedWorkOrderFilterDTO;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

@Local
public interface CoreFacadeHspBeanLocal {
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del reporte Productivity Report de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws BusinessException
	 */
	public ReportsProductivityReportAndFileResponseDTO reportsProductivityReport(ReportsComplyAndScheduleFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Pending Wo by date Report de core
	 * @param filter tiene los campos de filtro Lista de códigos de servicios y Código de la categoría.
	 * @param requestInfo en caso de usar paginacion
	 * @return
	 * @throws BusinessException
	 */
	public ReportsPendingServicesByDateAndFileResponseDTO getReportsPendingServicesByDate(ReportsPendingServicesByDateFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * @author Aharker
	 * Metodo: hace la consulta del Reporte Reporte Cumplimiento Agendamiento de core
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return DTO's del reporte
	 * @throws BusinessException
	 */
	public ReportsComplyAndScheduleAndFileResponseDTO getReportsComplyAndSchedule(ReportsComplyAndScheduleFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las Work Orders rechazadas por pais
	 * @param filter
	 * @param requestInfo
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection( ReportWorkOrderRejectionFilterDTO filter,RequestCollectionInfo requestInfo) throws BusinessException;

	/**
	 * REQ002 - WO Agendadas en linea.
	 * @author martlago / carlopez
	 * Metodo: hace la consulta del Reporte Reporte Work Order Agendadas en línea.
	 * @param filter principalmente tiene fecha inicio y fecha final
	 * @param requestInfo en caso de usar paginacion
	 * @return ReportsSucceedWorkOrderCSRAndFileResponseDTO
	 * @throws DAOSQLException
	 * @throws DAOServiceException
	 */
	public ReportsSucceedWorkOrderCSRAndFileResponseDTO getReportsSucceedWorkOrderCSR(ReportsSucceedWorkOrderFilterDTO filter, RequestCollectionInfo requestInfo) throws BusinessException;

}
