package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ContractTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ContractTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad ContractTypes 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.ContractTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal
 */
@Stateless(name="",mappedName="")
public class ContractTypesFacadeBean implements ContractTypesFacadeBeanLocal {
	
	@EJB(name="ContractTypesCRUDBeanLocal",beanInterface=ContractTypesCRUDBeanLocal.class)
	private ContractTypesCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal#getAllContractTypes()
	 */
	public List<ContractTypeVO> getAllContractTypes() throws BusinessException {
		return business.getAllContractTypes();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal#getContractTypesByCode(java.lang.String)
	 */
	public ContractTypeVO getContractTypesByCode(String code) throws BusinessException {
		return business.getContractTypesByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal#getContractTypesByID(java.lang.Long)
	 */
	public ContractTypeVO getContractTypesByID(Long id) throws BusinessException {
		return business.getContractTypesByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.ContractTypesFacadeBeanLocal#getAllContractTypesByCountryId(java.lang.Long)
	 */
	public List<ContractTypeVO> getAllContractTypesByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllContractTypesByCountryId(countryId);
	}
		

}
