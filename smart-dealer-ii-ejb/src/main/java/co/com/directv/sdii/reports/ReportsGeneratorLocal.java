package co.com.directv.sdii.reports;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;

/**
 * 
 * @author Leonardo Cardozo Cadavid
 *
 */
@Local
public interface ReportsGeneratorLocal {
	
	
	/**
	 * Genera el resporte de visitas en las �rdenes de trabajo
	 * @param workOrders
	 * @return String reportFile
	 * @throws PDFException
	 */
	public String generateCrewWorkOrdersPDF(Map<WorkOrder, WorkOrderAgenda> workOrders, Employee employee) throws PDFException;
	
	/**
	 * Con base en una lista de ordenes de servicio genera un pdf
	 * @param crewId 
	 * @param workOrders
	 * @return String reportFile
	 */
	public String generateWorkOrdersPDF(Collection<WorkOrderReportInfo> workOrderIds, Long crewId) throws PDFException;
	
	/**
	 * Con base en una lista de ordenes de servicio genera un pdf
	 * @param workOrderIds
	 * @return
	 * @throws PDFException
	 */
	public String generateWorkOrdersPDF(Collection<WorkOrderReportInfo> workOrderIds) throws PDFException;

}