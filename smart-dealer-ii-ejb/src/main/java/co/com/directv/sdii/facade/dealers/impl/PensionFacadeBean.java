package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.PensionCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.PensionFacadeBeanLocal;
import co.com.directv.sdii.model.vo.PensionVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Pension 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.PensionCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.PensionFacadeBeanLocal
 */
@Stateless(name="PensionFacadeBeanLocal",mappedName="ejb/PensionFacadeBeanLocal")
public class PensionFacadeBean implements PensionFacadeBeanLocal {
	
	@EJB(name="PensionCRUDBeanLocal",beanInterface=PensionCRUDBeanLocal.class)
	private PensionCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<PensionVO> getAllPension() throws BusinessException {
		return business.getAllPension();
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PensionVO getPensionByCode(String code) throws BusinessException {
		return business.getPensionByCode(code);
	}

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PensionVO getPensionByID(Long id) throws BusinessException {
		return business.getPensionByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.PensionFacadeBeanLocal#getAllPensionByCountryId(java.lang.Long)
	 */
	public List<PensionVO> getAllPensionByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllPensionByCountryId(countryId);
	}
		

}
