package co.com.directv.sdii.ejb.business.facade.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.BusinessTierVersionBeanLocal;
import co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessTierVersionVO;

/**
 * 
 * Invoca las operaciones de negocio que retornan la version
 * de la capa de negocio. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal
 */
@Stateless(name="BusinessTierVersionFacadelocal",mappedName="ejb/BusinessTierVersionFacadelocal")
public class BusinessTierVersionFacadeBean implements BusinessTierVersionFacadelocal{

	@EJB(name="BusinessTierVersionBeanLocal", beanInterface=BusinessTierVersionBeanLocal.class)
	private BusinessTierVersionBeanLocal business;
	
	@Override
	public BusinessTierVersionVO getBusinessVersion() throws BusinessException {		
		return business.getBusinessVersion();
	}
	
	@Override
	public void test6008(String woCode) throws BusinessException {
		
		business.test6008(woCode);
		
	}

}
