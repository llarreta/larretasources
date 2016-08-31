package co.com.directv.sdii.persistence.dao.core;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;
import co.com.directv.sdii.model.dto.AuxTechnicianDetailsDTO;
import co.com.directv.sdii.model.dto.DispacherProductivityDTO;
import co.com.directv.sdii.model.dto.MonthlyActivityResponseDTO;
import co.com.directv.sdii.model.dto.ReportWorkOrderRejectionAndFileResponseDTO;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;
import co.com.directv.sdii.model.dto.WoPendingLackMaterialsDTO;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;


@Local
public interface ReportsCoreDAOLocal {

	/**
	 * Metodo: permite obtener los datos del reporte de BACKLOG ACTIVIDAD
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<ActivityBacklogResponseDTO> getActivityBacklog(Long countryId,Date nowDate, RequestCollectionInfo requestInfo, String ... serviceTypes) throws DAOSQLException, DAOServiceException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param countryId
	 * @param requestInfo
	 * @param dateNow
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<DispacherProductivityDTO> getDispacherProductivity(Long countryId, RequestCollectionInfo requestInfo, Date dateNow) throws DAOSQLException,DAOServiceException ;
	
	/**
	 * Metodo: <Descripcion>
	 * @param request
	 * @param requestInfo
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<MonthlyActivityResponseDTO> getMonthlyActivity(ReportsParameterInputDTO request, Date nowDate, RequestCollectionInfo requestInfo) throws DAOSQLException,
	DAOServiceException;

	/**
	 * Metodo: <Descripcion>
	 * @param request
	 * @param requestInfo
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<MonthlyActivityResponseDTO> getJoinMonthlyActivity(ReportsParameterInputDTO request, Date nowDate, RequestCollectionInfo requestInfo) throws DAOSQLException,
	DAOServiceException;
	
	/** 
	 * CC54
	 * Metodo: permite obtener los datos del reporte de WO pendientes por falta de materiales
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<WoPendingLackMaterialsDTO> getWoPendingLackMaterials(Long countryId, RequestCollectionInfo requestInfo, String reasonCode) throws DAOSQLException, DAOServiceException;

	/**
	 * Metodo: Permite consultar las Work Orders rechazadas de un país
	 * @param countryId
	 * @param requestInfo
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public ReportWorkOrderRejectionAndFileResponseDTO getReportWorkOrderRejection(String woTypesCodes,
            String woStateCodeReject,
            Long countryId,
            RequestCollectionInfo requestInfo) throws DAOSQLException, DAOServiceException;
	
	/** 
	 * CC009
	 * Metodo: permite obtener los datos del reporte de WO pendientes por falta de materiales
	 * @return
	 * @throws DAOSQLException
	 * @throws DAOServiceException <tipo> <descripcion>
	 * @author
	 */
	public List<AuxTechnicianDetailsDTO> getAuxiliarTechnicianByDate(Long countryId, RequestCollectionInfo requestInfo, Date beginDate, Date endDate, Set<ScheduleReportParameter> scheduleReportParamSet) throws DAOSQLException, DAOServiceException;

}
