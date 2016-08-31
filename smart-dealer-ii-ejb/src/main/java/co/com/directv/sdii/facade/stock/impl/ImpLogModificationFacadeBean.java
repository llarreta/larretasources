package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImpLogModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogModificationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImpLogModification
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImpLogModificationFacadeLocal
 */
@Stateless(name="ImpLogModificationFacadeLocal",mappedName="ejb/ImpLogModificationFacadeLocal")
public class ImpLogModificationFacadeBean implements ImpLogModificationFacadeBeanLocal {

		
    @EJB(name="ImpLogModificationBusinessBeanLocal", beanInterface=ImpLogModificationBusinessBeanLocal.class)
    private ImpLogModificationBusinessBeanLocal businessImpLogModification;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogModificationFacadeLocal#getAllImpLogModifications()
     */
    public List<ImpLogModificationVO> getAllImpLogModifications() throws BusinessException {
    	return businessImpLogModification.getAllImpLogModifications();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogModificationFacadeLocal#getImpLogModificationsByID(java.lang.Long)
     */
    public ImpLogModificationVO getImpLogModificationByID(Long id) throws BusinessException {
    	return businessImpLogModification.getImpLogModificationByID(id);
    }
    
    @Override
	public List<ImpLogModificationVO> getImpLogModificationByImportLogID(Long id)
			throws BusinessException {
    	return businessImpLogModification.getImpLogModificationByImportLogID(id);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationFacadeLocal#createImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	public void createImpLogModification(ImpLogModificationVO obj) throws BusinessException {
		businessImpLogModification.createImpLogModification(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationFacadeLocal#updateImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	public void updateImpLogModification(ImpLogModificationVO obj) throws BusinessException {
		businessImpLogModification.updateImpLogModification(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogModificationFacadeLocal#deleteImpLogModification(co.com.directv.sdii.model.vo.ImpLogModificationVO)
	 */
	public void deleteImpLogModification(ImpLogModificationVO obj) throws BusinessException {
		businessImpLogModification.deleteImpLogModification(obj);
	}


	
}
