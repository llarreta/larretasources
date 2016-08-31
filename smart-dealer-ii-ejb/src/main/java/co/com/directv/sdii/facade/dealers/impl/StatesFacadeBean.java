package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.StatesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.StatesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.StateVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad States 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.StatesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.StatesFacadeBeanLocal
 */
@Stateless(name="StatesFacadeBeanLocal",mappedName="ejb/StatesFacadeBeanLocal")
public class StatesFacadeBean implements StatesFacadeBeanLocal {
	
	@EJB(name="StatesCRUDBeanLocal",beanInterface=StatesCRUDBeanLocal.class)
	private StatesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<StateVO> getAllStates() throws BusinessException {
		return business.getAllStates();
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public StateVO getStatesByCode(String code) throws BusinessException {
		return business.getStatesByCode(code);
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public StateVO getStatesByID(Long id) throws BusinessException {
		return business.getStatesByID(id);
	}

        public List<StateVO> getStatesByCountryID(Long countryId) throws BusinessException {
            return business.getStatesByCountryID(countryId);
        }
		

}
