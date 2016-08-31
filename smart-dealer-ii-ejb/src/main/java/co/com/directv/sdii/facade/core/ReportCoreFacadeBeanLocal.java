package co.com.directv.sdii.facade.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ActivityBacklogResponseDTO;

@Local
public interface ReportCoreFacadeBeanLocal {
	
	
	/**
	 * Metodo: Permite obtener los datos del reporte de BACKLOG ACTIVIDAD
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ActivityBacklogResponseDTO> getActivityBacklog(String countryCode) throws BusinessException;
}
