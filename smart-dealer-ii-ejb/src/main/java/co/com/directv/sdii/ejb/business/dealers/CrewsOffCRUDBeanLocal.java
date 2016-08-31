package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CrewOffVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad CrewOff
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewsOffCRUDBeanLocal {

	public void createCrewOff(CrewOffVO obj) throws BusinessException;

	public CrewOffVO getCrewOffByID(Long id) throws BusinessException;
	
	public void updateCrewOff(CrewOffVO obj) throws BusinessException;
	
	public void deleteCrewOff(CrewOffVO obj) throws BusinessException;
	
	public List<CrewOffVO> getAllCrewOff() throws BusinessException;
}
