package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.config.ParameterTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ParameterTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ParameterTypeVO;

/**
 * Session Bean implementation class ParameterTypeFacadeBean
 */
@Stateless(name="ParameterTypeFacadeBeanLocal",mappedName="ejb/ParameterTypeFacadeBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParameterTypeFacadeBean implements ParameterTypeFacadeBeanLocal {

	@EJB(name="ParameterTypeBusinessBeanLocal", beanInterface=ParameterTypeBusinessBeanLocal.class)
	private ParameterTypeBusinessBeanLocal crudParameterType;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ParameterTypeVO> getAllParametersTypes() throws BusinessException {
		return crudParameterType.getAllParametersTypes();
	}

}
