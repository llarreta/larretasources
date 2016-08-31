package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ReportsParameterInputDTO;


/**
 * CC053 - HSP Reportes de inventarios.
 */
@Local
public interface ReportsStockBusinessLocal {

	
	/**
	 * Metodo: Permite obtener la informacion del reporte dependiendo de los datos de entrada
	 * @param request
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void generateReport(ReportsParameterInputDTO request) throws BusinessException;
	
}
