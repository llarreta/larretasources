package co.com.directv.sdii.ejb.business.assign;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;
import co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule;
import co.com.directv.sdii.model.pojo.WorkOrderCSR;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;

@Local
public interface CSRBusinessLocal {

	/**
	 * Metodo que envia alerta de correo con los mensajes de error 
	 * encontrados en el proceso de consultar la informacion de la wo en ibs	 
     * @param assignRequestDTO
	 * @param countryId
	 * @author
	 */
    public void sendMailCsr(AssignRequestDTO assignRequestDTO, Long countryId);
    
    /**
	 * Metodo: Realiza el agendamiento del CSR
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void agendaWorkOrdersCSR(WorkOrderCSRDTO request) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la workOrderCSR del parametro woCode
	 * @param woCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public WorkOrderCSR getWorkOrderCSRByWoCode(String woCode) throws BusinessException;
	
	/**
	 * Metodo: Crea un workOrderCSR
	 * @param workOrderCSR
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void createWorkOrderCSR(WorkOrderCSR workOrderCSR) throws BusinessException;
	
	/**
	 * Metodo: Actualiza un WorkOrderCSR
	 * @param workOrderCSR
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void updateWorkOrderCSR(WorkOrderCSR workOrderCSR) throws BusinessException;

	/**
	 * Metodo: Permite almacenar la traza de asignador de WorkOrderCSR
	 * @param id
	 * @param trace
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void traceAssignmentWorkOrderCSR(Long id,String trace) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la workOrderCsr a partir de un WorkOrderCSRDTO, si no existe la crea y si existe la actualiza
	 * @param workOrderCSRDTO
	 * @throws BusinessException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public void getWorkOrderCSRDTOByWoCode(WorkOrderCSRDTO workOrderCSRDTO) throws BusinessException, PropertiesException;
	
	/**
	 * Metodo: Crea un AuditExternalSystemSchedule
	 * @param auditExternalSystemSchedule
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void createAuditExternalSystemSchedule(AuditExternalSystemSchedule auditExternalSystemSchedule) throws BusinessException;
	
}
