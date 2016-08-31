package co.com.directv.sdii.facade.config;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ServiceHourStatusVO;

/**
 * interfaz que define las operaciones de la entidad
 * ConfigJornadasStatus.
 * 
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@Local
public interface ConfigJornadasStatusFacadeBeanLocal {
	public List<ServiceHourStatusVO> getAllServiceHourStatus() throws BusinessException;
}
