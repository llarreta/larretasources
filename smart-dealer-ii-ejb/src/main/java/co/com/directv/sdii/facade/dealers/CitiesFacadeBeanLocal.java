package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CityVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Cities
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CitiesFacadeBeanLocal {

	public CityVO getCitiesByCode(String code) throws BusinessException;
	
	public CityVO getCitiesByID(Long id) throws BusinessException;
	
	public List<CityVO> getAllCities() throws BusinessException;

        public List<CityVO> getCitiesByStateId(Long stateId) throws BusinessException;
}
