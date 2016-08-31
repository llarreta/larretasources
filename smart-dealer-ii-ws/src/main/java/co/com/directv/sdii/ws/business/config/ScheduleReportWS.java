package co.com.directv.sdii.ws.business.config;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ScheduleReportFacadeLocal;
import co.com.directv.sdii.model.dto.CreateScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportDTO;
import co.com.directv.sdii.model.dto.ScheduleReportFilterDTO;
import co.com.directv.sdii.model.dto.ScheduleReportStatusDTO;
import co.com.directv.sdii.model.dto.ScheduleReportTypeDTO;
import co.com.directv.sdii.model.dto.CreateAuxiliarTechnicianReportDTO;


/**
 * Servicio web que expone las operaciones relacionadas con ScheduleReport
 * 
 * Fecha de Creaci�n: 11/10/2012
 * @author martlago
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ScheduleReportFacadeBeanLocal
 */

@MTOM
@WebService(serviceName="ScheduleReportWS",
targetNamespace="http://report.business.ws.sdii.directv.com.co/",
portName="ScheduleReportPort")	
@Stateless()
public class ScheduleReportWS {
	
	@EJB
    private ScheduleReportFacadeLocal ejbRef;
	
	 @WebMethod(operationName = "createScheduleReport", action="createScheduleReport")
	 public void createScheduleReport(CreateScheduleReportDTO csrDTO)
				throws BusinessException {
			ejbRef.createScheduleReport(csrDTO);
	 }
	 
	 @WebMethod(operationName = "getScheduleReportTypes", action="getScheduleReportTypes")
		public List<ScheduleReportTypeDTO> getScheduleReportTypes()
				throws BusinessException {
			return ejbRef.getScheduleReportTypes();
	 }

	 //CC053 - HSP Reportes - CRUD Programacion.
	 @WebMethod(operationName = "getScheduleReportStatus", action="getScheduleReportStatus")
		public List<ScheduleReportStatusDTO> getScheduleReportStatus()
				throws BusinessException {
			return ejbRef.getScheduleReportStatus();
	 }
	 
	 @WebMethod(operationName = "getScheduleReports", action="getScheduleReports")
	   public List<ScheduleReportDTO> getScheduleReports(ScheduleReportFilterDTO filter) 
			   throws BusinessException{
		 return ejbRef.getScheduleReports(filter);
	 }
	 
	 @WebMethod(operationName = "deleteScheduleReport", action="deleteScheduleReport")
	 public void deleteScheduleReport(Long id)
				throws BusinessException {
			ejbRef.deleteScheduleReport(id);
	 }

	 @WebMethod(operationName = "createAuxiliarTechnicianReport", action="createAuxiliarTechnicianReport")
	 public void createAuxiliarTechnicianReport(CreateAuxiliarTechnicianReportDTO filter)
				throws BusinessException {
			ejbRef.createAuxiliarTechnicianReport(filter);
	 }
	 
}
