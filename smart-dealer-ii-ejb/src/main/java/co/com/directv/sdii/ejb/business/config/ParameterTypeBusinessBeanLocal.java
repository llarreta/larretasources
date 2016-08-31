package co.com.directv.sdii.ejb.business.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ParameterTypeVO;

/**
 * Esta interfaz define las opetaciones para la
 * entidad de ParameterType.
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 *
 */
@Local
public interface ParameterTypeBusinessBeanLocal {
		public List<ParameterTypeVO> getAllParametersTypes()throws BusinessException ;
}
