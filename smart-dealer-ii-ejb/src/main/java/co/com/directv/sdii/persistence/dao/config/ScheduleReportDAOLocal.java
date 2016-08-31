package co.com.directv.sdii.persistence.dao.config;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;
import co.com.directv.sdii.model.pojo.ScheduleReport;
import co.com.directv.sdii.model.pojo.ScheduleReportParameter;
import co.com.directv.sdii.model.pojo.ScheduleReportStatus;
import co.com.directv.sdii.model.vo.ScheduleReportPeriodTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportTypeVO;
import co.com.directv.sdii.model.vo.ScheduleReportVO;

@Local
public interface ScheduleReportDAOLocal {
	
    public void createScheduleReport(ScheduleReportVO sr) throws DAOServiceException, DAOSQLException;
    
    public void createScheduleReport(ScheduleReport sr) throws DAOServiceException, DAOSQLException;
    
    public List<ScheduleReportVO> getAllScheduleReport() throws DAOServiceException, DAOSQLException;

    //CC053 - HSP Reportes - CRUD Programacion.
    public ScheduleReport getScheduleReportById(Long id) throws DAOServiceException, DAOSQLException;
    
    public List<ScheduleReportVO> getPendingReportsForThisTime(Long countryId,Date dateNow) throws DAOServiceException, DAOSQLException;
    
    public List<ScheduleReport> getPendingReportsForThisTimeSR(Long countryId , Date dateNow) throws DAOServiceException, DAOSQLException;
    
    public void updateScheduleReport(ScheduleReportVO sr) throws DAOServiceException, DAOSQLException;
    
    //CC053 - HSP Reportes - CRUD Programacion.
    public void deleteScheduleReport(ScheduleReport scheduleReport) throws DAOServiceException, DAOSQLException;
    
    public void deleteScheduleReportLog(Long scheduleReportId) throws DAOServiceException, DAOSQLException;
    
    public void createScheduleReportLog(Long scheduleReportId, String message, String newStateCode, Date dateNow) throws DAOServiceException, DAOSQLException;
    
    public ScheduleReportStatus getScheduleReportStateByCode(String code) throws DAOServiceException, DAOSQLException;

    //CC053 - HSP Reportes - CRUD Programacion.
    public List<ScheduleReportStatusDTO> getAllScheduleReportStatus() throws DAOServiceException, DAOSQLException;
    
    public List<ScheduleReportTypeDTO> getScheduleReportTypes() throws DAOServiceException, DAOSQLException;
    
    public ScheduleReportTypeVO getScheduleReportTypeId(Long id) throws DAOServiceException, DAOSQLException;
    
    public ScheduleReportPeriodTypeVO getScheduleReportPeriodTypeByCode(String code) throws DAOServiceException, DAOSQLException;

    //CC053 - HSP Reportes - CRUD Programacion.
    public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter, Long periodTypeId) throws DAOServiceException, DAOSQLException;
    
    public Long getScheduleReportsQuantityByCountryId (Long countryId, Long periodTypeId, Date dateNow) throws DAOServiceException, DAOSQLException;
    
    //CC009 
    public ScheduleReportTypeVO getScheduleReportTypeByCode(String code) throws DAOServiceException, DAOSQLException;
    
    public void createScheduleReportParameter(ScheduleReportParameter srp) throws DAOServiceException, DAOSQLException;
    
    public List<ScheduleReportParameter> getScheduleReportParamByScheduleReportId(Long id) throws DAOServiceException, DAOSQLException;
}
