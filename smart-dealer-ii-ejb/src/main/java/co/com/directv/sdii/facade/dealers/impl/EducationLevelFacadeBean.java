package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EducationLevelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EducationLevelFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EducationLevelVO;

@Stateless(name="EducationLevelFacadeBeanLocal",mappedName="ejb/EducationLevelFacadeBeanLocal")
public class EducationLevelFacadeBean implements EducationLevelFacadeBeanLocal {

	@EJB(name="EducationLevelBusinessBeanLocal",beanInterface=EducationLevelBusinessBeanLocal.class)
	private EducationLevelBusinessBeanLocal educationLevelBusinessBean;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EducationLevelFacadeBeanLocal#getAllEducationLevel()
	 */
	@Override
	public List<EducationLevelVO> getAllEducationLevel() throws BusinessException {
		return educationLevelBusinessBean.getAllEducationLevel();
	}

   
}
