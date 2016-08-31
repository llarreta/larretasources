package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EpsCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EpsVO;
/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Eps 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EpsCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal
 */
@Stateless(name="EpsFacadeBeanLocal",mappedName="ejb/EpsFacadeBeanLocal")
public class EpsFacadeBean implements EpsFacadeBeanLocal {
	
	@EJB(name="EpsCRUDBeanLocal",beanInterface=EpsCRUDBeanLocal.class)
	private EpsCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal#getAllEps()
	 */
	public List<EpsVO> getAllEps() throws BusinessException {
		return business.getAllEps();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal#getEpsByCode(java.lang.String)
	 */
	public EpsVO getEpsByCode(String code) throws BusinessException {
		return business.getEpsByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal#getEpsByID(java.lang.Long)
	 */
	public EpsVO getEpsByID(Long id) throws BusinessException {
		return business.getEpsByID(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EpsFacadeBeanLocal#getAllEpsByCountryId(java.lang.Long)
	 */
	public List<EpsVO> getAllEpsByCountryId(Long countryId) throws BusinessException{
		List<EpsVO> result = business.getAllEpsByCountryId(countryId);
		return result;
	}
		
}
