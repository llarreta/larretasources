package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RefConfirmationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefConfirmationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RefConfirmation
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RefConfirmationFacadeLocal
 */
@Stateless(name="RefConfirmationFacadeLocal",mappedName="ejb/RefConfirmationFacadeLocal")
public class RefConfirmationFacadeBean implements RefConfirmationFacadeBeanLocal {

		
    @EJB(name="RefConfirmationBusinessBeanLocal", beanInterface=RefConfirmationBusinessBeanLocal.class)
    private RefConfirmationBusinessBeanLocal businessRefConfirmation;
    
  
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefConfirmationFacadeLocal#getAllRefConfirmations()
     */
    public List<RefConfirmationVO> getAllRefConfirmations() throws BusinessException {
    	return businessRefConfirmation.getAllRefConfirmations();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefConfirmationFacadeLocal#getRefConfirmationsByID(java.lang.Long)
     */
    public RefConfirmationVO getRefConfirmationByID(Long id) throws BusinessException {
    	return businessRefConfirmation.getRefConfirmationByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefConfirmationFacadeLocal#createRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	public void createRefConfirmation(RefConfirmationVO obj) throws BusinessException {
		businessRefConfirmation.createRefConfirmation(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefConfirmationFacadeLocal#updateRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	public void updateRefConfirmation(RefConfirmationVO obj) throws BusinessException {
		businessRefConfirmation.updateRefConfirmation(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefConfirmationFacadeLocal#deleteRefConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO)
	 */
	public void deleteRefConfirmation(RefConfirmationVO obj) throws BusinessException {
		businessRefConfirmation.deleteRefConfirmation(obj);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefConfirmationFacadeLocal#saveNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	public void saveNotSerializedReferenceElementItemConfirmation(RefConfirmationVO confirmation,UserVO user,Long referenceId) throws BusinessException {
		businessRefConfirmation.saveNotSerializedReferenceElementItemConfirmation(confirmation,user,referenceId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefConfirmationFacadeLocal#saveSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	public void saveSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmation,UserVO user,Long referenceId) throws BusinessException {
		businessRefConfirmation.saveSerializedReferenceElementItemConfirmation(confirmation,user,referenceId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefConfirmationFacadeLocal#saveAllNotSerializedReferenceElementItemConfirmation(co.com.directv.sdii.model.vo.RefConfirmationVO,co.com.directv.sdii.model.vo.UserVO,java.lang.Long)
	 */
	public void saveAllNotSerializedReferenceElementItemConfirmation(List<RefConfirmationVO> confirmations,UserVO user,Long referenceId) throws BusinessException {
		businessRefConfirmation.saveAllNotSerializedReferenceElementItemConfirmation(confirmations,user,referenceId);
	}
}
