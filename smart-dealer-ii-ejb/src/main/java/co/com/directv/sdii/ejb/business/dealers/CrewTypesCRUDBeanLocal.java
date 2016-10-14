package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CrewTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad CrewTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewTypesCRUDBeanLocal {

	public CrewTypeVO getCrewTypesByCode(String code) throws BusinessException;
	
	public CrewTypeVO getCrewTypesByID(Long id) throws BusinessException;
	
	public List<CrewTypeVO> getAllCrewTypes() throws BusinessException;
}
