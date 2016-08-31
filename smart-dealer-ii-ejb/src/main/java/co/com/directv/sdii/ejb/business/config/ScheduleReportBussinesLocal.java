package co.com.directv.sdii.ejb.business.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CreateAuxiliarTechnicianReportDTO;
import co.com.directv.sdii.model.dto.CreateScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;

//CC053 - HSP Reportes - CRUD Programacion.
@Local
public interface ScheduleReportBussinesLocal {

    public void createScheduleReport(CreateScheduleReportDTO csrDTO) throws BusinessException;
    
    public void deleteScheduleReport(Long id) throws BusinessException;

    public List<ScheduleReportTypeDTO> getScheduleReportTypes() throws BusinessException;

    public List<ScheduleReportStatusDTO> getScheduleReportStatus() throws BusinessException;
    
    public void processReports(Long countryId) throws BusinessException;
    
    public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter) throws BusinessException;

    public void createAuxiliarTechnicianReport(CreateAuxiliarTechnicianReportDTO filter) throws BusinessException;
}
