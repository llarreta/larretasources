package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanRemote;
import co.com.directv.sdii.model.vo.CountryVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Countries 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CountriesFacadeBeanLocal
 */
@Stateless(name="CountriesFacadeBeanLocal")
@Local({CountriesFacadeBeanLocal.class})
@Remote({CountriesFacadeBeanRemote.class})
public class CountriesFacadeBean implements CountriesFacadeBeanLocal {
	
	@EJB(name="CountriesCRUDBeanLocal",beanInterface=CountriesCRUDBeanLocal.class)
	private CountriesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad Countries 
	 * @return List<CountriesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<CountryVO> getAllCountries() throws BusinessException {
		return business.getAllCountries();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad Countries.
	 * @param code
	 * @return CountriesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public CountryVO getCountriesByCode(String code) throws BusinessException {
		return business.getCountriesByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad Countries.
	 * @param id
	 * @return CountriesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public CountryVO getCountriesByID(Long id) throws BusinessException {
		return business.getCountriesByID(id);
	}
		

}
