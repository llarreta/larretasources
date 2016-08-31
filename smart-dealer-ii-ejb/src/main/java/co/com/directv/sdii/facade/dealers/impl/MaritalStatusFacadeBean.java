package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.MaritalStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.MaritalStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.MaritalStatusVO;

@Stateless(name="MaritalStatusFacadeBean",mappedName="ejb/MaritalStatusFacadeBean")
public class MaritalStatusFacadeBean implements MaritalStatusFacadeBeanLocal {

	@EJB(name="MaritalStatusBusinessBeanLocal",beanInterface=MaritalStatusBusinessBeanLocal.class)
	private MaritalStatusBusinessBeanLocal maritalStatusBusinessBean;

	@Override
	public List<MaritalStatusVO> getAllMaritalStatuss() throws BusinessException {
		return maritalStatusBusinessBean.getAllMaritalStatuss();
	}

}
