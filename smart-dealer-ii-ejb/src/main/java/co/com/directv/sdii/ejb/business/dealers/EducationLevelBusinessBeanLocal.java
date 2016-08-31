package co.com.directv.sdii.ejb.business.dealers;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EducationLevelVO;

@Local
public interface EducationLevelBusinessBeanLocal {

	/**
	 * Metodo: Obtiene todos los niveles de educacion
	 * @return Lista con los niveles de educacion
	 * @throws BusinessException En caso de error en la consulta
	 */
	public List<EducationLevelVO> getAllEducationLevel() throws BusinessException; 
}
