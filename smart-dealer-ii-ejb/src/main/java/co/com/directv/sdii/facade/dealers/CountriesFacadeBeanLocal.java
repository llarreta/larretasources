package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CountryVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Countries
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CountriesFacadeBeanLocal {

	public CountryVO getCountriesByCode(String code) throws BusinessException;
	
	public CountryVO getCountriesByID(Long id) throws BusinessException;
	
	public List<CountryVO> getAllCountries() throws BusinessException;
}
