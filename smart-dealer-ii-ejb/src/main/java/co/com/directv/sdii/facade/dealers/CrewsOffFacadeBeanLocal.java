package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CrewOffVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Crews
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewsOffFacadeBeanLocal {
	
	public void createCrewOff(CrewOffVO obj) throws BusinessException;

	public CrewOffVO getCrewOffByID(Long id) throws BusinessException;
	
	public void updateCrewOff(CrewOffVO obj) throws BusinessException;
	
	public void deleteCrewOff(CrewOffVO obj) throws BusinessException;
	
	public List<CrewOffVO> getAllCrewOff() throws BusinessException;
}
