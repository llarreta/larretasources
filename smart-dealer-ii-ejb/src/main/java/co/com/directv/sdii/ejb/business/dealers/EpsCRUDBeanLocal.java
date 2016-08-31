package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EpsVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad Eps.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EpsCRUDBeanLocal {

	public EpsVO getEpsByCode(String code) throws BusinessException;
	
	public EpsVO getEpsByID(Long id) throws BusinessException;
	
	public List<EpsVO> getAllEps() throws BusinessException;
	
	public List<EpsVO> getAllEpsByCountryId(Long countryId) throws BusinessException;
}
