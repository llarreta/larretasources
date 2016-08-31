package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.CoverageTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CoverageTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD CoverageType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.CoverageTypeFacadeLocal
 */
@Stateless(name="CoverageTypeFacadeLocal",mappedName="ejb/CoverageTypeFacadeLocal")
public class CoverageTypeFacadeBean implements CoverageTypeFacadeBeanLocal {

		
    @EJB(name="CoverageTypeBusinessBeanLocal", beanInterface=CoverageTypeBusinessBeanLocal.class)
    private CoverageTypeBusinessBeanLocal businessCoverageType;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.CoverageTypeFacadeLocal#getAllCoverageTypes()
     */
    public List<CoverageTypeVO> getAllCoverageTypes() throws BusinessException {
    	return businessCoverageType.getAllCoverageTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.CoverageTypeFacadeLocal#getCoverageTypesByID(java.lang.Long)
     */
    public CoverageTypeVO getCoverageTypeByID(Long id) throws BusinessException {
    	return businessCoverageType.getCoverageTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.CoverageTypeFacadeLocal#createCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	public void createCoverageType(CoverageTypeVO obj) throws BusinessException {
		businessCoverageType.createCoverageType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.CoverageTypeFacadeLocal#updateCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	public void updateCoverageType(CoverageTypeVO obj) throws BusinessException {
		businessCoverageType.updateCoverageType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.CoverageTypeFacadeLocal#deleteCoverageType(co.com.directv.sdii.model.vo.CoverageTypeVO)
	 */
	public void deleteCoverageType(CoverageTypeVO obj) throws BusinessException {
		businessCoverageType.deleteCoverageType(obj);
	}
}
