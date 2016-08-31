package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.SkillModeTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.SkillModeTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SkillModeTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD SkillModeType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.SkillModeTypeFacadeLocal
 */
@Stateless(name="SkillModeTypeFacadeLocal",mappedName="ejb/SkillModeTypeFacadeLocal")
public class SkillModeTypeFacadeBean implements SkillModeTypeFacadeBeanLocal {

		
    @EJB(name="SkillModeTypeBusinessBeanLocal", beanInterface=SkillModeTypeBusinessBeanLocal.class)
    private SkillModeTypeBusinessBeanLocal businessSkillModeType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillModeTypeFacadeLocal#getAllSkillModeTypes()
     */
    public List<SkillModeTypeVO> getAllSkillModeTypes() throws BusinessException {
    	return businessSkillModeType.getAllSkillModeTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SkillModeTypeFacadeLocal#getSkillModeTypesByID(java.lang.Long)
     */
    public SkillModeTypeVO getSkillModeTypeByID(Long id) throws BusinessException {
    	return businessSkillModeType.getSkillModeTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillModeTypeFacadeLocal#createSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	public void createSkillModeType(SkillModeTypeVO obj) throws BusinessException {
		businessSkillModeType.createSkillModeType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillModeTypeFacadeLocal#updateSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	public void updateSkillModeType(SkillModeTypeVO obj) throws BusinessException {
		businessSkillModeType.updateSkillModeType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SkillModeTypeFacadeLocal#deleteSkillModeType(co.com.directv.sdii.model.vo.SkillModeTypeVO)
	 */
	public void deleteSkillModeType(SkillModeTypeVO obj) throws BusinessException {
		businessSkillModeType.deleteSkillModeType(obj);
	}
}
