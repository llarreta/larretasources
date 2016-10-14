package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CityVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad Cities.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CitiesCRUDBeanLocal {

	public CityVO getCitiesByCode(String code) throws BusinessException;
	
	public CityVO getCitiesByID(Long id) throws BusinessException;
	
	public List<CityVO> getAllCities() throws BusinessException;

        public List<CityVO> getCitiesByStateId(Long stateId)throws BusinessException;
}
