package co.com.directv.sdii.ws.business.core;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;

/**
 * Web Service que expone las operaciones
 * para la generacion de reportes de Core 
 * 
 * Fecha de Creación: 10/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@WebService(name="ReportCoreService",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IReportCoreWS {
	
	
	/**
	 * Metodo: Permite obtener los datos del reporte de BACKLOG ACTIVIDAD
	 * @param countryCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName = "getActivityBacklog", action = "getActivityBacklog")
	public List<ActivityBacklogResponseDTO> getActivityBacklog(@WebParam(name = "countryCode") String countryCode) throws BusinessException;
	
}