package co.com.directv.sdii.ejb.business.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceHourStatusVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Jornadas.
 *
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigJornadasStatusBusinessBeanLocal {

	public List<ServiceHourStatusVO> getAllServiceHourStatus() throws BusinessException;
}
