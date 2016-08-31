package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SkillEvaluationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SkillEvaluationTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SkillEvaluationTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SkillEvaluationType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SkillEvaluationTypeFacadeLocal
 */
@Stateless(name="SkillEvaluationTypeFacadeLocal",mappedName="ejb/SkillEvaluationTypeFacadeLocal")
public class SkillEvaluationTypeFacadeBean implements SkillEvaluationTypeFacadeBeanLocal {

		
    @EJB(name="SkillEvaluationTypeBusinessBeanLocal", beanInterface=SkillEvaluationTypeBusinessBeanLocal.class)
    private SkillEvaluationTypeBusinessBeanLocal businessSkillEvaluationType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillEvaluationTypeFacadeLocal#getAllSkillEvaluationTypes()
     */
    public List<SkillEvaluationTypeVO> getAllSkillEvaluationTypes() throws BusinessException {
    	return businessSkillEvaluationType.getAllSkillEvaluationTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillEvaluationTypeFacadeLocal#getSkillEvaluationTypesByID(java.lang.Long)
     */
    public SkillEvaluationTypeVO getSkillEvaluationTypeByID(Long id) throws BusinessException {
    	return businessSkillEvaluationType.getSkillEvaluationTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillEvaluationTypeFacadeLocal#createSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	public void createSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
		businessSkillEvaluationType.createSkillEvaluationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillEvaluationTypeFacadeLocal#updateSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	public void updateSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
		businessSkillEvaluationType.updateSkillEvaluationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillEvaluationTypeFacadeLocal#deleteSkillEvaluationType(co.com.directv.sdii.model.vo.SkillEvaluationTypeVO)
	 */
	public void deleteSkillEvaluationType(SkillEvaluationTypeVO obj) throws BusinessException {
		businessSkillEvaluationType.deleteSkillEvaluationType(obj);
	}
}
