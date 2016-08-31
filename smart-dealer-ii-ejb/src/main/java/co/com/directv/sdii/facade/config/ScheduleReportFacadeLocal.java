package co.com.directv.sdii.facade.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.CreateAuxiliarTechnicianReportDTO;
import co.com.directv.sdii.model.dto.CreateScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;

@Local
public interface ScheduleReportFacadeLocal {
	
	/**
	 * Metodo:  Persiste la información de un objeto ScheduleReport
	 * @param obj objeto que encapsula la información de un ScheduleReport
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ScheduleReport
	 * @author martlago
	 */
    public void createScheduleReport(CreateScheduleReportDTO csrDTO) throws BusinessException;

    public List<ScheduleReportTypeDTO> getScheduleReportTypes() throws BusinessException;
    
    //CC053 - HSP Reportes - CRUD Programacion.
    public List<ScheduleReportStatusDTO> getScheduleReportStatus() throws BusinessException; 
    
    public void processReports(Long countryId) throws BusinessException;
    
    //CC053 - HSP Reportes - CRUD Programacion.
    public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter) throws BusinessException;
    
    //CC053 - HSP Reportes - CRUD Programacion.
    public void deleteScheduleReport(Long id) throws BusinessException;
    
    //CC009 - HSP Reportes - CRUD Programacion.
    public void createAuxiliarTechnicianReport(CreateAuxiliarTechnicianReportDTO filter) throws BusinessException;
}
