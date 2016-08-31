package co.com.directv.sdii.ejb.business.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceTypeVO;

/**
 * Esta interfaz define las operaciones de negocio para la
 * entidad de ServiceType
 * 
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
 
@Local
public interface ServiceTypeBusinessBeanLocal {
	/**
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException;

	/**
	 * Metodo: Obtiene el tipo de servicio por el código de servicio especificado
	 * @param serviceCode código del servicio a ser consultado
	 * @return información del tipo de servicio que es referenciada por el servicio con el código especificado
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public ServiceTypeVO getServiceTypeByServiceCode(String serviceCode)throws BusinessException;
}
