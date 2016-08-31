package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ImpLogConfirmationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogConfirmationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ImpLogConfirmation
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImpLogConfirmationFacadeLocal
 */
@Stateless(name="ImpLogConfirmationFacadeLocal",mappedName="ejb/ImpLogConfirmationFacadeLocal")
public class ImpLogConfirmationFacadeBean implements ImpLogConfirmationFacadeBeanLocal {

		
    @EJB(name="ImpLogConfirmationBusinessBeanLocal", beanInterface=ImpLogConfirmationBusinessBeanLocal.class)
    private ImpLogConfirmationBusinessBeanLocal businessImpLogConfirmation;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogConfirmationFacadeLocal#getAllImpLogConfirmations()
     */
    public List<ImpLogConfirmationVO> getAllImpLogConfirmations() throws BusinessException {
    	return businessImpLogConfirmation.getAllImpLogConfirmations();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ImpLogConfirmationFacadeLocal#getImpLogConfirmationsByID(java.lang.Long)
     */
    public ImpLogConfirmationVO getImpLogConfirmationByID(Long id) throws BusinessException {
    	return businessImpLogConfirmation.getImpLogConfirmationByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogConfirmationFacadeLocal#createImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	public void createImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
		businessImpLogConfirmation.createImpLogConfirmation(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogConfirmationFacadeLocal#updateImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	public void updateImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
		businessImpLogConfirmation.updateImpLogConfirmation(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ImpLogConfirmationFacadeLocal#deleteImpLogConfirmation(co.com.directv.sdii.model.vo.ImpLogConfirmationVO)
	 */
	public void deleteImpLogConfirmation(ImpLogConfirmationVO obj) throws BusinessException {
		businessImpLogConfirmation.deleteImpLogConfirmation(obj);
	}
}
