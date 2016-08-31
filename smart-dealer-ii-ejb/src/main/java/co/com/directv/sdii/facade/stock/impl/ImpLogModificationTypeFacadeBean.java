package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImpLogModificationTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogModificationTypeFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ImpLogModificationTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImpLogModificationType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImpLogModificationTypeFacadeLocal
 */
@Stateless(name="ImpLogModificationTypeFacadeLocal",mappedName="ejb/ImpLogModificationTypeFacadeLocal")
public class ImpLogModificationTypeFacadeBean implements ImpLogModificationTypeFacadeBeanLocal {

		
    @EJB(name="ImpLogModificationTypeBusinessBeanLocal", beanInterface=ImpLogModificationTypeBusinessBeanLocal.class)
    private ImpLogModificationTypeBusinessBeanLocal businessImpLogModificationType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogModificationTypeFacadeLocal#getAllImpLogModificationTypes()
     */
    public List<ImpLogModificationTypeVO> getAllImpLogModificationTypes() throws BusinessException {
    	return businessImpLogModificationType.getAllImpLogModificationTypes();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogModificationTypeFacadeLocal#getImpLogModificationTypesByID(java.lang.Long)
     */
    public ImpLogModificationTypeVO getImpLogModificationTypeByID(Long id) throws BusinessException {
    	return businessImpLogModificationType.getImpLogModificationTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationTypeFacadeLocal#createImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	public void createImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
		businessImpLogModificationType.createImpLogModificationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationTypeFacadeLocal#updateImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	public void updateImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
		businessImpLogModificationType.updateImpLogModificationType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationTypeFacadeLocal#deleteImpLogModificationType(co.com.directv.sdii.model.vo.ImpLogModificationTypeVO)
	 */
	public void deleteImpLogModificationType(ImpLogModificationTypeVO obj) throws BusinessException {
		businessImpLogModificationType.deleteImpLogModificationType(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ImpLogModificationTypeFacadeBeanLocal#getImpLogModificationTypeByCode(java.lang.String)
	 */
	public ImpLogModificationTypeVO getImpLogModificationTypeByCode(String code)
			throws BusinessException {
		return businessImpLogModificationType.getImpLogModificationTypeByCode(code);
	}


	@Override
	public List<ImpLogModificationTypeVO> getActiveImpLogModificationTypes()
			throws BusinessException {
		return businessImpLogModificationType.getActiveImpLogModificationTypes();
	}
}
