package co.com.directv.sdii.ejb.business.dealers;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MaritalStatusVO;

@Local
public interface MaritalStatusBusinessBeanLocal {

	/**
	 * Metodo: Obtiene todos los estados civiles
	 * @return Lista de estados civiles
	 * @throws BusinessException En caso de error en la consulta
	 */
	public List<MaritalStatusVO> getAllMaritalStatuss() throws BusinessException;
}
