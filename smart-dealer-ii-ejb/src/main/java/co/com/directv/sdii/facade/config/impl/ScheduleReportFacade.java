package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ScheduleReportBussinesLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ScheduleReportFacadeLocal;
import co.com.directv.sdii.model.dto.CreateAuxiliarTechnicianReportDTO;
import co.com.directv.sdii.model.dto.CreateScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;

/**
 * Session Bean implementation class ScheduleReportFacade
 */
@Stateless(name="ScheduleReportFacade" )
public class ScheduleReportFacade implements ScheduleReportFacadeLocal {

	@EJB
	private ScheduleReportBussinesLocal scheduleReportBussinesLocal;
	
    /**
     * Default constructor. 
     */
    public ScheduleReportFacade() {
    }

	@Override
	public void createScheduleReport(CreateScheduleReportDTO csrDTO)
			throws BusinessException {
		scheduleReportBussinesLocal.createScheduleReport(csrDTO);
	}

	@Override
	public List<ScheduleReportTypeDTO> getScheduleReportTypes()
			throws BusinessException {
		return scheduleReportBussinesLocal.getScheduleReportTypes();
	}

	//CC053 - HSP Reportes - CRUD Programacion.
	@Override
	public List<ScheduleReportStatusDTO> getScheduleReportStatus()
			throws BusinessException {
		return scheduleReportBussinesLocal.getScheduleReportStatus();
	}

	@Override
	public void processReports(Long countryId) throws BusinessException {
		scheduleReportBussinesLocal.processReports(countryId);		
	}

	//CC053 - HSP Reportes - CRUD Programacion.
	@Override
	public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter)
			throws BusinessException {
		return scheduleReportBussinesLocal.getScheduleReports(filter);
	}

	//CC053 - HSP Reportes - CRUD Programacion.
	@Override
	public void deleteScheduleReport(Long id)
			throws BusinessException {
		scheduleReportBussinesLocal.deleteScheduleReport(id);
	}
	
	@Override
	public void createAuxiliarTechnicianReport(CreateAuxiliarTechnicianReportDTO filter)
			throws BusinessException {
		scheduleReportBussinesLocal.createAuxiliarTechnicianReport(filter);
	}


}
