package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CitiesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CitiesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CityVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Cities 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CitiesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CitiesFacadeBeanLocal
 */
@Stateless(name="",mappedName="")
public class CitiesFacadeBean implements CitiesFacadeBeanLocal {
	
	@EJB(name="CitiesCRUDBeanLocal",beanInterface=CitiesCRUDBeanLocal.class)
	private CitiesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<CityVO> getAllCities() throws BusinessException {
		return business.getAllCities();
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public CityVO getCitiesByCode(String code) throws BusinessException {
		return business.getCitiesByCode(code);
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public CityVO getCitiesByID(Long id) throws BusinessException {
		return business.getCitiesByID(id);
	}

        public List<CityVO> getCitiesByStateId(Long stateId) throws BusinessException{
            return business.getCitiesByStateId(stateId);
        }
		

}
